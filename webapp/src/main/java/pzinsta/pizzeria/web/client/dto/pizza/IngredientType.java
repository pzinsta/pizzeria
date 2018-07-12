package pzinsta.pizzeria.web.client.dto.pizza;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class IngredientType implements Serializable {
	private Long id;
	private String name;
    private Collection<Ingredient> ingredients = new ArrayList<>();
    
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

    public Collection<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Collection<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof IngredientType)) {
			return false;
		}
		IngredientType that = (IngredientType) obj;
		return this.getId() == that.getId();
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
