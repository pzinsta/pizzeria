package pzinsta.pizzeria.web.client.dto.order;

import com.google.common.collect.Range;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class Order implements Serializable {
	private Long id;
	private Long customerId;
	private List<OrderItem> orderItems = new ArrayList<>();
    private String comment;
    private String trackingNumber;
    private String paymentTransactionId;
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
