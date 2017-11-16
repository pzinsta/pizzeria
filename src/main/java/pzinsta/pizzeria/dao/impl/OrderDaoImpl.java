package pzinsta.pizzeria.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.sql.DataSource;

import pzinsta.pizzeria.dao.OrderDao;
import pzinsta.pizzeria.model.Customer;
import pzinsta.pizzeria.model.order.Order;
import pzinsta.pizzeria.model.order.OrderItem;
import pzinsta.pizzeria.model.order.OrderStatus;
import pzinsta.pizzeria.model.pizza.Pizza;
import pzinsta.pizzeria.util.DatasourceFactory;

public class OrderDaoImpl implements OrderDao {
	private DataSource dataSource = DatasourceFactory.getDataSource();

	private static final String INSERT_INTO_ORDER = "INSERT INTO orders (customer_id, status_id) VALUES (?, ?)";
	private static final String INSERT_INTO_ORDER_PIZZA = "INSERT INTO order_pizza (order_id, pizza_id, quantity) VALUES (?, ?, ?)";
	private static final String SELECT_ORDERS_BY_STATUS = "SELECT * FROM orders WHERE status_id = ?";
	private static final String SELECT_PIZZA_IDS_AND_QUANTITIES_BY_ORDER_ID = "SELECT * FROM order_pizza WHERE order_id = ?";
	private static final String UPDATE_ORDER_STATUS_BY_ID = "UPDATE orders SET status_id = ? WHERE id = ?";
	
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

	@Override
	public Collection<Order> getOrdersByStatus(Set<OrderStatus> orderStatuses) {
		Collection<Order> orders = new LinkedHashSet<>();
		try(Connection connection = dataSource.getConnection()) {
			PreparedStatement selectOrdersByStatusPreparedStatement = connection.prepareStatement(SELECT_ORDERS_BY_STATUS);
			for (OrderStatus orderStatus : orderStatuses) {
				selectOrdersByStatusPreparedStatement.setInt(1, orderStatus.ordinal());
				ResultSet resultSet = selectOrdersByStatusPreparedStatement.executeQuery();
				while(resultSet.next()) {
					Order order = new Order();
					order.setId(resultSet.getLong("id"));
					order.setStatus(orderStatus);
					order.setPlaced(resultSet.getTimestamp("placed").toInstant());
					Customer customer = new Customer();
					customer.setId(resultSet.getLong("customer_id"));
					order.setCustomer(customer);
					
					PreparedStatement selectPizzaIdsAndQuantitiesByOrderId = connection.prepareStatement(SELECT_PIZZA_IDS_AND_QUANTITIES_BY_ORDER_ID);
					selectPizzaIdsAndQuantitiesByOrderId.setLong(1, order.getId());
					ResultSet pizzaIdsAndQuantitiesResultSet = selectPizzaIdsAndQuantitiesByOrderId.executeQuery();
					while (pizzaIdsAndQuantitiesResultSet.next()) {
						OrderItem orderItem = new OrderItem();
						Pizza pizza = new Pizza();
						pizza.setId(pizzaIdsAndQuantitiesResultSet.getLong("pizza_id"));
						orderItem.setPizza(pizza);
						orderItem.setQuantity(pizzaIdsAndQuantitiesResultSet.getInt("quantity"));
						order.addOrderItem(orderItem);
					}
					orders.add(order);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orders;
	}

    @Override
    public void updateOrderStatus(Long orderId, Long statusId) {
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement updateOrderStatusByIdPreparedStatement = connection.prepareStatement(UPDATE_ORDER_STATUS_BY_ID);
            updateOrderStatusByIdPreparedStatement.setLong(1, statusId);
            updateOrderStatusByIdPreparedStatement.setLong(2, orderId);
            updateOrderStatusByIdPreparedStatement.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
