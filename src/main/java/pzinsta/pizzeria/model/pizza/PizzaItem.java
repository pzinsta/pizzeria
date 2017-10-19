package pzinsta.pizzeria.model.pizza;

import java.util.Objects;

public class PizzaItem {
	private Ingredient ingredient;
	private int quantity;

	public Ingredient getIngredient() {
		return ingredient;
	}

	public void setIngredient(Ingredient ingredient) {
		this.ingredient = ingredient;
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
}
