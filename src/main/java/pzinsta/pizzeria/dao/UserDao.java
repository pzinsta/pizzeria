package pzinsta.pizzeria.dao;

import java.util.Optional;
import java.util.Set;

import pzinsta.pizzeria.model.User;

public interface UserDao {
	boolean isEmailPresent(String email);
	User createCustomer(User user);
	User createRegisteredCustomer(User user, String hashedPassword);
	void addRolesToUser(User user, Set<String> roles);
	Optional<User> getUserByEmail(String email);
	Optional<String> getHashedPasswordByUserId(long id);
}
