package pzinsta.pizzeria.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import pzinsta.pizzeria.model.Review;

public interface ReviewRepository extends PagingAndSortingRepository<Review, Long> {
}
