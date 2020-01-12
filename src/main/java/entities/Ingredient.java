package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = "Ingredient")
@Table(name = "ingredient")
@NamedQuery(name = "Ingredient.deleteAllRows", query = "DELETE from Ingredient ")
public class Ingredient implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "ingredient_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //List of all recipes the ingredient is used in
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "ingredients")
    private List<Recipe> recipes = new ArrayList<>();
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id")
    private Item item;
    private int amount;

    public Ingredient() {
    }

    public Ingredient(Item item, int amount) {
        this.item = item;
        this.amount = amount;
    }

    public Ingredient(List<Recipe> recipes, Item item, int amount) {
        this.recipes = recipes;
        this.item = item;
        this.amount = amount;
    }

    public void addRecipe(Recipe recipe) {
        this.recipes.add(recipe);
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ingredient)) return false;
        Ingredient that = (Ingredient) o;
        return amount == that.amount &&
                Objects.equals(recipes, that.recipes) &&
                Objects.equals(item, that.item);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipes, item, amount);
    }
}
