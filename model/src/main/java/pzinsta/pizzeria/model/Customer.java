package pzinsta.pizzeria.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;

import org.hibernate.validator.constraints.Length;

import pzinsta.pizzeria.model.order.Order;

@Entity
@PrimaryKeyJoinColumn(name = "user_id")
public class Customer extends User {
    @OneToMany (mappedBy = "customer", fetch = FetchType.LAZY)
	private Set<Order> orders;
    
    @OneToMany (mappedBy = "customer", fetch = FetchType.LAZY)
	private Set<Review> reviews;
    
    @Length(max = 1000)
	private String address;
	
	public Set<Order> getOrders() {
		return orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}

	public Set<Review> getReviews() {
		return reviews;
	}

	public void setReviews(Set<Review> reviews) {
		this.reviews = reviews;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
