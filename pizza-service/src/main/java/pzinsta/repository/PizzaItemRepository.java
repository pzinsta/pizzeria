package pzinsta.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pzinsta.model.PizzaItem;

@Repository
public interface PizzaItemRepository extends CrudRepository<PizzaItem, Long> {
}
