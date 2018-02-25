package pzinsta.pizzeria.model.pizza;

import java.io.Serializable;
import java.util.Objects;

import javax.money.MonetaryAmount;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
public class PizzaItem {
    
    @Embeddable
    private static class Id implements Serializable {
        
        @Column(name = "PIZZA_SIDE_ID")
        private Long pizzaSideId;
        
        @Column(name = "INGREDIENT_ID")
        private Long ingredientId;
    }
    
    @EmbeddedId
    private Id id = new Id();

    @Min(1)
    @NotNull
    @Column(updatable = false)
	private int quantity;
    
    @ManyToOne
    @JoinColumn(name = "INGREDIENT_ID", insertable = false, updatable = false)
    private Ingredient ingredient;
    
    @ManyToOne
    @JoinColumn(name = "PIZZA_SIDE_ID", insertable = false, updatable = false)
    private PizzaSide pizzaSide;
    
    public PizzaItem(PizzaSide pizzaSide, Ingredient ingredient, int quantity) {
        this.id.pizzaSideId = pizzaSide.getId();
        this.id.ingredientId = ingredient.getId();
        this.quantity = quantity;
        this.setIngredient(ingredient);
    }
    
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
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
	
	public MonetaryAmount getCost() {
		return getIngredient().getPrice().multiply(quantity);
	}

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public PizzaSide getPizzaSide() {
        return pizzaSide;
    }

    public void setPizzaSide(PizzaSide pizzaSide) {
        this.pizzaSide = pizzaSide;
    }

}
