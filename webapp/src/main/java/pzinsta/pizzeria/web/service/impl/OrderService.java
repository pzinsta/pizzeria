package pzinsta.pizzeria.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pzinsta.pizzeria.web.client.PizzaServiceClient;
import pzinsta.pizzeria.web.client.dto.order.OrderItem;
import pzinsta.pizzeria.web.client.dto.pizza.BakeStyle;
import pzinsta.pizzeria.web.client.dto.pizza.Crust;
import pzinsta.pizzeria.web.client.dto.pizza.CutStyle;
import pzinsta.pizzeria.web.client.dto.pizza.Ingredient;
import pzinsta.pizzeria.web.client.dto.pizza.Pizza;
import pzinsta.pizzeria.web.client.dto.pizza.PizzaItem;
import pzinsta.pizzeria.web.client.dto.pizza.PizzaSide;
import pzinsta.pizzeria.web.client.dto.pizza.PizzaSize;
import pzinsta.pizzeria.web.client.dto.pizza.PizzaTemplate;
import pzinsta.pizzeria.web.exception.BakeStyleNotFoundException;
import pzinsta.pizzeria.web.exception.CrustNotFoundException;
import pzinsta.pizzeria.web.exception.CutStyleNotFoundException;
import pzinsta.pizzeria.web.exception.IngredientNotFoundException;
import pzinsta.pizzeria.web.exception.PizzaNotFoundException;
import pzinsta.pizzeria.web.exception.PizzaSizeNotFoundException;
import pzinsta.pizzeria.web.model.Cart;
import pzinsta.pizzeria.web.model.CartDTO;
import pzinsta.pizzeria.web.model.PizzaOrderDTO;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

@Service("orderService")
public class OrderService {

    private PizzaServiceClient pizzaServiceClient;

    private Cart cart;

    @Value("${pizza.quantity.min}")
    private int minQuantity;

    @Value("${pizza.quantity.max}")
    private int maxQuantity;

    public Collection<Integer> getQuantities() {
        return IntStream.rangeClosed(minQuantity, maxQuantity).boxed().collect(toList());
    }

    public void addOrderItemToCart(PizzaOrderDTO pizzaOrderDTO) {
        cart.addOrderItem(createOrderItem(pizzaOrderDTO));
    }

    public void removeOrderItem(int orderItemIndex) {
        cart.removeOrderItemById(orderItemIndex);
    }

    public void emptyCart() {
        cart.reset();
    }

    public void replaceOrderItem(int orderItemIndex, PizzaOrderDTO pizzaOrderDTO) {
        cart.removeOrderItemById(orderItemIndex);
        cart.addOrderItem(createOrderItem(pizzaOrderDTO));
    }

    public PizzaOrderDTO getPizzaOrderDtoByPizzaTemplateId(int orderItemIndex) {
        return createPizzaOrderDTO(cart.getOrderItemById(orderItemIndex).orElseThrow(RuntimeException::new));
    }

    @Transactional(readOnly = true)
    public Optional<PizzaOrderDTO> getPizzaOrderDtoByPizzaTemplateId(Long pizzaTemplateId) {
        return pizzaServiceClient.findPizzaTemplateById(pizzaTemplateId)
                .map(PizzaTemplate::getPizza)
                .map(this::createPizzaOrderDTO);
    }

    public CartDTO getCartDTO() {
        CartDTO cartDTO = new CartDTO();
        List<CartDTO.CartItem> cartItems = cart.getOrderItems().stream()
                .map(this::transformToCartItem)
                .collect(Collectors.toList());
        cartDTO.setCartItems(cartItems);
        return cartDTO;
    }

    private CartDTO.CartItem transformToCartItem(OrderItem orderItem) {
        CartDTO.CartItem cartItem = new CartDTO.CartItem();
        Pizza pizza = pizzaServiceClient.findPizzaById(orderItem.getPizzaId())
                .orElseThrow(() -> new PizzaNotFoundException(orderItem.getPizzaId()));
        cartItem.setPizza(pizza);
        cartItem.setQuantity(orderItem.getQuantity());
        return cartItem;
    }

    private PizzaOrderDTO createPizzaOrderDTO(OrderItem orderItem) {
        Pizza pizza = pizzaServiceClient.findPizzaById(orderItem.getPizzaId())
                .orElseThrow(() -> new PizzaNotFoundException(orderItem.getPizzaId()));

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
        return pizzaServiceClient.findPizzaSizeById(pizzaSizeId)
                .orElseThrow(() -> new PizzaSizeNotFoundException(pizzaSizeId));
    }

    private CutStyle getCutStyle(Long cutStyleId) {
        return pizzaServiceClient.findCutStyleById(cutStyleId)
                .orElseThrow(() -> new CutStyleNotFoundException(cutStyleId));
    }

    private Crust getCrust(Long crustId) {
        return pizzaServiceClient.findCrustById(crustId)
                .orElseThrow(() -> new CrustNotFoundException(crustId));
    }

    private BakeStyle getBakeStyle(Long bakeStyleId) {
        return pizzaServiceClient.findBakeStyleById(bakeStyleId)
                .orElseThrow(() -> new BakeStyleNotFoundException(bakeStyleId));
    }

    private PizzaSide createPizzaSide(Map<Long, Integer> ingredientIdByQuantity) {
        PizzaSide pizzaSide = new PizzaSide();
        List<PizzaItem> pizzaItems = ingredientIdByQuantity.entrySet().stream()
                .map(this::createPizzaItem).collect(toList());
        pizzaSide.setPizzaItems(pizzaItems);
        return pizzaSide;
    }

    private PizzaItem createPizzaItem(Map.Entry<Long, Integer> ingredientIdQuantityEntry) {
        Ingredient ingredient = pizzaServiceClient.findIngredientById(ingredientIdQuantityEntry.getKey())
                .orElseThrow(() -> new IngredientNotFoundException(ingredientIdQuantityEntry.getKey()));
        PizzaItem pizzaItem = new PizzaItem();
        pizzaItem.setQuantity(ingredientIdQuantityEntry.getValue());
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

    public Cart getCart() {
        return cart;
    }

    @Autowired
    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public PizzaServiceClient getPizzaServiceClient() {
        return pizzaServiceClient;
    }

    @Autowired
    public void setPizzaServiceClient(PizzaServiceClient pizzaServiceClient) {
        this.pizzaServiceClient = pizzaServiceClient;
    }
}
