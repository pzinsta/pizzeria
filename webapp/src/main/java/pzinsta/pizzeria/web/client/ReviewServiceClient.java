package pzinsta.pizzeria.web.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pzinsta.pizzeria.web.client.dto.Review;

import java.util.Optional;

@Component
@FeignClient(name = "reviews", url = "${review.service.url}", path = "/reviews")
public interface ReviewServiceClient {

    @GetMapping("/{id}")
    Optional<Resource<Review>> findById(@PathVariable("id") Long id);

    @GetMapping
    PagedResources<Resource<Review>> findAll(@RequestParam("limit") int limit, @RequestParam("page") int page);

    @PostMapping
    Resource<Review> save(Review review);

    @PatchMapping("/{id}")
    Resource<Review> update(@PathVariable("id") Long id, Review review);


}
