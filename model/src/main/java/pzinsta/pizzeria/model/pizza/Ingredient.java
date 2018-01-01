package pzinsta.pizzeria.model.pizza;

import java.util.Objects;

import javax.money.MonetaryAmount;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import pzinsta.pizzeria.model.Constants;

@Entity
public class Ingredient {
    @Id
    @GeneratedValue(generator = Constants.ID_GENERATOR)
	private Long id;
    
    @NotNull
    @Size(max = 100)
    @Column(unique = true)
	private String name;
    
    //TODO : mapping
	private MonetaryAmount price;
	
	@NotNull
	@ManyToOne
	private IngredientType ingredientType;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
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

	public void setPrice(MonetaryAmount price) {
		this.price = price;
	}

	public IngredientType getIngredientType() {
		return ingredientType;
	}

	public void setIngredientType(IngredientType type) {
		this.ingredientType = type;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Ingredient)) {
			return false;
		}
		Ingredient that = (Ingredient) obj;
		return this.getId() == that.getId();
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
