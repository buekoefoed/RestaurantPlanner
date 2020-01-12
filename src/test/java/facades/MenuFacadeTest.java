package facades;

import dtos.RecipeDTO;
import entities.Ingredient;
import entities.Item;
import entities.Recipe;
import entities.WeekMenu;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.*;

class MenuFacadeTest {

    private static EntityManagerFactory emf;
    private static MenuFacade facade;

    private static RecipeDTO recipeDTO1, recipeDTO2;

    public MenuFacadeTest() {
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @BeforeAll
    static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.TEST, EMF_Creator.Strategy.DROP_AND_CREATE);
        facade = MenuFacade.getMenuFacade(emf);
    }

    @AfterAll
    static void tearDownClass() {
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

        List<Recipe> recipes = new ArrayList<>();
        recipes.add(recipe1);
        recipes.add(recipe2);

        WeekMenu weekMenu = new WeekMenu(recipes, 1, 2020);

        try {
            em.getTransaction().begin();
            em.createNamedQuery("WeekMenu.deleteAllRows").executeUpdate();
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

    @Test
    void getMenuFacade() {
    }

    @Test
    void getAllRecipesSize() {
        List<RecipeDTO> recipeDTOS = facade.getAllRecipes();
        assertEquals(2, recipeDTOS.size());
    }

    @Test
    void getAllRecipesContent() {
        List<RecipeDTO> recipeDTOS = facade.getAllRecipes();
        assertThat(recipeDTOS, containsInAnyOrder(recipeDTO1, recipeDTO2));
    }

    @Test
    void getRecipesByName() {
        List<RecipeDTO> recipeDTOS = facade.getRecipesByName("Onion Soup");
        assertEquals(1, recipeDTOS.size());
        assertThat(recipeDTOS, containsInAnyOrder(recipeDTO1));
    }

    @Test
    void getRecipesByNameFalse() {
        List<RecipeDTO> recipeDTOS = facade.getRecipesByName("Pizza");
        assertEquals(0, recipeDTOS.size());
    }

    @Test
    void createRecipe() {
    }

    @Test
    void updateRecipe() {
    }

    @Test
    void deleteRecipe() {
    }
}