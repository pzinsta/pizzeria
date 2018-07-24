package pzinsta.exception;

public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(Long id) {
        this(String.format("Order with ID %s not found.", id));
    }

    public OrderNotFoundException(String message) {
        super(message);
    }

    public static OrderNotFoundException withTrackingNumber(String trackingNumber) {
        return new OrderNotFoundException(String.format("Order with tracking number %s not found.", trackingNumber));
    }
}
