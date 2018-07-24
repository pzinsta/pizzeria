package pzinsta.pizzeria.web.flow.action;

import com.google.common.collect.ImmutableList;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.webflow.action.AbstractAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;
import pzinsta.pizzeria.web.client.CustomerServiceClient;
import pzinsta.pizzeria.web.client.DeliveryServiceClient;
import pzinsta.pizzeria.web.client.OrderServiceClient;
import pzinsta.pizzeria.web.client.dto.delivery.Delivery;
import pzinsta.pizzeria.web.client.dto.delivery.DeliveryAddress;
import pzinsta.pizzeria.web.client.dto.order.Order;
import pzinsta.pizzeria.web.client.dto.user.Customer;
import pzinsta.pizzeria.web.exception.OrderNotFoundException;
import pzinsta.pizzeria.web.model.Cart;

import javax.money.Monetary;
import javax.money.MonetaryAmount;

@Component
public class AssembleOrderAction extends AbstractAction {

    private final static MonetaryAmount ZERO = Monetary.getDefaultAmountFactory().setNumber(0).setCurrency("USD").create();

    private Cart cart;
    private CustomerServiceClient customerServiceClient;
    private OrderServiceClient orderServiceClient;
    private DeliveryServiceClient deliveryServiceClient;

    @Override
    protected Event doExecute(RequestContext context) throws Exception {
        Order order = assembleAndPostOrder(context);
        context.getFlowScope().put("order", order);

        MonetaryAmount orderCost = orderServiceClient.calculateCostById(order.getId())
                .orElseThrow(() -> new OrderNotFoundException(order.getId()));
        context.getFlowScope().put("orderCost", orderCost);

        context.getFlowScope().put("deliveryCost", ZERO);

        if (isDeliveryRequired(context)) {
            Delivery delivery = assembleAndPostDelivery(context, order);
            context.getFlowScope().put("delivery", delivery);

            MonetaryAmount deliveryCost = deliveryServiceClient.calculateCost(delivery);
            context.getFlowScope().put("deliveryCost", deliveryCost);
        }

        return success();
    }

    private Delivery assembleAndPostDelivery(RequestContext context, Order order) {
        Delivery delivery = new Delivery();
        delivery.setDeliveryAddress(getDeliveryAddress(context));
        delivery.setOrderId(order.getId());
        return deliveryServiceClient.create(delivery);
    }

    private Order assembleAndPostOrder(RequestContext context) {
        Customer customer = getCustomer(context);
        Order order = new Order();
        order.setOrderItems(ImmutableList.copyOf(cart.getOrderItems()));
        order.setCustomerId(customer.getId());
        order.setComment(getComment(context));
        return orderServiceClient.post(order);
    }

    private String getComment(RequestContext context) {
        return context.getFlowScope().get("comment", String.class);
    }

    private DeliveryAddress getDeliveryAddress(RequestContext context) {
        pzinsta.pizzeria.web.client.dto.user.DeliveryAddress userDeliveryAddress = context.getFlowScope().get("deliveryAddress", pzinsta.pizzeria.web.client.dto.user.DeliveryAddress.class);
        DeliveryAddress deliveryAddress = new DeliveryAddress();
        BeanUtils.copyProperties(userDeliveryAddress, deliveryAddress);
        return deliveryAddress;
    }

    private Boolean isDeliveryRequired(RequestContext context) {
        return context.getFlowScope().getBoolean("deliveryRequired");
    }

    private Customer getCustomer(RequestContext context) {
        Customer customer = context.getFlowScope().get("customer", Customer.class);
        if (isGuestCustomer(customer)) {
            customer = customerServiceClient.create(customer);
            context.getFlowScope().put("customer", customer);
        }
        return customer;
    }

    private boolean isGuestCustomer(Customer customer) {
        return customer.getId() == null;
    }

    public Cart getCart() {
        return cart;
    }

    @Autowired
    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public CustomerServiceClient getCustomerServiceClient() {
        return customerServiceClient;
    }

    @Autowired
    public void setCustomerServiceClient(CustomerServiceClient customerServiceClient) {
        this.customerServiceClient = customerServiceClient;
    }

    public OrderServiceClient getOrderServiceClient() {
        return orderServiceClient;
    }

    @Autowired
    public void setOrderServiceClient(OrderServiceClient orderServiceClient) {
        this.orderServiceClient = orderServiceClient;
    }

    public DeliveryServiceClient getDeliveryServiceClient() {
        return deliveryServiceClient;
    }

    @Autowired
    public void setDeliveryServiceClient(DeliveryServiceClient deliveryServiceClient) {
        this.deliveryServiceClient = deliveryServiceClient;
    }
}
