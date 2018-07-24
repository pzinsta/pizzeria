package pzinsta.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pzinsta.exception.DeliveryNotFoundException;
import pzinsta.model.Delivery;
import pzinsta.model.DeliveryStatus;
import pzinsta.repository.DeliveryRepository;
import pzinsta.service.strategy.DeliveryCostCalculationStrategy;

import javax.money.MonetaryAmount;

@Service
public class DeliveryService {

    private DeliveryRepository deliveryRepository;
    private DeliveryCostCalculationStrategy deliveryCostCalculationStrategy;

    @Autowired
    public DeliveryService(DeliveryRepository deliveryRepository, DeliveryCostCalculationStrategy deliveryCostCalculationStrategy) {
        this.deliveryRepository = deliveryRepository;
        this.deliveryCostCalculationStrategy = deliveryCostCalculationStrategy;
    }

    public Delivery findById(Long id) {
        return deliveryRepository.findById(id)
                .orElseThrow(() -> new DeliveryNotFoundException(id));
    }

    public MonetaryAmount calculateCost(Delivery delivery) {
        return deliveryCostCalculationStrategy.calculateCost(delivery);
    }

    public MonetaryAmount calculateCostById(Long id) {
        return calculateCost(findById(id));
    }

    public Delivery findByOrderId(Long orderId) {
        return deliveryRepository.findByOrderId(orderId)
                .orElseThrow(() -> DeliveryNotFoundException.withOrderId(orderId));
    }

    public Delivery create(Delivery delivery) {
        delivery.setId(null);
        delivery.setStatus(DeliveryStatus.PENDING);
        return deliveryRepository.save(delivery);
    }

    public Delivery update(Long id, Delivery delivery) {
        Delivery existingDelivery = deliveryRepository.findById(id)
                .orElseThrow(() -> new DeliveryNotFoundException(id));
        existingDelivery.setStatus(delivery.getStatus());
        existingDelivery.setOrderId(delivery.getOrderId());
        existingDelivery.setDeliveryAddress(delivery.getDeliveryAddress());
        existingDelivery.setDeliverypersonId(delivery.getDeliverypersonId());
        return deliveryRepository.save(existingDelivery);
    }

    public DeliveryRepository getDeliveryRepository() {
        return deliveryRepository;
    }

    public void setDeliveryRepository(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    public DeliveryCostCalculationStrategy getDeliveryCostCalculationStrategy() {
        return deliveryCostCalculationStrategy;
    }

    public void setDeliveryCostCalculationStrategy(DeliveryCostCalculationStrategy deliveryCostCalculationStrategy) {
        this.deliveryCostCalculationStrategy = deliveryCostCalculationStrategy;
    }
}
