package pzinsta.pizzeria.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pzinsta.pizzeria.service.ReviewService;

@Controller
@RequestMapping("/reviews")
public class ReviewsController {

    private ReviewService reviewService;

    @Autowired
    public ReviewsController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public String showReviews(Model model) {
        model.addAttribute("reviews", reviewService.getReviews());
        return "reviews";
    }
}
