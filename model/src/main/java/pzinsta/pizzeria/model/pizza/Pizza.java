package pzinsta.pizzeria.model.pizza;

import javax.money.MonetaryAmount;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Check;

import pzinsta.pizzeria.model.Constants;

@Entity
@Check(constraints = "left <> right")
public class Pizza {
    @Id
    @GeneratedValue(generator = Constants.ID_GENERATOR)
	private Long id;
    
    @ManyToOne
	private Crust crust;
    
    @ManyToOne
	private PizzaSize size;

    @Column(unique = true)
    @OneToOne
	private PizzaSide left;
    
    @Column(unique = true)
    @OneToOne
	private PizzaSide right;

	@ManyToOne
	private BakeStyle bakeStyle;
	
	@ManyToOne
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
