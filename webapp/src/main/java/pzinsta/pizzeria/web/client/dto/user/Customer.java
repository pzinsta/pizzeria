package pzinsta.pizzeria.web.client.dto.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Customer extends User implements Serializable {
    private List<DeliveryAddress> deliveryAddresses = new ArrayList<>();

    public List<DeliveryAddress> getDeliveryAddresses() {
        return deliveryAddresses;
    }
    public void setDeliveryAddresses(List<DeliveryAddress> deliveryAddresses) {
        this.deliveryAddresses = deliveryAddresses;
    }
}
