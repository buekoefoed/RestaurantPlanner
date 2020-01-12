package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


@Entity(name = "Item")
@Table(name = "item")
@NamedQuery(name = "Item.deleteAllRows", query = "DELETE from Item")
public class Item implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double pricePrKg;
    //Moved from separate entity to attribute
    private double storage;

    public Item() {
    }

    public Item(String name, double pricePrKg, double storage) {
        this.name = name;
        this.pricePrKg = pricePrKg;
        this.storage = storage;
    }

    public Item(String name, double pricePrKg) {
        this.name = name;
        this.pricePrKg = pricePrKg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPricePrKg() {
        return pricePrKg;
    }

    public void setPricePrKg(double pricePrKg) {
        this.pricePrKg = pricePrKg;
    }

    public double getStorage() {
        return storage;
    }

    public void setStorage(double storage) {
        this.storage = storage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        Item item = (Item) o;
        return Double.compare(item.pricePrKg, pricePrKg) == 0 &&
                Double.compare(item.storage, storage) == 0 &&
                Objects.equals(name, item.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, pricePrKg, storage);
    }
}
