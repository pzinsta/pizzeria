package pzinsta.pizzeria.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pzinsta.pizzeria.dao.CustomerDAO;
import pzinsta.pizzeria.dao.OrderDAO;
import pzinsta.pizzeria.dao.PizzaTemplateDAO;
import pzinsta.pizzeria.model.order.Cart;
import pzinsta.pizzeria.model.order.Order;
import pzinsta.pizzeria.model.order.OrderEvent;
import pzinsta.pizzeria.model.order.OrderEventType;
import pzinsta.pizzeria.model.order.OrderItem;
import pzinsta.pizzeria.model.order.PizzaTemplate;
import pzinsta.pizzeria.service.OrderService;
import pzinsta.pizzeria.service.dto.PizzaOrderDTO;
import pzinsta.pizzeria.service.exception.OrderNotFoundException;
import pzinsta.pizzeria.service.impl.strategy.TrackingNumberGenerationStrategy;
import pzinsta.pizzeria.web.client.PizzaServiceClient;
import pzinsta.pizzeria.web.client.dto.pizza.BakeStyle;
import pzinsta.pizzeria.web.client.dto.pizza.Crust;
import pzinsta.pizzeria.web.client.dto.pizza.CutStyle;
import pzinsta.pizzeria.web.client.dto.pizza.Ingredient;
import pzinsta.pizzeria.web.client.dto.pizza.Pizza;
import pzinsta.pizzeria.web.client.dto.pizza.PizzaItem;
import pzinsta.pizzeria.web.client.dto.pizza.PizzaSide;
import pzinsta.pizzeria.web.client.dto.pizza.PizzaSize;
import pzinsta.pizzeria.web.model.CartDTO;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

@Service("orderService")
public class OrderServiceImpl implements OrderService {

    private OrderDAO orderDAO;
    private CustomerDAO customerDAO;
    private PizzaTemplateDAO pizzaTemplateDAO;
    private PizzaServiceClient pizzaServiceClient;

    private Cart cart;

    @Value("${pizza.quantity.min}")
    private int minQuantity;

    @Value("${pizza.quantity.max}")
    private int maxQuantity;

    private TrackingNumberGenerationStrategy trackingNumberGenerationStrategy;

    @Override
    public Collection<Integer> getQuantities() {
        return IntStream.rangeClosed(minQuantity, maxQuantity).boxed().collect(toList());
    }

    @Override
    public void addOrderItemToCart(PizzaOrderDTO pizzaOrderDTO) {
        cart.addOrderItem(createOrderItem(pizzaOrderDTO));
    }

    @Override
    public void removeOrderItem(int orderItemIndex) {
        cart.removeOrderItemById(orderItemIndex);
    }

    @Override
    public void emptyCart() {
        cart.reset();
    }

    @Override
    public void replaceOrderItem(int orderItemIndex, PizzaOrderDTO pizzaOrderDTO) {
        cart.removeOrderItemById(orderItemIndex);
        cart.addOrderItem(createOrderItem(pizzaOrderDTO));
    }

    @Override
    public PizzaOrderDTO getPizzaOrderDtoByPizzaTemplateId(int orderItemIndex) {
        return createPizzaOrderDTO(cart.getOrderItemById(orderItemIndex).orElseThrow(RuntimeException::new));
    }

    @Override
    @Transactional
    public Order postOrder(Order order) {
        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setOccurredOn(Instant.now());
        orderEvent.setOrderEventType(OrderEventType.PURCHASED);
        order.getOrderEvents().add(orderEvent);
        order = orderDAO.saveOrUpdate(order);
        order.setTrackingNumber(trackingNumberGenerationStrategy.generatetrackingNumber(order));
        return order;
    }

    @Override
    @Transactional(readOnly = true)
    public Order getOrderByTrackingNumber(String trackingNumber) {
        return orderDAO.findByTrackingNumber(trackingNumber).orElseThrow(OrderNotFoundException::new);
    }

