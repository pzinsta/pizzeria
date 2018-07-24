package pzinsta.repository;

import org.springframework.data.repository.CrudRepository;
import pzinsta.model.Customer;

import java.util.Optional;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
    boolean existsByUsername(String username);
    Optional<Customer> findByUsername(String username);
}
