package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = "Recipe")
@Table(name = "recipe")
@NamedQueries(value = {
        @NamedQuery(name = "Recipe.getAllRows", query = "SELECT Recipe FROM Recipe Recipe"),
        @NamedQuery(name = "Recipe.deleteAllRows", query = "DELETE from Recipe")
})
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
    private String description;
    @ManyToMany(mappedBy = "recipes")
    private List<WeekMenu> weekMenus = new ArrayList<>();

    public Recipe() {
    }

    public Recipe(String name, List<Ingredient> ingredients, int prepTime, String description) {
        this.name = name;
        this.ingredients = ingredients;
        this.prepTime = prepTime;
        this.description = description;
    }

    public Recipe(String name, List<Ingredient> ingredients, int prepTime, String description, List<WeekMenu> weekMenus) {
        this.name = name;
        this.ingredients = ingredients;
        this.prepTime = prepTime;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String directions) {
        this.description = directions;
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
                Objects.equals(description, recipe.description) &&
                Objects.equals(weekMenus, recipe.weekMenus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, ingredients, prepTime, description, weekMenus);
    }
}
