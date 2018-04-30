package pzinsta.pizzeria.model.pizza;

import org.hibernate.annotations.Check;
import pzinsta.pizzeria.model.Constants;

import javax.money.MonetaryAmount;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.io.Serializable;

@Entity
@Check(constraints = "left_pizzaside_id <> right_pizzaside_id")
public class Pizza implements Serializable {
    @Id
    @GeneratedValue(generator = Constants.ID_GENERATOR)
	private Long id;
    
    @ManyToOne
	private Crust crust;
    
    @ManyToOne
	private PizzaSize size;

    @JoinColumn(unique = true, name = "left_pizzaside_id")
    @OneToOne(cascade = CascadeType.ALL)
	private PizzaSide leftPizzaSide;
    
    @JoinColumn(unique = true, name = "right_pizzaside_id")
    @OneToOne(cascade = CascadeType.ALL)
	private PizzaSide rightPizzaSide;

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
	
	public MonetaryAmount getCost() {
		return crust.getPrice().add(leftPizzaSide.getCost()).add(rightPizzaSide.getCost());
	}
}
