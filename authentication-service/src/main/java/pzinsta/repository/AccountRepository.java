package pzinsta.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import pzinsta.model.Account;

import java.util.Optional;

public interface AccountRepository extends PagingAndSortingRepository<Account, Long> {
    Optional<Account> findByUsername(String username);
    boolean existsByUsername(String username);
}
