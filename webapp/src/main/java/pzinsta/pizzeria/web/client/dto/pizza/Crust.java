package pzinsta.pizzeria.web.client.dto.pizza;

import javax.money.MonetaryAmount;
import java.io.Serializable;
import java.util.Objects;

public class Crust implements Serializable {
	private Long id;
	private String name;
	private MonetaryAmount price;

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

	public void setPrice(MonetaryAmount cost) {
		this.price = cost;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Crust)) return false;
		Crust crust = (Crust) o;
		return Objects.equals(getName(), crust.getName()) &&
				Objects.equals(getPrice(), crust.getPrice());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getName(), getPrice());
	}
}
