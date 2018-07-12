package pzinsta.pizzeria.web.client.dto.pizza;

import javax.money.MonetaryAmount;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class PizzaItem implements Serializable {
    private Long id;
    private int quantity;
    private Ingredient ingredient;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public MonetaryAmount getCost() {
        return getIngredient().getPrice().multiply(quantity);
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof PizzaItem)) {
            return false;
        }
        PizzaItem that = (PizzaItem) obj;
        return Objects.equals(this.getIngredient(), that.getIngredient()) && (this.getQuantity() == that.getQuantity());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIngredient(), getQuantity());
    }
}
