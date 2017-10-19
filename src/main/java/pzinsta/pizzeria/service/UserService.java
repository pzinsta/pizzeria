package pzinsta.pizzeria.service;

import java.util.Optional;
import java.util.Set;

import pzinsta.pizzeria.model.Customer;
import pzinsta.pizzeria.model.User;

public interface UserService {
	Customer createCustomer(User user);
	Customer createRegisteredCustomer(User user, String password);
	Optional<User> getRegisteredUserByEmailAndPassword(String email, String password);
}
