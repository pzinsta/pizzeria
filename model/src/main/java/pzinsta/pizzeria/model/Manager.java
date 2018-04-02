package pzinsta.pizzeria.model;

import pzinsta.pizzeria.model.order.Order;
import pzinsta.pizzeria.model.user.User;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import java.util.Set;

@Entity
@PrimaryKeyJoinColumn(name = "user_id")
public class Manager extends User {

    @OneToMany(mappedBy = "manager", fetch = FetchType.LAZY)
	private Set<Order> processedOrders;

	public Set<Order> getProcessedOrders() {
		return processedOrders;
	}

	public void setProcessedOrders(Set<Order> processedOrders) {
		this.processedOrders = processedOrders;
	}
}
