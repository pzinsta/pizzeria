package pzinsta.repository;

import org.springframework.data.repository.CrudRepository;
import pzinsta.model.Order;

import java.util.Optional;

public interface OrderRepository extends CrudRepository<Order, Long> {

    Optional<Order> findByTrackingNumber(String trackingNumber);

    Iterable<Order> findAllByCustomerId(Long customerId);
}
