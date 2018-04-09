package pzinsta.pizzeria.service;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException() {
    }

    public CustomerNotFoundException(String message) {
        super(message);
    }

    public CustomerNotFoundException(Long customerId) {
        this("Customer with id " + customerId + "was not found.");
    }
}
