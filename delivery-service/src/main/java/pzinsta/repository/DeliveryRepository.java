package pzinsta.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pzinsta.model.Delivery;

import java.util.Optional;

@Repository
public interface DeliveryRepository extends PagingAndSortingRepository<Delivery, Long> {

    Optional<Delivery> findByOrderId(Long orderId);

}
