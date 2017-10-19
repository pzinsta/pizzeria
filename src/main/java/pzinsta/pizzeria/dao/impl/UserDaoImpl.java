package pzinsta.pizzeria.dao.impl;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.sql.DataSource;

import com.google.common.collect.ImmutableSet;

import pzinsta.pizzeria.dao.UserDao;
import pzinsta.pizzeria.model.User;
import pzinsta.pizzeria.util.DatasourceFactory;

public class UserDaoImpl implements UserDao {

	private static final String SELECT_ROLES_BY_USER_ID = "SELECT name FROM role WHERE id IN (SELECT role_id FROM user_role WHERE user_id = ?)";
	private static final String SELECT_USER_COUNT_BY_EMAIL = "SELECT COUNT(id) FROM users WHERE email = ?";
	private static final String INSERT_USER = "INSERT INTO users (first_name, last_name, email, phone_number) VALUES (?, ?, ?, ?)";
	private static final String INSERT_REGISTERED_USER = "INSERT INTO registered_user (id, hashed_password) VALUES(?, ?)";
	private static final String SELECT_ROLE_ID_BY_NAME = "SELECT id FROM role where name = ANY (?)";
	private static final String INSERT_ROLE_ID_USER_ID = "INSERT INTO user_role (user_id, role_id) VALUES (?, ?)";
	private static final String SELECT_HASHED_PASSWORD_BY_USER_ID = "SELECT hashed_password FROM registered_user WHERE id = ?";
	private static final String SELECT_USER_BY_EMAIL= "SELECT * FROM users INNER JOIN registered_user ON users.id = registered_user.id WHERE users.email = ?";
	
	private DataSource dataSource = DatasourceFactory.getDataSource();

	private Set<String> getUserRoles(long id, Connection connection) throws SQLException {
		Set<String> roles = new HashSet<>();
		try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ROLES_BY_USER_ID)) {
			preparedStatement.setLong(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				roles.add(resultSet.getString("name"));
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
	public User createCustomer(User user) {
		try (Connection connection = dataSource.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER,
						Statement.RETURN_GENERATED_KEYS)) {
			preparedStatement.setString(1, user.getFirstName());
			preparedStatement.setString(2, user.getLastName());
			preparedStatement.setString(3, user.getEmail());
			preparedStatement.setString(4, user.getPhoneNumber());
			preparedStatement.executeUpdate();
			ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
			generatedKeys.next();
			user.setId(generatedKeys.getLong(1));
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return user;
	}

	@Override
	public User createRegisteredCustomer(User user, String hashedPassword) {
		User customer = createCustomer(user);
		try (Connection connection = dataSource.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_REGISTERED_USER)) {
			preparedStatement.setLong(1, user.getId());
			preparedStatement.setString(2, hashedPassword);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return customer;
	}

	@Override
	public void addRolesToUser(User user, Set<String> roles) {
		try (Connection connection = dataSource.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ROLE_ID_BY_NAME)) {
			preparedStatement.setArray(1, connection.createArrayOf("varchar", roles.toArray()));
			ResultSet resultSet = preparedStatement.executeQuery();
			PreparedStatement insertRoles = connection.prepareStatement(INSERT_ROLE_ID_USER_ID);
			insertRoles.setLong(1, user.getId());
			while (resultSet.next()) {
				insertRoles.setLong(2, resultSet.getLong("id"));
				insertRoles.executeUpdate();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

}
