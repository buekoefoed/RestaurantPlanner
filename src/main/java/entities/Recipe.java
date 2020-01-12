package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = "Recipe")
@Table(name = "recipe")
@NamedQuery(name = "Recipe.deleteAllRows", query = "DELETE from Recipe")
public class Recipe implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "recipe_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "recipe_ingredient_join",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    private List<Ingredient> ingredients = new ArrayList<>();
    private int prepTime;
    private String directions;
    @ManyToMany(mappedBy = "recipes")
    private List<WeekMenu> weekMenus = new ArrayList<>();

    public Recipe() {
    }

    public Recipe(String name, List<Ingredient> ingredients, int prepTime, String directions) {
        this.name = name;
        this.ingredients = ingredients;
        this.prepTime = prepTime;
        this.directions = directions;
    }

    public Recipe(String name, List<Ingredient> ingredients, int prepTime, String directions, List<WeekMenu> weekMenus) {
        this.name = name;
        this.ingredients = ingredients;
        this.prepTime = prepTime;
        this.directions = directions;
        this.weekMenus = weekMenus;
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

    public String getDirections() {
        return directions;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }

    public List<WeekMenu> getWeekMenus() {
        return weekMenus;
    }

    public void setWeekMenus(List<WeekMenu> weekMenus) {
        this.weekMenus = weekMenus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Recipe)) return false;
        Recipe recipe = (Recipe) o;
        return prepTime == recipe.prepTime &&
                Objects.equals(id, recipe.id) &&
                Objects.equals(name, recipe.name) &&
                Objects.equals(ingredients, recipe.ingredients) &&
                Objects.equals(directions, recipe.directions) &&
                Objects.equals(weekMenus, recipe.weekMenus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, ingredients, prepTime, directions, weekMenus);
    }
}
