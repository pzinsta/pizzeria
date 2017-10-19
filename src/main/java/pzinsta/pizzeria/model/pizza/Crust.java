package pzinsta.pizzeria.model.pizza;

import javax.money.MonetaryAmount;

public class Crust {
	private long id;
	private String name;
	private MonetaryAmount price;

	public long getId() {
		return id;
	}

	public void setId(long id) {
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

	public void setPrice(MonetaryAmount cost) {
		this.price = cost;
	}
}