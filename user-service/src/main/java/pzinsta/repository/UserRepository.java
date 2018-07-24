package pzinsta.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import pzinsta.model.User;

import java.util.Optional;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
