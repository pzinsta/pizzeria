package pzinsta.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pzinsta.model.PizzaSide;

@Repository
public interface PizzaSideRepository extends CrudRepository<PizzaSide, Long> {
}
