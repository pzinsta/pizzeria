package pzinsta.pizzeria.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pzinsta.pizzeria.dao.BakeStyleDAO;
import pzinsta.pizzeria.dao.CrustDAO;
import pzinsta.pizzeria.dao.CutStyleDAO;
import pzinsta.pizzeria.dao.IngredientDAO;
import pzinsta.pizzeria.dao.PizzaSizeDAO;
import pzinsta.pizzeria.model.order.Order;
import pzinsta.pizzeria.model.order.OrderItem;
import pzinsta.pizzeria.model.pizza.BakeStyle;
import pzinsta.pizzeria.model.pizza.Crust;
import pzinsta.pizzeria.model.pizza.CutStyle;
import pzinsta.pizzeria.model.pizza.Ingredient;
import pzinsta.pizzeria.model.pizza.Pizza;
import pzinsta.pizzeria.model.pizza.PizzaItem;
import pzinsta.pizzeria.model.pizza.PizzaSide;
import pzinsta.pizzeria.model.pizza.PizzaSize;
import pzinsta.pizzeria.service.PizzaBuilderService;
import pzinsta.pizzeria.service.dto.PizzaOrderDTO;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

@Service
public class PizzaBuilderServiceImpl implements PizzaBuilderService {

    private CrustDAO crustDAO;
    private PizzaSizeDAO pizzaSizeDAO;
    private BakeStyleDAO bakeStyleDAO;
    private CutStyleDAO cutStyleDAO;
    private IngredientDAO ingredientDAO;

    private Order order;

    @Value("${pizza.quantity.min}")
    private int minQuantity;

    @Value("${pizza.quantity.max}")
    private int maxQuantity;

    @Override
    @Transactional(readOnly = true)
    public Collection<Crust> getCrusts() {
        return crustDAO.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<PizzaSize> getPizzaSizes() {
        return pizzaSizeDAO.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<BakeStyle> getBakeStyles() {
        return bakeStyleDAO.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<CutStyle> getCutStyles() {
        return cutStyleDAO.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Ingredient> getIngredients() {
        return ingredientDAO.findAll();
    }

    @Override
    public Collection<Integer> getQuantities() {
        return IntStream.rangeClosed(minQuantity, maxQuantity).boxed().collect(toList());
    }

    @Override
    public void addOrderItemToOrder(PizzaOrderDTO pizzaOrderDTO) {
        order.addOrderItem(createOrderItem(pizzaOrderDTO));
    }

    private OrderItem createOrderItem(PizzaOrderDTO pizzaOrderDTO) {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(UUID.randomUUID().toString());
        orderItem.setOrder(order);
        orderItem.setPizza(createPizza(pizzaOrderDTO));
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

        return pizza;
    }

    private PizzaSize getPizzaSize(Long pizzaSizeId) {
        return pizzaSizeDAO.findById(pizzaSizeId).orElseThrow(RuntimeException::new);
    }

    private CutStyle getCutStyle(Long cutStyleId) {
        return cutStyleDAO.findById(cutStyleId).orElseThrow(RuntimeException::new);
    }

    private Crust getCrust(Long crustId) {
        return crustDAO.findById(crustId).orElseThrow(RuntimeException::new);
    }

    private BakeStyle getBakeStyle(Long bakeStyleId) {
        return bakeStyleDAO.findById(bakeStyleId).orElseThrow(RuntimeException::new);
    }

    private PizzaSide createPizzaSide(Map<Long, Integer> ingredientIdByQuantity) {
        PizzaSide pizzaSide = new PizzaSide();
        List<PizzaItem> pizzaItems = ingredientIdByQuantity.entrySet().stream().map(longIntegerEntry -> createPizzaItem(pizzaSide, longIntegerEntry)).collect(toList());
        pizzaSide.setPizzaItems(pizzaItems);
        return pizzaSide;
    }

    private PizzaItem createPizzaItem(PizzaSide leftPizzaSide, Map.Entry<Long, Integer> longIntegerEntry) {
        Ingredient ingredient = ingredientDAO.findById(longIntegerEntry.getKey()).orElseThrow(RuntimeException::new);
        return new PizzaItem(leftPizzaSide, ingredient, longIntegerEntry.getValue());
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


    public CrustDAO getCrustDAO() {
        return crustDAO;
    }

    @Autowired
    public void setCrustDAO(CrustDAO crustDAO) {
        this.crustDAO = crustDAO;
    }

    public PizzaSizeDAO getPizzaSizeDAO() {
        return pizzaSizeDAO;
    }

    @Autowired
    public void setPizzaSizeDAO(PizzaSizeDAO pizzaSizeDAO) {
        this.pizzaSizeDAO = pizzaSizeDAO;
    }

    public BakeStyleDAO getBakeStyleDAO() {
        return bakeStyleDAO;
    }

    @Autowired
    public void setBakeStyleDAO(BakeStyleDAO bakeStyleDAO) {
        this.bakeStyleDAO = bakeStyleDAO;
    }

    public CutStyleDAO getCutStyleDAO() {
        return cutStyleDAO;
    }

    @Autowired
    public void setCutStyleDAO(CutStyleDAO cutStyleDAO) {
        this.cutStyleDAO = cutStyleDAO;
    }

    public IngredientDAO getIngredientDAO() {
        return ingredientDAO;
    }

    @Autowired
    public void setIngredientDAO(IngredientDAO ingredientDAO) {
        this.ingredientDAO = ingredientDAO;
    }

    public Order getOrder() {
        return order;
    }

    @Autowired
    public void setOrder(Order order) {
        this.order = order;
    }
}
