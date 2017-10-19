package pzinsta.pizzeria.model.pizza;

import java.util.Objects;

public class IngredientType {
	private long id;
	private String name;

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
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof IngredientType)) {
			return false;
		}
		IngredientType that = (IngredientType) obj;
		return this.getId() == that.getId();
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
