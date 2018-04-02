package pzinsta.pizzeria.service;

import pzinsta.pizzeria.model.user.Customer;

import java.util.Optional;

public interface UserService {
    Optional<Customer> getCustomerByUsername(String username);

    Customer createNewCustomer();
}
