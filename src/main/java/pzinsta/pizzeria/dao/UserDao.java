package pzinsta.pizzeria.dao;

import java.util.Optional;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

import pzinsta.pizzeria.model.Customer;
import pzinsta.pizzeria.model.User;
import pzinsta.pizzeria.model.UserRole;

public interface UserDao {
	boolean isEmailPresent(String email);

	Customer createCustomer(Customer customer);

	Customer createRegisteredCustomer(Customer customer, String hashedPassword);

	void addRolesToUser(User user, Set<UserRole> roles);

	Optional<Customer> getCustomerByEmail(String email);

	Optional<String> getHashedPasswordByUserId(long id);

	void updateCustomer(Customer customer);

	Optional<User> getUserByEmail(String email);

	Optional<Customer> getCustomerById(Long id);
}
