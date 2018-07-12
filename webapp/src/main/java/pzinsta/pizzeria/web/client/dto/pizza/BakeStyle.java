package pzinsta.pizzeria.web.client.dto.pizza;

import java.io.Serializable;
import java.util.Objects;

public class BakeStyle implements Serializable {
	private Long id;
	private String name;

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

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof BakeStyle)) return false;
		BakeStyle bakeStyle = (BakeStyle) o;
		return Objects.equals(getName(), bakeStyle.getName());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getName());
	}

}
