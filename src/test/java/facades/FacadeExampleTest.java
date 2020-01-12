package facades;

import entities.Ingredient;
import entities.Item;
import entities.Recipe;
import entities.WeekMenu;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;
import utils.EMF_Creator.DbSelector;
import utils.EMF_Creator.Strategy;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

//Uncomment the line below, to temporarily disable this test
@Disabled
public class FacadeExampleTest {

    private static EntityManagerFactory emf;
    private static FacadeExample facade;

    public FacadeExampleTest() {
    }

    //@BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactory(
                "pu",
                "jdbc:mysql://localhost:3307/startcode_test",
                "dev",
                "ax2",
                EMF_Creator.Strategy.CREATE);
        facade = FacadeExample.getFacadeExample(emf);
    }

    /*   **** HINT **** 
        A better way to handle configuration values, compared to the UNUSED example above, is to store those values
        ONE COMMON place accessible from anywhere.
        The file config.properties and the corresponding helper class utils.Settings is added just to do that. 
        See below for how to use these files. This is our RECOMENDED strategy
     */
    @BeforeAll
    public static void setUpClassV2() {
        emf = EMF_Creator.createEntityManagerFactory(DbSelector.TEST, Strategy.DROP_AND_CREATE);
        facade = FacadeExample.getFacadeExample(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the script below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();


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
        recipes.add(recipe1);
        recipes.add(recipe1);
        recipes.add(recipe1);
        recipes.add(recipe2);
        recipes.add(recipe2);
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
    }

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }

    // TODO: Delete or change this method 
    @Test
    public void testAFacadeMethod() {
        assertEquals(2, facade.getRenameMeCount(), "Expects two rows in the database");
    }

}
