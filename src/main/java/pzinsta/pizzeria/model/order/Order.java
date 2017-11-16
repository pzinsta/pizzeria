package pzinsta.pizzeria.model.order;

import static java.math.BigDecimal.ZERO;
import static pzinsta.pizzeria.util.Utils.fromBigDecimal;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

import javax.money.MonetaryAmount;

import org.apache.commons.lang3.StringUtils;

import pzinsta.pizzeria.model.Customer;

public class Order {
	private long id;
	private Customer customer;
	private Collection<OrderItem> orderItems = new ArrayList<>();
	private Instant placed;
	private OrderStatus status; // refactor to the State pattern?

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Instant getPlaced() {
		return placed;
	}

	public void setPlaced(Instant placedDate) {
		this.placed = placedDate;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus state) {
		this.status = state;
	}

	public MonetaryAmount getCost() {
		return orderItems.stream().map(OrderItem::getCost).reduce(fromBigDecimal(ZERO), MonetaryAmount::add);
	}

	public Collection<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void addOrderItem(OrderItem orderItem) {
		orderItems.add(orderItem);
	}

	public void removeOrderItemById(String orderItemId) {
		orderItems.removeIf(item -> StringUtils.equals(orderItemId, item.getId()));
	}

	public Optional<OrderItem> getOrderItemById(String orderItemId) {
		return orderItems.stream().filter(item -> StringUtils.equals(orderItemId, item.getId())).findFirst();
	}
}
