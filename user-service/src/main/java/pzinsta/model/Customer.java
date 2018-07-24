package pzinsta.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@PrimaryKeyJoinColumn(name = "user_id")
public class Customer extends User implements Serializable {
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(FetchMode.SELECT)
    private List<DeliveryAddress> deliveryAddresses = new ArrayList<>();

    public List<DeliveryAddress> getDeliveryAddresses() {
        return deliveryAddresses;
    }

    public void setDeliveryAddresses(List<DeliveryAddress> deliveryAddresses) {
        this.deliveryAddresses = deliveryAddresses;
    }
}
