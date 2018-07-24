package pzinsta.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.Size;

@Entity
public class PizzaTemplate {
    @Id
    @GeneratedValue(generator = Constants.ID_GENERATOR)
    private Long id;

    @OneToOne(optional = false)
    private Pizza pizza;

    @Size(max = 1000)
    private String name;

    @Size(max = 1000)
    private String description;

    @Size(max = 1000)
    private String imageFileName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Pizza getPizza() {
        return pizza;
    }

    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }
}
