package pzinsta.pizzeria.web.client.dto.pizza;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class PizzaSide implements Serializable {
	private Long id;
	private String name;
	private Collection<PizzaItem> pizzaItems = new ArrayList<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Collection<PizzaItem> getPizzaItems() {
		return pizzaItems;
	}

	public void setPizzaItems(Collection<PizzaItem> items) {
		this.pizzaItems = items;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof PizzaSide)) return false;
		PizzaSide pizzaSide = (PizzaSide) o;
		return Objects.equals(getPizzaItems(), pizzaSide.getPizzaItems());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getPizzaItems());
	}
}
