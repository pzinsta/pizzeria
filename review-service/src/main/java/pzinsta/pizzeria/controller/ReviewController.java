package pzinsta.pizzeria.controller;

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
import pzinsta.pizzeria.exception.ReviewNotFoundException;
import pzinsta.pizzeria.model.Review;
import pzinsta.pizzeria.service.ReviewService;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/{id}")
    public Review findById(@PathVariable("id") Long id) {
        return reviewService.findById(id);
    }

    @GetMapping(params = "orderId")
    public Review findByOrderId(@RequestParam("orderId") Long orderId) {
        return reviewService.findByOrderId(orderId);
    }

    @GetMapping
    public Iterable<Review> findAll() {
        return reviewService.findAll();
    }

    @GetMapping(params = {"size", "page"})
    public Page<Review> findAll(Pageable pageable) {
        return reviewService.findAll(pageable);
    }

    @PostMapping
    public Review save(@RequestBody Review review) {
        return reviewService.save(review);
    }

    @ExceptionHandler(ReviewNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleReviewNotFoundException(ReviewNotFoundException e) {
        return e.getMessage();
    }

    public ReviewService getReviewService() {
        return reviewService;
    }

    public void setReviewService(ReviewService reviewService) {
        this.reviewService = reviewService;
    }
}
