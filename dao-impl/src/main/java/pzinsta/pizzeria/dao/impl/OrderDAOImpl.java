package pzinsta.pizzeria.dao.impl;

import org.springframework.stereotype.Repository;
import pzinsta.pizzeria.dao.OrderDAO;
import pzinsta.pizzeria.model.order.Order;

@Repository
public class OrderDAOImpl extends GenericDAOImpl<Order, Long> implements OrderDAO {
    public OrderDAOImpl() {
        super(Order.class);
    }
}
