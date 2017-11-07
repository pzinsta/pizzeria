package pzinsta.pizzeria.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import pzinsta.pizzeria.dao.OrderDao;
import pzinsta.pizzeria.model.order.Order;
import pzinsta.pizzeria.util.DatasourceFactory;

public class OrderDaoImpl implements OrderDao {
	private DataSource dataSource = DatasourceFactory.getDataSource();

	private static final String INSERT_INTO_ORDER = "INSERT INTO orders (customer_id, status_id) VALUES (?, ?)";
	private static final String INSERT_INTO_ORDER_PIZZA = "INSERT INTO order_pizza (order_id, pizza_id, quantity) VALUES (?, ?, ?)";

	@Override
	public void saveOrder(Order order) {
		try (Connection connection = dataSource.getConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_ORDER,
					Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setLong(1, order.getCustomer().getId());
			preparedStatement.setLong(2, order.getStatus().ordinal());
			preparedStatement.executeUpdate();
			ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
			generatedKeys.next();
			order.setId(generatedKeys.getLong(1));

			PreparedStatement preparedStatement2 = connection.prepareStatement(INSERT_INTO_ORDER_PIZZA);
			preparedStatement2.setLong(1, order.getId());
			order.getOrderItems().stream().forEach(o -> {
				try {
					preparedStatement2.setLong(2, o.getPizza().getId());
					preparedStatement2.setInt(3, o.getQuantity());
					preparedStatement2.executeUpdate();
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
