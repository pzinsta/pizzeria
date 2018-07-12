package pzinsta.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pzinsta.model.PizzaSize;

@Repository
public interface PizzaSizeRepository extends CrudRepository<PizzaSize, Long> {
}
