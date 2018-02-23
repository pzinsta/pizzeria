package pzinsta.pizzeria.model.order;

import java.io.Serializable;
import java.util.UUID;

import javax.money.MonetaryAmount;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import pzinsta.pizzeria.model.pizza.Pizza;

@Entity
public class OrderItem implements Serializable {
    @Transient
	private String id; //not to be persisted
    
    @Id
    @ManyToOne(optional = false)
    private Order order;
    
    @Id
    @JoinColumn(unique = true)
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
