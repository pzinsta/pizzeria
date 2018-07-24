package pzinsta.pizzeria.web.client.dto.delivery;

import java.io.Serializable;

public class Delivery implements Serializable {
    private Long id;
    private DeliveryAddress deliveryAddress;
    private DeliveryStatus status;
    private Long deliverypersonId;
    private Long orderId;

    public DeliveryAddress getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(DeliveryAddress deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public DeliveryStatus getStatus() {
        return status;
    }

    public void setStatus(DeliveryStatus status) {
        this.status = status;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDeliverypersonId() {
        return deliverypersonId;
    }

    public void setDeliverypersonId(Long deliverypersonId) {
        this.deliverypersonId = deliverypersonId;
    }
}
