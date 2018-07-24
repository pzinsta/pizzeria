package pzinsta.pizzeria.web.exception;

public class DeliveryAddressNotFoundException extends RuntimeException {

    public DeliveryAddressNotFoundException(Long id) {
        this(String.format("Delivery address with ID %s not found.", id));
    }

    public DeliveryAddressNotFoundException(String message) {
        super(message);
    }
}
