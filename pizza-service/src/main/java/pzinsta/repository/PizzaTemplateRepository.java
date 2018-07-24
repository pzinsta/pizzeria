package pzinsta.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pzinsta.model.PizzaTemplate;

@Repository
public interface PizzaTemplateRepository extends CrudRepository<PizzaTemplate, Long> {
}
