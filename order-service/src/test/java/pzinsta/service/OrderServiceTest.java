package pzinsta.service;

import com.google.common.collect.ImmutableList;
import org.javamoney.moneta.Money;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import pzinsta.client.PizzaServiceClient;
import pzinsta.exception.OrderNotFoundException;
import pzinsta.model.Order;
import pzinsta.model.OrderItem;
import pzinsta.repository.OrderRepository;
import pzinsta.strategy.TrackingNumberGenerationStrategy;

import javax.money.MonetaryAmount;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OrderServiceTest {

    private static final long ORDER_ID = 42L;
    private static final String TRACKING_NUMBER = "ABCXYZ";
    private static final long CUSTOMER_ID = 17L;
    private static final String COMMENT = "Knock on the door.";

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private TrackingNumberGenerationStrategy trackingNumberGenerationStrategy;

    @Mock
    private PizzaServiceClient pizzaServiceClient;

    @Captor
    private ArgumentCaptor<Order> orderArgumentCaptor;

    @Test
    public void shouldFindOrderById() throws Exception {
        // given
        Order order = new Order();
        when(orderRepository.findById(ORDER_ID)).thenReturn(Optional.of(order));

        // when
        Order result = orderService.findById(ORDER_ID);

        // then
        assertThat(result).isSameAs(order);
    }

    @Test
    public void shouldThrowExceptionWhenCannotFindOrderById() throws Exception {
        // given
        when(orderRepository.findById(ORDER_ID)).thenReturn(Optional.empty());

        // when
        Throwable throwable = catchThrowable(() -> orderService.findById(ORDER_ID));

        // then
        assertThat(throwable).isInstanceOf(OrderNotFoundException.class);
    }

    @Test
    public void shouldFindByTrackingNumber() throws Exception {
        // given
        Order order = new Order();
        when(orderRepository.findByTrackingNumber(TRACKING_NUMBER)).thenReturn(Optional.of(order));

        // when
        Order result = orderService.findByTrackingNumber(TRACKING_NUMBER);

        // then
        assertThat(result).isSameAs(order);
    }

    @Test
    public void shouldThrowExceptionWhenCannotFindOrderByTrackingNumber() throws Exception {
        // given
        when(orderRepository.findByTrackingNumber(TRACKING_NUMBER)).thenReturn(Optional.empty());

        // when
        Throwable throwable = catchThrowable(() -> orderService.findByTrackingNumber(TRACKING_NUMBER));

        // then
        assertThat(throwable).isInstanceOf(OrderNotFoundException.class);
    }

    @Test
    public void shouldFindAllByCustomerId() throws Exception {
        // given
        List<Order> orders = ImmutableList.of();
        when(orderRepository.findAllByCustomerId(CUSTOMER_ID)).thenReturn(orders);

        // when
        Iterable<Order> result = orderService.findAllByCustomerId(CUSTOMER_ID);

        // then
        assertThat(result).isSameAs(orders);
    }

    @Test
    public void shouldPostOrder() throws Exception {
        // given
        Order order = new Order();
        order.setId(ORDER_ID);
        order.setCustomerId(CUSTOMER_ID);
        order.setComment(COMMENT);

        when(trackingNumberGenerationStrategy.generateTrackingNumber(order)).thenReturn(TRACKING_NUMBER);

        // when
        Order result = orderService.post(order);

        // then
        Order expectedOrder = new Order();
        expectedOrder.setId(ORDER_ID);
        expectedOrder.setCustomerId(CUSTOMER_ID);
        expectedOrder.setComment(COMMENT);
        expectedOrder.setTrackingNumber(TRACKING_NUMBER);

        verify(orderRepository).save(orderArgumentCaptor.capture());
        Order capturedOrder = orderArgumentCaptor.getValue();
        assertThat(capturedOrder).isEqualToComparingOnlyGivenFields(expectedOrder, "id", "customerId", "comment", "trackingNumber");
    }

    @Test
    public void shouldCalculateCost() throws Exception {
        // given
        Order order = orderWithTwoOrderItems();
        when(pizzaServiceClient.calculatePizzaCost(14L)).thenReturn(Money.of(5, "USD"));
        when(pizzaServiceClient.calculatePizzaCost(15L)).thenReturn(Money.of(3, "USD"));

        // when
        MonetaryAmount result = orderService.calculateCost(order);

        // then
        assertThat(result).isEqualTo(Money.of(21, "USD"));
    }

    @Test
    public void shouldCalculateCostById() throws Exception {
        // given
        when(orderRepository.findById(ORDER_ID)).thenReturn(Optional.of(orderWithTwoOrderItems()));
        when(pizzaServiceClient.calculatePizzaCost(14L)).thenReturn(Money.of(5, "USD"));
        when(pizzaServiceClient.calculatePizzaCost(15L)).thenReturn(Money.of(3, "USD"));

        // when
        MonetaryAmount result = orderService.calculateCostById(ORDER_ID);

        // then
        assertThat(result).isEqualTo(Money.of(21, "USD"));
    }

    @Test
    public void shouldThrowExceptionWhenCalculatingCostByIdAndCannotFindOrder() throws Exception {
        // given
        when(orderRepository.findById(ORDER_ID)).thenReturn(Optional.empty());

        // when
        Throwable throwable = catchThrowable(() -> orderService.calculateCostById(ORDER_ID));

        // then
        assertThat(throwable).isInstanceOf(OrderNotFoundException.class);
    }

    private static Order orderWithTwoOrderItems() {
        OrderItem firstOrderItem = new OrderItem();
        firstOrderItem.setPizzaId(14L);
        firstOrderItem.setQuantity(3);

        OrderItem secondOrderItem = new OrderItem();
        secondOrderItem.setPizzaId(15L);
        secondOrderItem.setQuantity(2);

        Order order = new Order();
        order.setOrderItems(ImmutableList.of(firstOrderItem, secondOrderItem));
        return order;
    }
}