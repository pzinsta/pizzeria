package pzinsta.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pzinsta.model.Pizza;

@Repository
public interface PizzaRepository extends CrudRepository<Pizza, Long> {
}
