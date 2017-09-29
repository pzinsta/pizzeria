package pzinsta.pizzeria.model;

import java.util.Set;

import pzinsta.pizzeria.model.order.Order;

public class Deliveryperson {
	private Set<Order> orders;
	private double latitude;
	private double longitude;

	public Set<Order> getOrders() {
		return orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
}
