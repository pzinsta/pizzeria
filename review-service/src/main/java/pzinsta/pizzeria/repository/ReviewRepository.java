package pzinsta.pizzeria.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import pzinsta.pizzeria.model.Review;

import java.util.Optional;

public interface ReviewRepository extends PagingAndSortingRepository<Review, Long> {
    Optional<Review> findByOrderId(Long orderId);
}
