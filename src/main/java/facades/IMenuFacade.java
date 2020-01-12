package facades;

import dtos.RecipeDTO;
import entities.Recipe;

import java.util.List;

public interface IMenuFacade {

    List<RecipeDTO> getAllRecipes();

    List<RecipeDTO> getRecipesByName(String name);

    RecipeDTO createRecipe(RecipeDTO recipeDTO);

    RecipeDTO updateRecipe(RecipeDTO recipeDTO);

    RecipeDTO deleteRecipe(RecipeDTO recipeDTO);
}
