package pzinsta.pizzeria.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;

import pzinsta.pizzeria.model.order.Order;

@Entity
@PrimaryKeyJoinColumn(name = "registered_user_id")
public class Deliveryperson extends RegisteredUser {
    @OneToMany(mappedBy = "deliveryperson")
	private Collection<Order> orders = new ArrayList<>();
    
	private double latitude;
	private double longitude;

	public Collection<Order> getOrders() {
		return orders;
	}

	public void setOrders(Collection<Order> orders) {
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
