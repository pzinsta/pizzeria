package pzinsta.pizzeria.web.client.dto.order;

import java.io.Serializable;
import java.time.Instant;

public class OrderEvent implements Serializable {
    private Long id;
    private Instant occurredOn;
    private OrderEventType orderEventType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getOccurredOn() {
        return occurredOn;
    }

    public void setOccurredOn(Instant occurredOn) {
        this.occurredOn = occurredOn;
    }

    public OrderEventType getOrderEventType() {
        return orderEventType;
    }

    public void setOrderEventType(OrderEventType orderEventType) {
        this.orderEventType = orderEventType;
    }
}
