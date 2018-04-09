package pzinsta.pizzeria.service.impl;

import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import pzinsta.pizzeria.dao.AccountDAO;
import pzinsta.pizzeria.dao.CustomerDAO;
import pzinsta.pizzeria.model.user.Account;
import pzinsta.pizzeria.model.user.Customer;

import java.util.Optional;

public class CustomerServiceImplTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private AccountDAO accountDAO;

    @Mock
    private CustomerDAO customerDAO;

    @InjectMocks
    private CustomerServiceImpl userService;

    @Test
    public void shouldGetCustomerByUsername() throws Exception {
        // given
        String username = "john.doe";
        Long customerId = 42L;

        Customer customer = new Customer();
        customer.setId(customerId);

        Account account = new Account();
        account.setUser(customer);

        Mockito.when(accountDAO.findByUsername(username)).thenReturn(Optional.of(account));
        Mockito.when(customerDAO.findById(customerId)).thenReturn(Optional.of(customer));

        // when
        Optional<Customer> result = userService.getCustomerByUsername(username);

        // then
        Assertions.assertThat(result).contains(customer);
    }

    @Test
    public void shouldReturnEmptyOptionalWhenAccountIsNotFound() throws Exception {
        // given
        Mockito.when(accountDAO.findByUsername(ArgumentMatchers.anyString())).thenReturn(Optional.empty());

        // when
        Optional<Customer> result = userService.getCustomerByUsername("john");

        // then
        Assertions.assertThat(result).isEmpty();
    }
}