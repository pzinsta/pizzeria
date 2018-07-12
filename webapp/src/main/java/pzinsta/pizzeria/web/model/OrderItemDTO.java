package pzinsta.pizzeria.web.model;

import pzinsta.pizzeria.web.client.dto.pizza.Pizza;

public class OrderItemDTO {
    private Pizza pizza;
    private int quantity;

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
}
