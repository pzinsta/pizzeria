package pzinsta.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pzinsta.exception.AccountNotFoundException;
import pzinsta.exception.UsernameAlreadyExistsException;
import pzinsta.model.Account;
import pzinsta.repository.AccountRepository;

@Service
public class AccountService {

    private AccountRepository accountRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Iterable<Account> findAll() {
        return accountRepository.findAll();
    }

    public Page<Account> findAll(Pageable pageable) {
        return accountRepository.findAll(pageable);
    }

    public Account findByUsername(String username) {
        return accountRepository.findByUsername(username)
                .orElseThrow(() -> AccountNotFoundException.withUsername(username));
    }

    public Account findById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> AccountNotFoundException.withId(id));
    }

    public Account create(Account account) {
        if (accountRepository.existsByUsername(account.getUsername())) {
            throw new UsernameAlreadyExistsException(account.getUsername());
        }
        Account newAccount = new Account();
        newAccount.setUsername(account.getUsername());
        newAccount.setEnabled(true);
        newAccount.setRoles(account.getRoles());
        newAccount.setPassword(passwordEncoder.encode(account.getPassword()));
        return accountRepository.save(newAccount);
    }

    public Account update(Long id, Account account) {
        Account existingAccount = accountRepository.findById(id)
                .orElseThrow(() -> AccountNotFoundException.withId(id));
        existingAccount.setAccountExpired(account.isAccountExpired());
        existingAccount.setAccountLocked(account.isAccountLocked());
        existingAccount.setCredentialsExpired(account.isCredentialsExpired());
        existingAccount.setEnabled(account.isEnabled());
        existingAccount.setRoles(account.getRoles());
        return accountRepository.save(existingAccount);
    }

    public void updatePassword(Long id, String password) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> AccountNotFoundException.withId(id));
        account.setPassword(passwordEncoder.encode(password));
        accountRepository.save(account);
    }

    public AccountRepository getAccountRepository() {
        return accountRepository;
    }

    public void setAccountRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
}
