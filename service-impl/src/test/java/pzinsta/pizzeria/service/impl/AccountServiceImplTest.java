package pzinsta.pizzeria.service.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import pzinsta.pizzeria.dao.AccountDAO;
import pzinsta.pizzeria.model.user.Account;
import pzinsta.pizzeria.model.user.Role;
import pzinsta.pizzeria.model.user.User;
import pzinsta.pizzeria.service.dto.AccountDTO;

import java.time.Instant;
import java.util.Collection;
import java.util.Optional;

import static org.mockito.Mockito.when;

public class AccountServiceImplTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private AccountDAO accountDAO;

    @InjectMocks
    private AccountServiceImpl accountService;

    @Test
    public void shouldGetAccountByUsernameFromDao() throws Exception {
        // given
        String username = "john.doe";
        Optional<Account> accountOptional = Optional.empty();
        when(accountDAO.findByUsername(username)).thenReturn(accountOptional);

        // when
        Optional<Account> result = accountService.getAccountByUsername(username);

        // then
        Assertions.assertThat(result).isSameAs(accountOptional);
    }

    @Test
    public void shouldGetAccountsAsAccountDTO() {
        // given
        Instant createdOn = Instant.now();
        Account account = createAccount(createdOn);

        when(accountDAO.findAll()).thenReturn(ImmutableList.of(account));

        // when
        Collection<AccountDTO> result = accountService.getAccounts();

        // then
        AccountDTO accountDTO = createExpectedAccountDTO(createdOn);
        Assertions.assertThat(result).hasSize(1).first().isEqualToIgnoringNullFields(accountDTO);
    }

    private AccountDTO createExpectedAccountDTO(Instant createdOn) {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(1L);
        accountDTO.setUserId(2L);
        accountDTO.setUsername("john.doe");
        accountDTO.setEnabled(true);
        accountDTO.setAccountLocked(true);
        accountDTO.setAccountExpired(true);
        accountDTO.setCredentialsExpired(true);
        accountDTO.setCreatedOn(createdOn);
        accountDTO.setRoles(ImmutableSet.of(Role.ADMIN, Role.MANAGER));
        return accountDTO;
    }

    private Account createAccount(Instant createdOn) {
        Account account = new Account();
        account.setId(1L);
        account.setUsername("john.doe");
        account.setPassword("s3cr3t");
        account.setEnabled(true);
        account.setAccountExpired(true);
        account.setAccountLocked(true);
        account.setCredentialsExpired(true);
        account.setCreatedOn(createdOn);
        account.setRoles(ImmutableSet.of(Role.ADMIN, Role.MANAGER));
        User user = new User();
        user.setId(2L);
        account.setUser(user);
        return account;
    }
}