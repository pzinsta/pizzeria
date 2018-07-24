package pzinsta.service;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import pzinsta.exception.DeliveryNotFoundException;
import pzinsta.model.Delivery;
import pzinsta.model.DeliveryAddress;
import pzinsta.model.DeliveryStatus;
import pzinsta.repository.DeliveryRepository;
import pzinsta.service.strategy.DeliveryCostCalculationStrategy;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DeliveryServiceTest {

    private static final long DELIVERY_ID = 42L;
    private static final long ORDER_ID = 15L;
    private static final long DELIVERYPERSON_ID = 11L;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @InjectMocks
    private DeliveryService deliveryService;

    @Mock
    private DeliveryCostCalculationStrategy deliveryCostCalculationStrategy;

    @Mock
    private DeliveryRepository deliveryRepository;

    @Captor
    private ArgumentCaptor<Delivery> deliveryArgumentCaptor;

    @Test
    public void shouldFindExistingDeliveryById() throws Exception {
        // given
        Delivery delivery = new Delivery();
        when(deliveryRepository.findById(DELIVERY_ID)).thenReturn(Optional.of(delivery));

        // when
        Delivery result = deliveryService.findById(DELIVERY_ID);

        // then
        assertThat(result).isSameAs(delivery);
    }

    @Test
    public void shouldThrowExceptionWhenCannotFindDeliveryById() throws Exception {
        // given
        when(deliveryRepository.findById(DELIVERY_ID)).thenReturn(Optional.empty());

        // when
        Throwable throwable = catchThrowable(() -> deliveryService.findById(DELIVERY_ID));

        // then
        assertThat(throwable).isInstanceOf(DeliveryNotFoundException.class);
    }

    @Test
    public void shouldCalculateCost() throws Exception {
        // given
        Delivery delivery = new Delivery();
        MonetaryAmount cost = Monetary.getDefaultAmountFactory().setNumber(5.31).setCurrency("USD").create();
        when(deliveryCostCalculationStrategy.calculateCost(delivery)).thenReturn(cost);

        // when
        MonetaryAmount result = deliveryService.calculateCost(delivery);

        // then
        assertThat(result).isEqualTo(cost);
    }

    @Test
    public void shouldCalculateCostByDeliveryId() throws Exception {
        // given
        Delivery delivery = new Delivery();
        MonetaryAmount cost = Monetary.getDefaultAmountFactory().setNumber(5.31).setCurrency("USD").create();
        when(deliveryCostCalculationStrategy.calculateCost(delivery)).thenReturn(cost);
        when(deliveryRepository.findById(DELIVERY_ID)).thenReturn(Optional.of(delivery));

        // when
        MonetaryAmount result = deliveryService.calculateCostById(DELIVERY_ID);

        // then
        assertThat(result).isEqualTo(cost);
    }

    @Test
    public void shouldThrowExceptionWhenCalculatingCostAndCannotFindDeliveryById() throws Exception {
        // given
        when(deliveryRepository.findById(DELIVERY_ID)).thenReturn(Optional.empty());

        // when
        Throwable throwable = catchThrowable(() -> deliveryService.calculateCostById(DELIVERY_ID));

        // then
        assertThat(throwable).isInstanceOf(DeliveryNotFoundException.class);
    }

    @Test
    public void shouldFindDeliveryByOrderId() throws Exception {
        // given
        Delivery delivery = new Delivery();
        when(deliveryRepository.findByOrderId(ORDER_ID)).thenReturn(Optional.of(delivery));

        // when
        Delivery result = deliveryService.findByOrderId(ORDER_ID);

        // then
        assertThat(result).isSameAs(delivery);
    }

    @Test
    public void shouldThrowExceptionWhenCannotFindDeliveryByOrderId() throws Exception {
        // given
        when(deliveryRepository.findByOrderId(ORDER_ID)).thenReturn(Optional.empty());

        // when
        Throwable throwable = catchThrowable(() -> deliveryService.findByOrderId(ORDER_ID));

        // then
        assertThat(throwable).isInstanceOf(DeliveryNotFoundException.class);
    }

    @Test
    public void shouldCreateDelivery() throws Exception {
        // given
        Delivery delivery = new Delivery();

        // when
        Delivery result = deliveryService.create(delivery);

        // then
        verify(deliveryRepository).save(deliveryArgumentCaptor.capture());
        Delivery capturedDelivery = deliveryArgumentCaptor.getValue();
        assertThat(capturedDelivery.getOrderId()).isNull();
        assertThat(capturedDelivery.getStatus()).isEqualTo(DeliveryStatus.PENDING);
    }

    @Test
    public void shouldUpdateDelivery() throws Exception {
        // given
        Delivery existingDelivery = new Delivery();
        existingDelivery.setId(DELIVERY_ID);
        when(deliveryRepository.findById(DELIVERY_ID)).thenReturn(Optional.of(existingDelivery));

        DeliveryAddress updatedDeliveryAddress = new DeliveryAddress();
        Delivery updatedDelivery = new Delivery();
        updatedDelivery.setStatus(DeliveryStatus.DISPATCHED);
        updatedDelivery.setDeliverypersonId(DELIVERYPERSON_ID);
        updatedDelivery.setOrderId(ORDER_ID);
        updatedDelivery.setDeliveryAddress(updatedDeliveryAddress);

        // when
        Delivery result = deliveryService.update(DELIVERY_ID, updatedDelivery);

        // then
        Delivery expectedDelivery = new Delivery();
        expectedDelivery.setId(DELIVERY_ID);
        expectedDelivery.setDeliveryAddress(updatedDeliveryAddress);
        expectedDelivery.setOrderId(ORDER_ID);
        expectedDelivery.setDeliverypersonId(DELIVERYPERSON_ID);
        expectedDelivery.setStatus(DeliveryStatus.DISPATCHED);

        verify(deliveryRepository).save(deliveryArgumentCaptor.capture());
        Delivery capturedDelivery = deliveryArgumentCaptor.getValue();
        assertThat(capturedDelivery).isEqualToComparingFieldByField(expectedDelivery);
    }

    @Test
    public void shouldThrowExceptionWhenUpdatingDeliveryAndCannontFindById() throws Exception {
        // given
        Delivery delivery = new Delivery();
        when(deliveryRepository.findById(DELIVERY_ID)).thenReturn(Optional.empty());

        // when
        Throwable throwable = catchThrowable(() -> deliveryService.update(DELIVERY_ID, delivery));

        // then
        assertThat(throwable).isInstanceOf(DeliveryNotFoundException.class);
    }
}