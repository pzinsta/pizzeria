package pzinsta.pizzeria.dao;

import java.util.Optional;
import java.util.Set;

import pzinsta.pizzeria.model.Customer;
import pzinsta.pizzeria.model.User;

public interface UserDao {
	boolean isEmailPresent(String email);

	Customer createCustomer(Customer customer);

	Customer createRegisteredCustomer(Customer customer, String hashedPassword);

	void addRolesToUser(User user, Set<String> roles);

	Optional<Customer> getCustomerByEmail(String email);

	Optional<String> getHashedPasswordByUserId(long id);

	void updateCustomer(Customer customer);
}
