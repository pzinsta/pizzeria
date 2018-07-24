package pzinsta.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
public class Delivery implements Serializable {

    @Id
    @GeneratedValue(generator = Constants.ID_GENERATOR)
    private Long id;

    private DeliveryAddress deliveryAddress;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    private Long deliverypersonId;

    @NotNull
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
