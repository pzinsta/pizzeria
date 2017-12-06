package pzinsta.pizzeria.service.impl;

import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;

import com.google.common.collect.ImmutableSet;

import pzinsta.pizzeria.dao.UserDao;
import pzinsta.pizzeria.model.Customer;
import pzinsta.pizzeria.model.User;
import pzinsta.pizzeria.model.UserRole;
import pzinsta.pizzeria.service.UserService;

public class UserServiceImpl implements UserService {

	private UserDao userDao;

	public UserServiceImpl(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public Customer createCustomer(Customer customer) {
		Customer createdCustomer = userDao.createCustomer(customer);
		ImmutableSet<UserRole> roles = ImmutableSet.of(UserRole.UNREGISTERED_CUSTOMER);
		userDao.addRolesToUser(createdCustomer, roles);
		createdCustomer.setReviews(ImmutableSet.of());
		createdCustomer.setOrders(ImmutableSet.of());
		createdCustomer.setRoles(roles);
		return createdCustomer;
	}

	@Override
	public Customer createRegisteredCustomer(Customer customer, String password) {
		Customer registeredCustomer = userDao.createRegisteredCustomer(customer,
				BCrypt.hashpw(password, BCrypt.gensalt(4)));
		ImmutableSet<UserRole> roles = ImmutableSet.of(UserRole.UNREGISTERED_CUSTOMER, UserRole.REGISTERED_CUSTOMER);
		userDao.addRolesToUser(customer, roles);
		customer.setReviews(ImmutableSet.of());
		customer.setOrders(ImmutableSet.of());
		return registeredCustomer;
	}

	@Override
	public Optional<Customer> getRegisteredCustomerByEmailAndPassword(String email, String password) {
		Optional<Customer> customerOptional = userDao.getCustomerByEmail(email);
		if (customerOptional.isPresent()) {
			Customer customer = customerOptional.get();
			Optional<String> hashedPasswordOptional = userDao.getHashedPasswordByUserId(customer.getId());
			if (hashedPasswordOptional.isPresent()) {
				String hashedPassword = hashedPasswordOptional.get();
				boolean passwordValid = BCrypt.checkpw(password, hashedPassword);
				return passwordValid ? customerOptional : Optional.empty();
			}
		}
		return Optional.empty();
	}

	@Override
	public void updateCustomer(Customer customer) {
		userDao.updateCustomer(customer);
	}

	@Override
	public Optional<User> getRegisteredStaffUserByEmailAndPassword(String email, String password) {
		Optional<User> userOptional = userDao.getUserByEmail(email).filter(user -> user.getRoles().contains(UserRole.MANAGER));
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

	@Override
	public Optional<Customer> getCustomerById(Long id) {
		return userDao.getCustomerById(id);
	}
}
