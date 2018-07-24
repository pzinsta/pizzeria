package pzinsta.model;

import com.google.common.collect.Range;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.Length;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "orders")
public class Order implements Serializable {
    @Id
    @GeneratedValue(generator = Constants.ID_GENERATOR)
	private Long id;

    @NotNull
	private Long customerId;

	@Fetch(value = FetchMode.SUBSELECT)
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<OrderItem> orderItems = new ArrayList<>();

    @Length(max = 1000)
    private String comment;

    @Column(unique = true)
    private String trackingNumber;

    // todo remove and implement the payment service
    @Column(unique = true)
    private String paymentTransactionId;

    // todo remove and replace with the status enum
	@Fetch(value = FetchMode.SUBSELECT)
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Collection<OrderEvent> orderEvents = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public void addOrderItem(OrderItem orderItem) {
		orderItems.add(orderItem);
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

	public void removeAllOrderItems() {
		orderItems.clear();
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getTrackingNumber() {
		return trackingNumber;
	}

	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}

    public String getPaymentTransactionId() {
        return paymentTransactionId;
    }

    public void setPaymentTransactionId(String paymentTransactionId) {
        this.paymentTransactionId = paymentTransactionId;
    }

	public Collection<OrderEvent> getOrderEvents() {
		return orderEvents;
	}

	public void setOrderEvents(Collection<OrderEvent> orderEvents) {
		this.orderEvents = orderEvents;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
}
