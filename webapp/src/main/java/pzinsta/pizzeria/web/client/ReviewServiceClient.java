package pzinsta.pizzeria.web.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import pzinsta.pizzeria.web.client.dto.Review;

import java.util.Collection;
import java.util.Optional;

@Component
@FeignClient(
        name = "review-service",
        url = "${review.service.url}",
        path = "/reviews",
        decode404 = true
)
public interface ReviewServiceClient {

    @GetMapping("/{id}")
    Optional<Review> findById(@PathVariable("id") Long id);

    @GetMapping
    Optional<Review> findByOrderId(@RequestParam("orderId") Long orderId);

    @GetMapping
    Collection<Review> findAll();

    @GetMapping
    Page<Review> findAll(@RequestParam("size") int size, @RequestParam("page") int page);

    @PostMapping
    Review save(@RequestBody Review review);

}
