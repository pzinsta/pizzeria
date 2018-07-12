package pzinsta.pizzeria.web.client.dto.pizza;

import java.io.Serializable;
import java.util.Objects;

public class Pizza implements Serializable {
	private Long id;
	private Crust crust;
	private PizzaSize size;
	private PizzaSide leftPizzaSide;
	private PizzaSide rightPizzaSide;
	private BakeStyle bakeStyle;
	private CutStyle cutStyle;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Crust getCrust() {
		return crust;
	}

	public void setCrust(Crust crust) {
		this.crust = crust;
	}

	public PizzaSide getLeftPizzaSide() {
		return leftPizzaSide;
	}

	public void setLeftPizzaSide(PizzaSide left) {
		this.leftPizzaSide = left;
	}

	public PizzaSide getRightPizzaSide() {
		return rightPizzaSide;
	}

	public void setRightPizzaSide(PizzaSide right) {
		this.rightPizzaSide = right;
	}

	public BakeStyle getBakeStyle() {
		return bakeStyle;
	}

	public void setBakeStyle(BakeStyle bakeStyle) {
		this.bakeStyle = bakeStyle;
	}

	public CutStyle getCutStyle() {
		return cutStyle;
	}

	public void setCutStyle(CutStyle cutStyle) {
		this.cutStyle = cutStyle;
	}

	public PizzaSize getSize() {
		return size;
	}

	public void setSize(PizzaSize size) {
		this.size = size;
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Pizza)) return false;
		Pizza pizza = (Pizza) o;
		return Objects.equals(getCrust(), pizza.getCrust()) &&
				Objects.equals(getSize(), pizza.getSize()) &&
				Objects.equals(getLeftPizzaSide(), pizza.getLeftPizzaSide()) &&
				Objects.equals(getRightPizzaSide(), pizza.getRightPizzaSide()) &&
				Objects.equals(getBakeStyle(), pizza.getBakeStyle()) &&
				Objects.equals(getCutStyle(), pizza.getCutStyle());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getCrust(), getSize(), getLeftPizzaSide(), getRightPizzaSide(), getBakeStyle(), getCutStyle());
	}
}
