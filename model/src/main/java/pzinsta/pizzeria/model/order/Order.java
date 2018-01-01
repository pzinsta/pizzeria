package pzinsta.pizzeria.model.order;

import static java.math.BigDecimal.ZERO;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import javax.money.MonetaryAmount;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.javamoney.moneta.Money;

import pzinsta.pizzeria.model.Customer;
import pzinsta.pizzeria.model.Constants;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(generator = Constants.ID_GENERATOR)
	private long id;
    
    @ManyToOne(optional = false)
    @JoinColumn (name = "customer_id")
	private Customer customer;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
	private Collection<OrderItem> orderItems = new ArrayList<>();

    @Generated(GenerationTime.INSERT)
    @CreationTimestamp
	private Instant placedDate;
	
    @Enumerated(EnumType.STRING)
	private OrderStatus status;
	
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

	public Instant getPlacedDate() {
		return placedDate;
	}

	public void setPlacedDate(Instant placedDate) {
		this.placedDate = placedDate;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus state) {
		this.status = state;
	}

	public MonetaryAmount getCost() {
		return orderItems.stream().map(OrderItem::getCost).reduce(Money.of(ZERO, "USD"), MonetaryAmount::add);
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
