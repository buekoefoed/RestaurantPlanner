package dtos;

import entities.Ingredient;
import entities.Recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RecipeDTO {
    private String name;
    private List<IngredientsDTO> ingredients;
    private int prepTime;
    private String description;

    public RecipeDTO(Recipe recipe) {
        this.name = recipe.getName();
        this.ingredients = convertIngredientsToDTO(recipe);
        //recipe.getIngredients().forEach(ingredient -> this.ingredients.add(new IngredientsDTO(ingredient)));
        this.prepTime = recipe.getPrepTime();
        this.description = recipe.getDescription();
    }

    private List<IngredientsDTO> convertIngredientsToDTO(Recipe recipe) {
        List<IngredientsDTO> ingredientsDTOS = new ArrayList<>();
        recipe.getIngredients().forEach(ingredient -> ingredientsDTOS.add(new IngredientsDTO(ingredient)));
        return ingredientsDTOS;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<IngredientsDTO> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientsDTO> ingredients) {
        this.ingredients = ingredients;
    }

    public int getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(int prepTime) {
        this.prepTime = prepTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RecipeDTO)) return false;
        RecipeDTO recipeDTO = (RecipeDTO) o;
        return prepTime == recipeDTO.prepTime &&
                Objects.equals(name, recipeDTO.name) &&
                Objects.equals(ingredients, recipeDTO.ingredients) &&
                Objects.equals(description, recipeDTO.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, ingredients, prepTime, description);
    }

    @Override
    public String toString() {
        return "RecipeDTO{" +
                "name='" + name + '\'' +
                ", ingredients=" + ingredients +
                ", prepTime=" + prepTime +
                ", description='" + description + '\'' +
                '}';
    }
}
