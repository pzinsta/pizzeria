package pzinsta.pizzeria.web.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import pzinsta.pizzeria.web.client.dto.account.Account;

import java.util.Collection;
import java.util.Optional;

@Component
@FeignClient(name = "account-service",
        path = "/accounts",
        decode404 = true
)
public interface AccountServiceClient {

    @GetMapping
    Collection<Account> findAll();

    @GetMapping("/{id}")
    Optional<Account> findById(@PathVariable("id") Long id);

    @GetMapping
    Optional<Account> findByUsername(@RequestParam("username") String username);

    @PutMapping("/{id}")
    Account update(@PathVariable("id") Long id, @RequestBody Account account);

    @PostMapping
    Account create(@RequestBody Account account);

    @PutMapping("/{id}/password")
    public void updatePassword(@PathVariable("id") Long id, @RequestBody String password);
}
