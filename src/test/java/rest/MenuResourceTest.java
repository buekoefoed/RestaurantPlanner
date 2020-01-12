package rest;

import dtos.RecipeDTO;
import entities.Ingredient;
import entities.Item;
import entities.Recipe;
import entities.WeekMenu;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;

class MenuResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";

    private static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    private static RecipeDTO recipeDTO1, recipeDTO2;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @BeforeAll
    static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.TEST, EMF_Creator.Strategy.DROP_AND_CREATE);

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @BeforeEach
    public void setUp() {
        EntityManager em = getEntityManager();

        Item item1 = new Item("Potatoes",5.5,62.1);
        Item item2 = new Item("Onions", 8.0, 14.0);
        Item item3 = new Item("Garlic", 17.5, 0.8);
        Item item4 = new Item("Chicken", 52.8, 4.2);
        Item item5 = new Item("Salt", 7.0, 12.0);

        Ingredient ingredient1 = new Ingredient(item2, 4000);
        Ingredient ingredient2 = new Ingredient(item3, 50);
        Ingredient ingredient3 = new Ingredient(item4, 2000);
        Ingredient ingredient4 = new Ingredient(item1, 3000);

        List<Ingredient> ingredients1 = new ArrayList<>();
        List<Ingredient> ingredients2 = new ArrayList<>();
        ingredients1.add(ingredient1);
        ingredients1.add(ingredient2);
        ingredients2.add(ingredient3);
        ingredients2.add(ingredient4);

        Recipe recipe1 = new Recipe("Onion Soup", ingredients1, 1800, "Fry onion and garlic, add water and boil for 15min");
        Recipe recipe2 = new Recipe("Chicken with potatoes", ingredients2, 3600, "Cook chicken in oven and boil potatoes until done");

        ingredient1.addRecipe(recipe1);
        ingredient2.addRecipe(recipe1);
        ingredient3.addRecipe(recipe2);
        ingredient4.addRecipe(recipe2);

        List<Recipe> recipes = new ArrayList<>();
        recipes.add(recipe1);
        recipes.add(recipe2);

        WeekMenu weekMenu = new WeekMenu(recipes, 1, 2020);

        //weekMenu.addRecipe(recipe1);
        //weekMenu.addRecipe(recipe2);

        try {
            em.getTransaction().begin();
            em.persist(weekMenu);
            em.getTransaction().commit();
        } finally {
            em.close();
        }

        recipeDTO1 = new RecipeDTO(recipe1);
        recipeDTO2 = new RecipeDTO(recipe2);
    }

    @AfterEach
    void tearDown() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("select weekMenu from WeekMenu weekMenu", WeekMenu.class).getResultList().forEach(em::remove);
            em.createQuery("select recipe from Recipe recipe", Recipe.class).getResultList().forEach((em::remove));
            em.createQuery("select ingredient from Ingredient ingredient", Ingredient.class).getResultList().forEach((em::remove));
            em.createQuery("select item from Item item", Item.class).getResultList().forEach((em::remove));
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterAll
    static void closeTestServer() {
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    @Test
    void demo() {
    }

    @Test
    void getAllRecipesSize() {
        given()
                .contentType("application/json")
                .get("restaurant/all").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("", hasSize(2));
    }

    @Test
    void getAllRecipesContent() {
        List<RecipeDTO> recipeDTOS = given()
                .contentType("application/json")
                .get("restaurant/all").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .extract()
                .body().jsonPath().getList("", RecipeDTO.class);

        System.out.println(recipeDTO1.getIngredients().get(0).toString());
        System.out.println(recipeDTOS.get(0).getIngredients().get(0).toString());

        assertThat(recipeDTOS, containsInAnyOrder(recipeDTO1, recipeDTO2));
    }
}