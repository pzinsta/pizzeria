package pzinsta.pizzeria.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.sql.DataSource;

import pzinsta.pizzeria.dao.UserDao;
import pzinsta.pizzeria.model.Customer;
import pzinsta.pizzeria.model.User;
import pzinsta.pizzeria.model.UserRole;
import pzinsta.pizzeria.util.DatasourceFactory;

public class UserDaoImpl implements UserDao {

	private static final String SELECT_ROLES_BY_USER_ID = "SELECT role_id FROM user_role WHERE user_id = ?";
	private static final String SELECT_USER_COUNT_BY_EMAIL = "SELECT COUNT(id) FROM users WHERE email = ?";
	private static final String INSERT_USER = "INSERT INTO users (first_name, last_name, email, phone_number) VALUES (?, ?, ?, ?)";
	private static final String UPDATE_USER = "UPDATE users SET first_name = ?, last_name = ?, email = ?, phone_number = ? WHERE id = ?";
	private static final String INSERT_REGISTERED_USER = "INSERT INTO registered_user (id, hashed_password) VALUES(?, ?)";
	private static final String INSERT_CUSTOMER = "INSERT INTO customer (user_id, registered_user_id, address) VALUES (?, ?, ?)";
	private static final String UPDATE_CUSTOMER = "UPDATE customer SET address = ? WHERE user_id = ?";
	private static final String INSERT_ROLE_ID_USER_ID = "INSERT INTO user_role (user_id, role_id) VALUES (?, ?)";
	private static final String SELECT_HASHED_PASSWORD_BY_USER_ID = "SELECT hashed_password FROM registered_user WHERE id = ?";
	private static final String SELECT_CUSTOMER_BY_EMAIL = "SELECT * FROM users INNER JOIN registered_user ON users.id = registered_user.id INNER JOIN customer ON users.id = customer.user_id WHERE users.email = ?";
	private static final String UPDATE_CUSTOMER_REGISTERED_USER_ID = "UPDATE customer SET registered_user_id = user_id WHERE user_id = ?";
	private static final String SELECT_USER_BY_EMAIL = "SELECT * FROM users INNER JOIN registered_user ON users.id = registered_user.id WHERE users.email = ?";
	private static final String SELECT_CUSTOMER_BY_ID = "SELECT * FROM users INNER JOIN customer ON users.id = customer.user_id WHERE users.id = ?";
	
	private DataSource dataSource = DatasourceFactory.getDataSource();

