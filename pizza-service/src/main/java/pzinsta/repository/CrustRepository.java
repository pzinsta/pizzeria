package pzinsta.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pzinsta.model.Crust;

@Repository
public interface CrustRepository extends CrudRepository<Crust, Long> {
}
