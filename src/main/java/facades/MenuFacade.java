package facades;

import dtos.RecipeDTO;
import entities.Recipe;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;

public class MenuFacade implements IMenuFacade {

    private static MenuFacade instance;
    private static EntityManagerFactory emf;

    private MenuFacade() {
    }

    public static MenuFacade getMenuFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new MenuFacade();
        }
        return instance;
    }

    @Override
    public List<RecipeDTO> getAllRecipes() {
        EntityManager em = emf.createEntityManager();
        try {
            List<RecipeDTO> recipeDTOS = new ArrayList<>();
            List<Recipe> recipes = em.createNamedQuery("Recipe.getAllRows", Recipe.class).getResultList();
            recipes.forEach(recipe -> recipeDTOS.add(new RecipeDTO(recipe)));
            return recipeDTOS;
        } finally {
            em.close();
        }
    }

    @Override
    public List<RecipeDTO> getRecipesByName() {
        return null;
    }

    @Override
    public RecipeDTO createRecipe(RecipeDTO recipeDTO) {
        return null;
    }

    @Override
    public RecipeDTO updateRecipe(RecipeDTO recipeDTO) {
        return null;
    }

    @Override
    public RecipeDTO deleteRecipe(RecipeDTO recipeDTO) {
        return null;
    }
}
