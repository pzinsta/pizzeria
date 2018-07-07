package pzinsta.pizzeria.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pzinsta.pizzeria.model.File;

import java.util.Optional;

@Repository
public interface FileRepository extends PagingAndSortingRepository<File, Long> {
    public Optional<File> findByName(String name);
}
