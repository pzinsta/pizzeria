package pzinsta.pizzeria.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pzinsta.pizzeria.dao.AccountDAO;
import pzinsta.pizzeria.model.user.Account;
import pzinsta.pizzeria.service.AccountService;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountDAO accountDAO;

    @Autowired
    public AccountServiceImpl(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Account> getAccountByUsername(String username) {
            return accountDAO.findByUsername(username);
    }
}
