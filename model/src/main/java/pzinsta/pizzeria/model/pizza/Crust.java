package pzinsta.pizzeria.model.pizza;

import pzinsta.pizzeria.model.Constants;
import pzinsta.pizzeria.model.MonetaryAmountAttributeConverter;

import javax.money.MonetaryAmount;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
public class Crust  implements Serializable {
    @Id
    @GeneratedValue(generator = Constants.ID_GENERATOR)
	private Long id;
    
    @Size(max = 100)
    @NotNull
    @Column(unique = true)
	private String name;
	
    @NotNull
	@Convert(converter = MonetaryAmountAttributeConverter.class)
	private MonetaryAmount price;

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

	public void setPrice(MonetaryAmount cost) {
		this.price = cost;
	}
}
