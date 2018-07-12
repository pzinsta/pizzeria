package pzinsta.pizzeria.web.client.dto.pizza;

import java.io.Serializable;
import java.util.Objects;

public class CutStyle implements Serializable {
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
		if (!(o instanceof CutStyle)) return false;
		CutStyle cutStyle = (CutStyle) o;
		return Objects.equals(getName(), cutStyle.getName());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getName());
	}
}
