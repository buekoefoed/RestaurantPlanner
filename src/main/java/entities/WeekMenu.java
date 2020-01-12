package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "WeekMenu")
@Table(name = "weekmenu")
@NamedQuery(name = "WeekMenu.deleteAllRows", query = "DELETE from WeekMenu")
public class WeekMenu implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "weekmenu_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "weekmenu_recipe_join",
            joinColumns = @JoinColumn(name = "weekmenu_id"),
            inverseJoinColumns = @JoinColumn(name = "recipe_id")
    )
    private List<Recipe> recipes = new ArrayList<>();
    private int WeekNumber;
    private int Year;

    public WeekMenu() {
    }

    public WeekMenu(List<Recipe> recipes, int weekNumber, int year) {
        this.recipes = recipes;
        WeekNumber = weekNumber;
        Year = year;
    }

    public void addRecipe(Recipe recipe) {
        recipes.add(recipe);
        recipe.getWeekMenus().add(this);
    }

    public void removeRecipe(Recipe recipe) {
        recipes.remove(recipe);
        recipe.getWeekMenus().remove(this);
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> menu) {
        this.recipes = menu;
    }

    public int getWeekNumber() {
        return WeekNumber;
    }

    public void setWeekNumber(int weekNumber) {
        WeekNumber = weekNumber;
    }

    public int getYear() {
        return Year;
    }

    public void setYear(int year) {
        Year = year;
    }
}
