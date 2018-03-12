package pzinsta.pizzeria.service.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
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
import pzinsta.pizzeria.service.dto.PizzaOrderDTO;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PizzaBuilderServiceImplTest {

    private static final int PIZZA_QUANTITY = 6;

    private static final long PIZZA_SIZE_ID = 1L;
    private static final long CUT_STYLE_ID = 2L;
    private static final long CRUST_ID = 3L;
    private static final long BAKE_STYLE_ID = 4L;

    private static final int QUANTITY_TWO = 2;
    private static final int QUANTITY_ONE = 1;

    private static final long FIRST_INGREDIENT_ID = 7L;
    private static final long SECOND_INGREDIENT_ID = 8L;
    private static final long THIRD_INGREDIENT_ID = 9L;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private CrustDAO crustDAO;

    @Mock
    private PizzaSizeDAO pizzaSizeDAO;

    @Mock
    private BakeStyleDAO bakeStyleDAO;

    @Mock
    private CutStyleDAO cutStyleDAO;

    @Mock
    private IngredientDAO ingredientDAO;

    @InjectMocks
    private PizzaBuilderServiceImpl pizzaBuilderService;

    @Spy
    private Order order;

    @Captor
    private ArgumentCaptor<OrderItem> orderItemArgumentCaptor;

    @Test
    public void shouldGetCrustsFromDAO() throws Exception {
        // given
        List<Crust> crusts = ImmutableList.of();
        when(crustDAO.findAll()).thenReturn(crusts);

        // when
        Collection<Crust> result = pizzaBuilderService.getCrusts();

        // then
        assertThat(result).isSameAs(crusts);
    }

    @Test
    public void shouldGetPizzaSizesFromDAO() throws Exception {
        // given
        List<PizzaSize> pizzaSizes = ImmutableList.of();
        when(pizzaSizeDAO.findAll()).thenReturn(pizzaSizes);

        // when
        Collection<PizzaSize> result = pizzaBuilderService.getPizzaSizes();

        // then
        assertThat(result).isSameAs(pizzaSizes);
    }

    @Test
    public void shouldGetBakeStylesFromDAO() throws Exception {
        // given
        List<BakeStyle> bakeStyles = ImmutableList.of();
        when(bakeStyleDAO.findAll()).thenReturn(bakeStyles);

        // when
        Collection<BakeStyle> result = pizzaBuilderService.getBakeStyles();

        // then
        assertThat(result).isSameAs(bakeStyles);
    }

    @Test
    public void shouldGetCutStylesFromDAO() throws Exception {
        // given
        List<CutStyle> cutStyles = ImmutableList.of();
        when(cutStyleDAO.findAll()).thenReturn(cutStyles);

        // when
        Collection<CutStyle> result = pizzaBuilderService.getCutStyles();

        // then
        assertThat(result).isSameAs(cutStyles);
    }

    @Test
    public void shouldGenerateListOfQuantities() throws Exception {
        // given
        pizzaBuilderService.setMinQuantity(3);
        pizzaBuilderService.setMaxQuantity(7);

        // when
        Collection<Integer> result = pizzaBuilderService.getQuantities();

        // then
        assertThat(result).containsExactly(3, 4, 5, 6, 7);
    }

    @Test
    public void shouldGetIngredientsFromDAO() throws Exception {
        // given
        List<Ingredient> ingredients = ImmutableList.of();
        when(ingredientDAO.findAll()).thenReturn(ingredients);

        // when
        Collection<Ingredient> result = pizzaBuilderService.getIngredients();

        // then
        assertThat(result).isSameAs(ingredients);
    }

    @Test
    public void shouldAddPizzaOrderItemToOrder() throws Exception {
        // given
        PizzaOrderDTO pizzaOrderDTO = createPizzaOrderDTO();

        when(crustDAO.findById(CRUST_ID)).thenReturn(Optional.of(getCrust()));
        when(pizzaSizeDAO.findById(PIZZA_SIZE_ID)).thenReturn(Optional.of(getPizzaSize()));
        when(bakeStyleDAO.findById(BAKE_STYLE_ID)).thenReturn(Optional.of(getBakeStyle()));
        when(cutStyleDAO.findById(CUT_STYLE_ID)).thenReturn(Optional.of(getCutStyle()));
        when(ingredientDAO.findById(anyLong())).then(invocation -> {
            Ingredient ingredient = new Ingredient();
            ingredient.setId(invocation.getArgument(0));
            return Optional.of(ingredient);
        });

        // when
        pizzaBuilderService.addOrderItemToOrder(pizzaOrderDTO);

        // then
        verify(order).addOrderItem(orderItemArgumentCaptor.capture());
        OrderItem capturedOrderItem = orderItemArgumentCaptor.getValue();

        assertThatOrderItemsAreEqual(capturedOrderItem, createExpectedOrderItem());
    }

    private void assertThatOrderItemsAreEqual(OrderItem capturedOrderItem, OrderItem expectedOrderItem) {
        assertThat(capturedOrderItem.getOrder()).isEqualToComparingFieldByFieldRecursively(expectedOrderItem.getOrder());
        assertThat(capturedOrderItem.getPizza()).isEqualToComparingFieldByFieldRecursively(expectedOrderItem.getPizza());
        assertThat(capturedOrderItem.getQuantity()).isEqualTo(expectedOrderItem.getQuantity());
    }

    private OrderItem createExpectedOrderItem() {
        OrderItem expectedOrderItem = new OrderItem();
        expectedOrderItem.setOrder(order);
        expectedOrderItem.setQuantity(PIZZA_QUANTITY);

        Pizza expectedPizza = createExpectedPizza();

        expectedOrderItem.setPizza(expectedPizza);
        return expectedOrderItem;
    }

    private Pizza createExpectedPizza() {
        Pizza expectedPizza = new Pizza();

        expectedPizza.setCutStyle(getCutStyle());
        expectedPizza.setSize(getPizzaSize());
        expectedPizza.setCrust(getCrust());
        expectedPizza.setBakeStyle(getBakeStyle());

        PizzaSide rightPizzaSide = new PizzaSide();
        PizzaItem rightPizzaItem1 = createPizzaItem(rightPizzaSide, FIRST_INGREDIENT_ID, QUANTITY_TWO);
        PizzaItem rightPizzaItem2 = createPizzaItem(rightPizzaSide, SECOND_INGREDIENT_ID, QUANTITY_ONE);
        PizzaItem rightPizzaItem3 = createPizzaItem(rightPizzaSide, THIRD_INGREDIENT_ID, QUANTITY_TWO);
        rightPizzaSide.setPizzaItems(ImmutableList.of(rightPizzaItem1, rightPizzaItem2, rightPizzaItem3));

        PizzaSide leftPizzaSide = new PizzaSide();
        PizzaItem leftPizzaItem1 = createPizzaItem(leftPizzaSide, FIRST_INGREDIENT_ID, QUANTITY_ONE);
        PizzaItem leftPizzaItem2 = createPizzaItem(leftPizzaSide, THIRD_INGREDIENT_ID, QUANTITY_TWO);
        leftPizzaSide.setPizzaItems(ImmutableList.of(leftPizzaItem1, leftPizzaItem2));

        expectedPizza.setLeftPizzaSide(leftPizzaSide);
        expectedPizza.setRightPizzaSide(rightPizzaSide);

        return expectedPizza;
    }

    private CutStyle getCutStyle() {
        CutStyle cutStyle = new CutStyle();
        cutStyle.setId(CUT_STYLE_ID);
        return cutStyle;
    }

    private BakeStyle getBakeStyle() {
        BakeStyle bakeStyle = new BakeStyle();
        bakeStyle.setId(BAKE_STYLE_ID);
        return bakeStyle;
    }

    private PizzaSize getPizzaSize() {
        PizzaSize pizzaSize = new PizzaSize();
        pizzaSize.setId(PIZZA_SIZE_ID);
        return pizzaSize;
    }

    private Crust getCrust() {
        Crust crust = new Crust();
        crust.setId(CRUST_ID);
        return crust;
    }

    private PizzaOrderDTO createPizzaOrderDTO() {
        PizzaOrderDTO pizzaOrderDTO = new PizzaOrderDTO();
        pizzaOrderDTO.setPizzaSizeId(PIZZA_SIZE_ID);
        pizzaOrderDTO.setCutStyleId(CUT_STYLE_ID);
        pizzaOrderDTO.setCrustId(CRUST_ID);
        pizzaOrderDTO.setBakeStyleId(BAKE_STYLE_ID);
        pizzaOrderDTO.setQuantity(PIZZA_QUANTITY);
        pizzaOrderDTO.setLeftSideIngredientIdByQuantity(ImmutableMap.of(FIRST_INGREDIENT_ID, QUANTITY_ONE, THIRD_INGREDIENT_ID, QUANTITY_TWO));
        pizzaOrderDTO.setRightSideIngredientIdByQuantity(ImmutableMap.of(FIRST_INGREDIENT_ID, QUANTITY_TWO, SECOND_INGREDIENT_ID, QUANTITY_ONE, THIRD_INGREDIENT_ID, QUANTITY_TWO));
        return pizzaOrderDTO;
    }

    private PizzaItem createPizzaItem(PizzaSide pizzaSide, Long ingredientId, int quantity) {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(ingredientId);
        return new PizzaItem(pizzaSide, ingredient, quantity);
    }
}