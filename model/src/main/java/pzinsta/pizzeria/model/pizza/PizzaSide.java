package pzinsta.pizzeria.model.pizza;

import static java.math.BigDecimal.ZERO;

import java.util.ArrayList;
import java.util.Collection;

import javax.money.MonetaryAmount;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.ColumnDefault;
import org.javamoney.moneta.Money;

import pzinsta.pizzeria.model.Constants;

@Entity
public class PizzaSide {
    @Id
    @GeneratedValue(generator = Constants.ID_GENERATOR)
	private Long id;
    
    @NotNull
    @Size(max = 100)
    @ColumnDefault("Custom")
	private String name;
    
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "pizzaSide")
	private Collection<PizzaItem> pizzaItems = new ArrayList<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Collection<PizzaItem> getPizzaItems() {
		return pizzaItems;
	}

	public void setPizzaItems(Collection<PizzaItem> items) {
		this.pizzaItems = items;
	}
	
	public boolean containsIngredient(Long ingredientId) {
		return pizzaItems.stream().map(pizzaItem -> pizzaItem.getIngredient().getId()).anyMatch(i -> i == ingredientId);
	}

	public boolean containsDoubledIngredient(Long ingredientId) {
		return pizzaItems.stream().anyMatch(pizzaItem -> pizzaItem.getIngredient().getId() == ingredientId && pizzaItem.getQuantity() == 2);
	}
	
	public MonetaryAmount getCost() {
		return pizzaItems.stream().map(PizzaItem::getCost).reduce(Money.of(ZERO, "USD"), MonetaryAmount::add);
	}
}
