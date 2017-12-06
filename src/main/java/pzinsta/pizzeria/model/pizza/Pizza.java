package pzinsta.pizzeria.model.pizza;

import javax.money.MonetaryAmount;

public class Pizza {
	private long id;

	private Crust crust;
	private PizzaSize size;

	private PizzaSide left;
	private PizzaSide right;

	private BakeStyle bakeStyle;
	private CutStyle cutStyle;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Crust getCrust() {
		return crust;
	}

	public void setCrust(Crust crust) {
		this.crust = crust;
	}

	public PizzaSide getLeft() {
		return left;
	}

	public void setLeft(PizzaSide left) {
		this.left = left;
	}

	public PizzaSide getRight() {
		return right;
	}

	public void setRight(PizzaSide right) {
		this.right = right;
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
	
	public MonetaryAmount getCost() {
	    //TODO include pizza's size into the calculation instead of 1
		return crust.getPrice().add(left.getCost()).add(right.getCost()).multiply(1);
	}
}
