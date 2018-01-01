package pzinsta.pizzeria.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;

import pzinsta.pizzeria.model.order.Order;

@Entity
@PrimaryKeyJoinColumn(name = "registered_user_id")
public class Manager extends RegisteredUser {
    @OneToMany(mappedBy = "manager", fetch = FetchType.LAZY)
	private Set<Order> processedOrders;

	public Set<Order> getProcessedOrders() {
		return processedOrders;
	}

	public void setProcessedOrders(Set<Order> processedOrders) {
		this.processedOrders = processedOrders;
	}
}
