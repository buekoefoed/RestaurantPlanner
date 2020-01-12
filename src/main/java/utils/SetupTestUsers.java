package utils;


import entities.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;

public class SetupTestUsers {

    public static void main(String[] args) {

        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.DEV, EMF_Creator.Strategy.CREATE);

        persistMenuEntities(emf);
        persistUserEntities(emf);
    }

    private static void persistMenuEntities(EntityManagerFactory emf) {
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
    }

    private static void persistUserEntities(EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();

        // IMPORTAAAAAAAAAANT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        // This breaks one of the MOST fundamental security rules in that it ships with default users and passwords
        // CHANGE the three passwords below, before you uncomment and execute the code below
        // Also, either delete this file, when users are created or rename and add to .gitignore
        // Whatever you do DO NOT COMMIT and PUSH with the real passwords

        User user = new User("user", "test");
        User admin = new User("admin", "test");
        User both = new User("user_admin", "test");

        if (admin.getUserPass().equals("test") || user.getUserPass().equals("test") || both.getUserPass().equals("test"))
            throw new UnsupportedOperationException("You have not changed the passwords");

        em.getTransaction().begin();
        Role userRole = new Role("user");
        Role adminRole = new Role("admin");
        user.addRole(userRole);
        admin.addRole(adminRole);
        both.addRole(userRole);
        both.addRole(adminRole);
        em.persist(userRole);
        em.persist(adminRole);
        em.persist(user);
        em.persist(admin);
        em.persist(both);
        em.getTransaction().commit();
        System.out.println("PW: " + user.getUserPass());
        System.out.println("Testing user with OK password: " + user.verifyPassword("test"));
        System.out.println("Testing user with wrong password: " + user.verifyPassword("test1"));
        System.out.println("Created TEST Users");
    }
}
