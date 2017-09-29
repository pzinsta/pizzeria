package pzinsta.pizzeria.model.pizza;

import java.util.Collection;

public class PizzaSide {
	private long id;
	private String name;
	private Collection<PizzaItem> items;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Collection<PizzaItem> getItems() {
		return items;
	}

	public void setItems(Collection<PizzaItem> items) {
		this.items = items;
	}

}
