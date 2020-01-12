package dtos;

import entities.Ingredient;
import entities.Recipe;

import java.util.List;
import java.util.Objects;

public class RecipeDTO {
    private String name;
    private List<Ingredient> ingredients;
    private int prepTime;
    private String description;

    public RecipeDTO(Recipe recipe) {
        this.name = recipe.getName();
        this.ingredients = recipe.getIngredients();
        this.prepTime = recipe.getPrepTime();
        this.description = recipe.getDescription();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
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
}
