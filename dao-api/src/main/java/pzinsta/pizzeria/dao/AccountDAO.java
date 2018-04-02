package pzinsta.pizzeria.dao;

import pzinsta.pizzeria.model.user.Account;

import java.util.Optional;

public interface AccountDAO extends GenericDAO<Account, Long> {
    Optional<Account> findByUsername(String username);
}
