package dtos;

import entities.Ingredient;

import java.util.Objects;

public class IngredientsDTO {
    private String itemName;
    private double itemPricePrKg;
    private double itemStorage;
    private int amount;

    public IngredientsDTO(Ingredient ingredient) {
        this.itemName = ingredient.getItem().getName();
        this.itemPricePrKg = ingredient.getItem().getPricePrKg();
        this.itemStorage = ingredient.getItem().getStorage();
        this.amount = ingredient.getAmount();
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getItemPricePrKg() {
        return itemPricePrKg;
    }

    public void setItemPricePrKg(double itemPricePrKg) {
        this.itemPricePrKg = itemPricePrKg;
    }

    public double getItemStorage() {
        return itemStorage;
    }

    public void setItemStorage(double itemStorage) {
        this.itemStorage = itemStorage;
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
        if (!(o instanceof IngredientsDTO)) return false;
        IngredientsDTO that = (IngredientsDTO) o;
        return Double.compare(that.itemPricePrKg, itemPricePrKg) == 0 &&
                Double.compare(that.itemStorage, itemStorage) == 0 &&
                amount == that.amount &&
                Objects.equals(itemName, that.itemName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemName, itemPricePrKg, itemStorage, amount);
    }

    @Override
    public String toString() {
        return "IngredientsDTO{" +
                "itemName='" + itemName + '\'' +
                ", itemPricePrKg=" + itemPricePrKg +
                ", itemStorage=" + itemStorage +
                ", amount=" + amount +
                '}';
    }
}