	private Set<UserRole> getUserRoles(long id, Connection connection) throws SQLException {
		Set<UserRole> roles = new HashSet<>();
		try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ROLES_BY_USER_ID)) {
			preparedStatement.setLong(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				roles.add(UserRole.values()[(int) resultSet.getLong("role_id")]);
			}
		}
		return roles;
	}

	@Override
	public boolean isEmailPresent(String email) {
		try (Connection connection = dataSource.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_COUNT_BY_EMAIL)) {
			preparedStatement.setString(1, email);
			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.next();
			return resultSet.getInt(1) > 0;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Customer createCustomer(Customer customer) {
		try (Connection connection = dataSource.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER,
						Statement.RETURN_GENERATED_KEYS)) {
			preparedStatement.setString(1, customer.getFirstName());
			preparedStatement.setString(2, customer.getLastName());
			preparedStatement.setString(3, customer.getEmail());
			preparedStatement.setString(4, customer.getPhoneNumber());
			preparedStatement.executeUpdate();
			ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
			generatedKeys.next();
			customer.setId(generatedKeys.getLong(1));

			PreparedStatement preparedStatement2 = connection.prepareStatement(INSERT_CUSTOMER);
			preparedStatement2.setLong(1, customer.getId());
			preparedStatement2.setNull(2, Types.INTEGER);
			preparedStatement2.setString(3, customer.getAddress());
			preparedStatement2.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return customer;
	}

	@Override
	public Customer createRegisteredCustomer(Customer customer, String hashedPassword) {
		customer = createCustomer(customer);
		try (Connection connection = dataSource.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_REGISTERED_USER)) {
			preparedStatement.setLong(1, customer.getId());
			preparedStatement.setString(2, hashedPassword);
			preparedStatement.executeUpdate();

			PreparedStatement preparedStatement2 = connection.prepareStatement(UPDATE_CUSTOMER_REGISTERED_USER_ID);
			preparedStatement2.setLong(1, customer.getId());
			preparedStatement2.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return customer;
	}

	@Override
	public void addRolesToUser(User user, Set<UserRole> roles) {
		try (Connection connection = dataSource.getConnection();
				PreparedStatement insertRoles = connection.prepareStatement(INSERT_ROLE_ID_USER_ID);) {
			for(UserRole role : roles) {
				insertRoles.setLong(1, user.getId());
				insertRoles.setLong(2, role.ordinal());
				insertRoles.executeUpdate();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Optional<Customer> getCustomerByEmail(String email) {
		try (Connection connection = dataSource.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CUSTOMER_BY_EMAIL)) {
			preparedStatement.setString(1, email);
			preparedStatement.setMaxRows(1);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return extractCustomerFromResultSet(connection, resultSet);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Optional.empty();
	}

	private Optional<Customer> extractCustomerFromResultSet(Connection connection, ResultSet resultSet)
			throws SQLException {
		Customer customer = new Customer();
		customer.setId(resultSet.getLong("id"));
		customer.setFirstName(resultSet.getString("first_name"));
		customer.setLastName(resultSet.getString("last_name"));
		customer.setPhoneNumber(resultSet.getString("phone_number"));
		customer.setAddress(resultSet.getString("address"));
		customer.setEmail(resultSet.getString("email"));
		customer.setRoles(getUserRoles(customer.getId(), connection));
		return Optional.ofNullable(customer);
	}

	@Override
	public Optional<String> getHashedPasswordByUserId(long id) {
		try (Connection connection = dataSource.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_HASHED_PASSWORD_BY_USER_ID)) {
			preparedStatement.setLong(1, id);
			preparedStatement.setMaxRows(1);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return Optional.ofNullable(resultSet.getString("hashed_password"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Optional.empty();
	}

	@Override
	public void updateCustomer(Customer customer) {
		try(Connection connection = dataSource.getConnection()) {
			PreparedStatement updateUserPreparedStatement = connection.prepareStatement(UPDATE_USER);
			updateUserPreparedStatement.setString(1, customer.getFirstName());
			updateUserPreparedStatement.setString(2, customer.getLastName());
			updateUserPreparedStatement.setString(3, customer.getEmail());
			updateUserPreparedStatement.setString(4, customer.getPhoneNumber());
			updateUserPreparedStatement.setLong(5, customer.getId());
			updateUserPreparedStatement.executeUpdate();
			
			PreparedStatement updateCustomerPreparedStatement = connection.prepareStatement(UPDATE_CUSTOMER);
			updateCustomerPreparedStatement.setString(1, customer.getAddress());
			updateCustomerPreparedStatement.setLong(2, customer.getId());
			updateCustomerPreparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public Optional<User> getUserByEmail(String email) {
		try (Connection connection = dataSource.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_EMAIL)) {
			preparedStatement.setString(1, email);
			preparedStatement.setMaxRows(1);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				User user = new User();
				user.setId(resultSet.getLong("id"));
				user.setFirstName(resultSet.getString("first_name"));
				user.setLastName(resultSet.getString("last_name"));
				user.setPhoneNumber(resultSet.getString("phone_number"));
				user.setEmail(resultSet.getString("email"));
				user.setRoles(getUserRoles(user.getId(), connection));
				return Optional.ofNullable(user);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Optional.empty();
	}

	@Override
	public Optional<Customer> getCustomerById(Long id) {
		try(Connection connection = dataSource.getConnection()) {
			PreparedStatement selectCustomerByIdPreparedStatement = connection.prepareStatement(SELECT_CUSTOMER_BY_ID);
			selectCustomerByIdPreparedStatement.setLong(1, id);
			selectCustomerByIdPreparedStatement.setMaxRows(1);
			ResultSet resultSet = selectCustomerByIdPreparedStatement.executeQuery();
			if (resultSet.next()) {
				return extractCustomerFromResultSet(connection, resultSet);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Optional.empty();
	}

}
