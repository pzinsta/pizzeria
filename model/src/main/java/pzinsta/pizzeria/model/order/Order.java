package pzinsta.pizzeria.model.order;

import com.google.common.collect.Range;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.Length;
import org.javamoney.moneta.Money;
import pzinsta.pizzeria.model.Constants;
import pzinsta.pizzeria.model.Manager;
import pzinsta.pizzeria.model.delivery.Delivery;
import pzinsta.pizzeria.model.user.Customer;

import javax.money.MonetaryAmount;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.math.BigDecimal.ZERO;

@Entity
@Table(name = "orders")
public class Order implements Serializable {
    @Id
    @GeneratedValue(generator = Constants.ID_GENERATOR)
	private Long id;
    
    @ManyToOne(optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn (name = "customer_id")
	private Customer customer;

    @OneToOne(mappedBy = "order", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Review review;

	@Fetch(value = FetchMode.SUBSELECT)
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "order", cascade = CascadeType.ALL)
	private List<OrderItem> orderItems = new ArrayList<>();

    @CreationTimestamp
	private Instant placedDate;

    @Enumerated(EnumType.STRING)
	private OrderStatus status;

    // TODO remove this and add order events like paid, ready for pickup/delivery etc. for order tracking
    @ManyToOne(fetch = FetchType.LAZY)
    private Manager manager;

    @OneToOne(mappedBy = "order", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
	private Delivery delivery;

    @Length(max = 1000)
    private String comment;

    @Column(unique = true)
    private String trackNumber;

    @Column(unique = true)
    private String paymentTransactionId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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
		// TODO
		return orderItems.stream().map(OrderItem::getCost).reduce(Money.of(ZERO, "USD"), MonetaryAmount::add);
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public void addOrderItem(OrderItem orderItem) {
		orderItems.add(orderItem);
		orderItem.setOrder(this);
	}

	public void removeOrderItemById(int orderItemIndex) {
		Range<Integer> validIndexes = Range.closedOpen(0, orderItems.size());
		if (validIndexes.contains(orderItemIndex)) {
			orderItems.remove(orderItemIndex);
		}
	}

	public Optional<OrderItem> getOrderItemByIndex(int orderItemIndex) {
        return isIndexPresent(orderItemIndex) ? Optional.of(orderItems.get(orderItemIndex)) : Optional.empty();
	}

    private boolean isIndexPresent(int orderItemIndex) {
        return Range.closedOpen(0, orderItems.size()).contains(orderItemIndex);
    }

    public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}

	public void removeAllOrderItems() {
		orderItems.clear();
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Delivery getDelivery() {
		return delivery;
	}

	public void setDelivery(Delivery delivery) {
		this.delivery = delivery;
	}

	public String getTrackNumber() {
		return trackNumber;
	}

	public void setTrackNumber(String trackNumber) {
		this.trackNumber = trackNumber;
	}

	public Review getReview() {
		return review;
	}

	public void setReview(Review review) {
		this.review = review;
	}

    public String getPaymentTransactionId() {
        return paymentTransactionId;
    }

    public void setPaymentTransactionId(String paymentTransactionId) {
        this.paymentTransactionId = paymentTransactionId;
    }
}
