package pzinsta.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pzinsta.model.BakeStyle;

@Repository
public interface BakeStyleRepository extends CrudRepository<BakeStyle, Long> {
}
