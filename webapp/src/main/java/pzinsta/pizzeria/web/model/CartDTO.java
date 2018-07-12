package pzinsta.pizzeria.web.model;

import pzinsta.pizzeria.web.client.dto.pizza.Pizza;

import java.io.Serializable;
import java.util.Collection;

public class CartDTO implements Serializable {
    private Collection<CartItem> cartItems;

    public Collection<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(Collection<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public static class CartItem implements Serializable {
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

}
