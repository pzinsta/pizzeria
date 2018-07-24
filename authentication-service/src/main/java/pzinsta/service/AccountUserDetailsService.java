package pzinsta.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pzinsta.model.Account;
import pzinsta.repository.AccountRepository;

@Service
public class AccountUserDetailsService implements UserDetailsService {

    private AccountRepository accountRepository;

    @Autowired
    public AccountUserDetailsService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return User.builder()
                .username(account.getUsername())
                .password(account.getPassword())
                .accountExpired(account.isAccountExpired())
                .accountLocked(account.isAccountLocked())
                .credentialsExpired(account.isCredentialsExpired())
                .disabled(!account.isEnabled())
                .roles(account.getRoles().stream().map(Enum::toString).toArray(String[]::new)).build();
    }

    public AccountRepository getAccountRepository() {
        return accountRepository;
    }

    public void setAccountRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
}
