package pzinsta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pzinsta.exception.AccountNotFoundException;
import pzinsta.model.Account;
import pzinsta.service.AccountService;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public Iterable<Account> findAll() {
        return accountService.findAll();
    }

    @GetMapping(params = "username")
    public Account findByUsername(@RequestParam("username") String username) {
        return accountService.findByUsername(username);
    }

    @GetMapping(params = {"size", "page"})
    public Page<Account> findAll(Pageable pageable) {
        return accountService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public Account findById(@PathVariable("id") Long id) {
        return accountService.findById(id);
    }

    @PutMapping("/{id}")
    public Account update(@PathVariable("id") Long id, @RequestBody Account account) {
        return accountService.update(id, account);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Account create(@RequestBody Account account) {
        return accountService.create(account);
    }

    @PutMapping("/{id}/password")
    public void updatePassword(@PathVariable("id") Long id, @RequestBody String password) {
        accountService.updatePassword(id, password);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleAccountNotFoundException(AccountNotFoundException e) {
        return e.getMessage();
    }
}
