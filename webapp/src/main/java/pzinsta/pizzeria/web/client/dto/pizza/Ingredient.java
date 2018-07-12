package pzinsta.pizzeria.web.client.dto.pizza;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.money.MonetaryAmount;
import java.io.Serializable;
import java.util.Objects;
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class Ingredient implements Serializable {
	private Long id;
	private String name;
	private MonetaryAmount price;
	private IngredientType ingredientType;
	private String imageFileName;

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

	public void setPrice(MonetaryAmount price) {
		this.price = price;
	}

	public IngredientType getIngredientType() {
		return ingredientType;
	}

	public void setIngredientType(IngredientType type) {
		this.ingredientType = type;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
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
