package pzinsta.pizzeria.service;

import java.util.Optional;

import pzinsta.pizzeria.model.Customer;
import pzinsta.pizzeria.model.User;

public interface UserService {
	Customer createCustomer(Customer customer);

	Customer createRegisteredCustomer(Customer customer, String password);

	Optional<Customer> getRegisteredCustomerByEmailAndPassword(String email, String password);
	
	void updateCustomer(Customer customer);
	
	Optional<User> getRegisteredStaffUserByEmailAndPassword(String email, String password);

	Optional<Customer> getCustomerById(Long id);
}
