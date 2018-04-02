package pzinsta.pizzeria.model.user;

import pzinsta.pizzeria.model.delivery.Delivery;
import pzinsta.pizzeria.model.order.Order;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@PrimaryKeyJoinColumn(name = "user_id")
public class Customer extends User implements Serializable {
    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    private Collection<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private Collection<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private Collection<Delivery> deliveries = new ArrayList<>();

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private Collection<Address> deliveryAddresses = new ArrayList<>();

    public Collection<Order> getOrders() {
        return orders;
    }

    public void setOrders(Collection<Order> orders) {
        this.orders = orders;
    }

    public Collection<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Collection<Review> reviews) {
        this.reviews = reviews;
    }

    public Collection<Delivery> getDeliveries() {
        return deliveries;
    }

    public void setDeliveries(Collection<Delivery> deliveries) {
        this.deliveries = deliveries;
    }

    public Collection<Address> getDeliveryAddresses() {
        return deliveryAddresses;
    }

    public void setDeliveryAddresses(Collection<Address> deliveryAddresses) {
        this.deliveryAddresses = deliveryAddresses;
    }
}
