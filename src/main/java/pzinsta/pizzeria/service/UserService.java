package pzinsta.pizzeria.service;

import java.util.Optional;

import pzinsta.pizzeria.model.Customer;

public interface UserService {
	Customer createCustomer(Customer customer);

	Customer createRegisteredCustomer(Customer customer, String password);

	Optional<Customer> getRegisteredCustomerByEmailAndPassword(String email, String password);
}
