package pzinsta.exception;

public class DeliveryNotFoundException extends RuntimeException {
    public DeliveryNotFoundException(Long id) {
        this(String.format("Delivery with ID %s not found.", id));
    }

    public DeliveryNotFoundException(String message) {
        super(message);
    }

    public static DeliveryNotFoundException withOrderId(Long orderId) {
        return new DeliveryNotFoundException(String.format("Delivery for order with ID %s not found.", orderId));
    }
}
