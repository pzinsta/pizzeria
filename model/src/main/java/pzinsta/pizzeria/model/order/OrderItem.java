package pzinsta.pizzeria.model.order;

import java.util.UUID;

import javax.money.MonetaryAmount;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import pzinsta.pizzeria.model.pizza.Pizza;

@Entity
public class OrderItem {
    @Transient
	private String id; //not to be persisted
    
    @Id
    @ManyToOne(optional = false)
    private Order order;
    
    @Id
    @Column(unique = true)
    @OneToOne
	private Pizza pizza;
	
    @NotNull
    @Min(1)
	private int quantity;

	public OrderItem() {
		id = UUID.randomUUID().toString();
	}
	
	public Pizza getPizza() {
		return pizza;
	}

	public void setPizza(Pizza pizza) {
		this.pizza = pizza;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getId() {
		return id;
	}

	public MonetaryAmount getCost() {
		return pizza.getCost();
	}

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
