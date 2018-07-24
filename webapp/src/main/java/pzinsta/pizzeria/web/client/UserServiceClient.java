package pzinsta.pizzeria.web.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import pzinsta.pizzeria.web.client.dto.user.User;

import java.util.Collection;
import java.util.Optional;

@Component
@FeignClient(
        name = "user-service",
        url = "${user.service.url}",
        path = "/users",
        decode404 = true
)
public interface UserServiceClient {

    @GetMapping
    Collection<User> findAll();

    @GetMapping("/{id}")
    Optional<User> findById(@PathVariable("id") Long id);

    @GetMapping
    Optional<User> findByUsername(@RequestParam("username") String username);

    @PostMapping
    User save(@RequestBody User user);

}
