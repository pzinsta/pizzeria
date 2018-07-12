package pzinsta.pizzeria.web.client.dto.pizza;

import javax.money.MonetaryAmount;
import java.io.Serializable;
import java.util.Objects;

public class PizzaSize implements Serializable {
	private Long id;
	private String name;
    private int diameterInInches;
	private MonetaryAmount price;
	private double ingredientCostFactor;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MonetaryAmount getPrice() {
		return price;
	}

	public void setPrice(MonetaryAmount price) {
		this.price = price;
	}

	public int getDiameterInInches() {
		return diameterInInches;
	}

	public void setDiameterInInches(int diameterInInches) {
		this.diameterInInches = diameterInInches;
	}

	public double getIngredientCostFactor() {
		return ingredientCostFactor;
	}

	public void setIngredientCostFactor(double ingredientCostFactor) {
		this.ingredientCostFactor = ingredientCostFactor;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof PizzaSize)) return false;
		PizzaSize pizzaSize = (PizzaSize) o;
		return Objects.equals(getName(), pizzaSize.getName()) &&
				Objects.equals(getPrice(), pizzaSize.getPrice());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getName(), getPrice());
	}
}