    @Override
    @Transactional
    public void addReviewToOrderByTrackingNumber(String trackingNumber, Long reviewId) {
        Order order = orderDAO.findByTrackingNumber(trackingNumber).orElseThrow(OrderNotFoundException::new);
        order.setReviewId(reviewId);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PizzaOrderDTO> getPizzaOrderDtoByPizzaTemplateId(Long pizzaTemplateId) {
        return pizzaTemplateDAO.findById(pizzaTemplateId)
                .map(PizzaTemplate::getPizzaId)
                .map(pizzaServiceClient::findPizzaById)
                .map(this::createPizzaOrderDTO);
    }

    public CartDTO getCartDTO() {
        CartDTO cartDTO = new CartDTO();
        List<CartDTO.CartItem> cartItems = cart.getOrderItems().stream().map(orderItem -> {
            CartDTO.CartItem cartItem = new CartDTO.CartItem();
            Pizza pizza = pizzaServiceClient.findPizzaById(orderItem.getPizzaId());
            cartItem.setPizza(pizza);
            cartItem.setQuantity(orderItem.getQuantity());
            return cartItem;
        }).collect(Collectors.toList());
        cartDTO.setCartItems(cartItems);
        return cartDTO;
    }

    private PizzaOrderDTO createPizzaOrderDTO(OrderItem orderItem) {
        Pizza pizza = pizzaServiceClient.findPizzaById(orderItem.getPizzaId());

        PizzaOrderDTO pizzaOrderDTO = createPizzaOrderDTO(pizza);
        pizzaOrderDTO.setId(orderItem.getId());
        pizzaOrderDTO.setQuantity(orderItem.getQuantity());

        return pizzaOrderDTO;
    }

    private PizzaOrderDTO createPizzaOrderDTO(Pizza pizza) {
        PizzaOrderDTO pizzaOrderDTO = new PizzaOrderDTO();
        pizzaOrderDTO.setQuantity(1);

        pizzaOrderDTO.setBakeStyleId(pizza.getBakeStyle().getId());
        pizzaOrderDTO.setCrustId(pizza.getCrust().getId());
        pizzaOrderDTO.setCutStyleId(pizza.getCutStyle().getId());
        pizzaOrderDTO.setPizzaSizeId(pizza.getSize().getId());

        pizzaOrderDTO.setLeftSideIngredientIdByQuantity(getIngredientsByQuantity(pizza.getLeftPizzaSide()));
        pizzaOrderDTO.setRightSideIngredientIdByQuantity(getIngredientsByQuantity(pizza.getRightPizzaSide()));

        return pizzaOrderDTO;
    }

    private Map<Long, Integer> getIngredientsByQuantity(PizzaSide pizzaSide) {
        return pizzaSide.getPizzaItems().stream().collect(Collectors.toMap(pizzaItem -> pizzaItem.getIngredient().getId(), PizzaItem::getQuantity));
    }

    private OrderItem createOrderItem(PizzaOrderDTO pizzaOrderDTO) {
        OrderItem orderItem = new OrderItem();
        Pizza pizza = createPizza(pizzaOrderDTO);
        orderItem.setPizzaId(pizza.getId());
        orderItem.setQuantity(pizzaOrderDTO.getQuantity());
        return orderItem;
    }

    private Pizza createPizza(PizzaOrderDTO pizzaOrderDTO) {
        Pizza pizza = new Pizza();

        pizza.setBakeStyle(getBakeStyle(pizzaOrderDTO.getBakeStyleId()));
        pizza.setCrust(getCrust(pizzaOrderDTO.getCrustId()));
        pizza.setCutStyle(getCutStyle(pizzaOrderDTO.getCutStyleId()));
        pizza.setSize(getPizzaSize(pizzaOrderDTO.getPizzaSizeId()));

        pizza.setLeftPizzaSide(createPizzaSide(pizzaOrderDTO.getLeftSideIngredientIdByQuantity()));
        pizza.setRightPizzaSide(createPizzaSide(pizzaOrderDTO.getRightSideIngredientIdByQuantity()));

        return pizzaServiceClient.savePizza(pizza);
    }

    private PizzaSize getPizzaSize(Long pizzaSizeId) {
        return pizzaServiceClient.findPizzaSizeById(pizzaSizeId);
    }

    private CutStyle getCutStyle(Long cutStyleId) {
        return pizzaServiceClient.findCutStyleById(cutStyleId);
    }

    private Crust getCrust(Long crustId) {
        return pizzaServiceClient.findCrustById(crustId);
    }

    private BakeStyle getBakeStyle(Long bakeStyleId) {
        return pizzaServiceClient.findBakeStyleById(bakeStyleId);
    }

    private PizzaSide createPizzaSide(Map<Long, Integer> ingredientIdByQuantity) {
        PizzaSide pizzaSide = new PizzaSide();
        List<PizzaItem> pizzaItems = ingredientIdByQuantity.entrySet().stream()
                .map(this::createPizzaItem).collect(toList());
        pizzaSide.setPizzaItems(pizzaItems);
        return pizzaSide;
    }

    private PizzaItem createPizzaItem(Map.Entry<Long, Integer> longIntegerEntry) {
        Ingredient ingredient = pizzaServiceClient.findIngredientById(longIntegerEntry.getKey());
        PizzaItem pizzaItem = new PizzaItem();
        pizzaItem.setQuantity(longIntegerEntry.getValue());
        pizzaItem.setIngredient(ingredient);
        return pizzaItem;
    }

    public int getMaxQuantity() {
        return maxQuantity;
    }

    public void setMaxQuantity(int maxQuantity) {
        this.maxQuantity = maxQuantity;
    }

    public int getMinQuantity() {
        return minQuantity;
    }

    public void setMinQuantity(int minQuantity) {
        this.minQuantity = minQuantity;
    }

    public OrderDAO getOrderDAO() {
        return orderDAO;
    }

    @Autowired
    public void setOrderDAO(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    public Cart getCart() {
        return cart;
    }

    @Autowired
    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public CustomerDAO getCustomerDAO() {
        return customerDAO;
    }

    @Autowired
    public void setCustomerDAO(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    public TrackingNumberGenerationStrategy getTrackingNumberGenerationStrategy() {
        return trackingNumberGenerationStrategy;
    }

    @Autowired
    public void setTrackingNumberGenerationStrategy(TrackingNumberGenerationStrategy trackingNumberGenerationStrategy) {
        this.trackingNumberGenerationStrategy = trackingNumberGenerationStrategy;
    }

    public PizzaTemplateDAO getPizzaTemplateDAO() {
        return pizzaTemplateDAO;
    }

    @Autowired
    public void setPizzaTemplateDAO(PizzaTemplateDAO pizzaTemplateDAO) {
        this.pizzaTemplateDAO = pizzaTemplateDAO;
    }

    public PizzaServiceClient getPizzaServiceClient() {
        return pizzaServiceClient;
    }

    @Autowired
    public void setPizzaServiceClient(PizzaServiceClient pizzaServiceClient) {
        this.pizzaServiceClient = pizzaServiceClient;
    }
}
