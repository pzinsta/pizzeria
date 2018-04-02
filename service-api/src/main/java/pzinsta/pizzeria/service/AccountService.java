package pzinsta.pizzeria.service;

import pzinsta.pizzeria.model.user.Account;

import java.util.Optional;

public interface AccountService {

    Optional<Account> getAccountByUsername(String username);
}
