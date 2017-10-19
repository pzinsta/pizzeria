package pzinsta.pizzeria.service.impl;

import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;

import com.google.common.collect.ImmutableSet;

import pzinsta.pizzeria.dao.UserDao;
import pzinsta.pizzeria.model.Customer;
import pzinsta.pizzeria.model.User;
import pzinsta.pizzeria.service.UserService;

public class UserServiceImpl implements UserService {
	
	private UserDao userDao;
	
	public UserServiceImpl(UserDao userDao) {
		this.userDao = userDao;
	}
	
	@Override
	public Customer createCustomer(User user) {
		User createdUser = userDao.createCustomer(user);
		ImmutableSet<String> roles = ImmutableSet.of("Customer");
		userDao.addRolesToUser(user, roles);
		Customer customer = convertUserToCustomer(createdUser);
		customer.setRoles(roles);
		return customer;
	}

	private Customer convertUserToCustomer(User user) {
		Customer customer = new Customer();
		customer.setId(user.getId());
		customer.setEmail(user.getEmail());
		customer.setFirstName(user.getFirstName());
		customer.setLastName(user.getLastName());
		customer.setPhoneNumber(user.getPhoneNumber());
		customer.setReviews(ImmutableSet.of());
		customer.setOrders(ImmutableSet.of());
		return customer;
	}

	@Override
	public Customer createRegisteredCustomer(User user, String password) {
		User registeredCustomer = userDao.createRegisteredCustomer(user, BCrypt.hashpw(password, BCrypt.gensalt(4)));
		ImmutableSet<String> roles = ImmutableSet.of("Customer", "Registered customer");
		userDao.addRolesToUser(user, roles);
		return convertUserToCustomer(registeredCustomer);
	}

	@Override
	public Optional<User> getRegisteredUserByEmailAndPassword(String email, String password) {
		Optional<User> userOptional = userDao.getUserByEmail(email);
		if (userOptional.isPresent()) {
			User user = userOptional.get();
			Optional<String> hashedPasswordOptional = userDao.getHashedPasswordByUserId(user.getId());
			if (hashedPasswordOptional.isPresent()) {
				String hashedPassword = hashedPasswordOptional.get();
				boolean passwordValid = BCrypt.checkpw(password, hashedPassword);
				return passwordValid ? userOptional : Optional.empty();
			}
		}
		return Optional.empty();
	}


}
