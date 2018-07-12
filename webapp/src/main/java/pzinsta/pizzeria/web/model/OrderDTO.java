package pzinsta.pizzeria.web.model;

import java.util.Collection;

public class OrderDTO {
    private Collection<OrderItemDTO> orderItems;
    private String trackingNumber;

    public Collection<OrderItemDTO> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Collection<OrderItemDTO> orderItems) {
        this.orderItems = orderItems;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }
}
