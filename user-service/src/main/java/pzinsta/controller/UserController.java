package pzinsta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pzinsta.exception.UserNotFoundException;
import pzinsta.model.User;
import pzinsta.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Iterable<User> findAll() {
        return userService.findAll();
    }

    @GetMapping(params = {"size", "page"})
    public Page<User> findAll(Pageable pageable) {
        return userService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable("id") Long id) {
        return userService.findById(id);
    }

    @GetMapping(params = "username")
    public User findByUsername(@RequestParam("username") String username) {
        return userService.findByUsername(username);
    }

    @PostMapping
    public User save(@RequestBody User user) {
        return userService.save(user);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleUserNotFoundException(UserNotFoundException e) {
        return e.getMessage();
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
