package pzinsta.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pzinsta.model.CutStyle;

@Repository
public interface CutStyleRepository extends CrudRepository<CutStyle, Long> {
}
