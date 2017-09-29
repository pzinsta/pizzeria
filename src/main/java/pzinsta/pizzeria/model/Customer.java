package pzinsta.pizzeria.model;

import java.util.Set;

import pzinsta.pizzeria.model.order.Order;

public class Customer extends User {
	private Set<Order> orders;
	private Set<Review> reviews;
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
