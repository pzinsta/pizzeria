package pzinsta.pizzeria.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pzinsta.pizzeria.model.order.Review;
import pzinsta.pizzeria.service.ReviewService;
import pzinsta.pizzeria.web.client.FileStorageServiceClient;
import pzinsta.pizzeria.web.model.ReviewDTO;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/reviews")
public class ReviewsController {

    private ReviewService reviewService;
    private FileStorageServiceClient fileStorageServiceClient;

    @Value("${reviews.per.page}")
    private int reviewsPerPage;

    @Autowired
    public ReviewsController(ReviewService reviewService, FileStorageServiceClient fileStorageServiceClient) {
        this.reviewService = reviewService;
        this.fileStorageServiceClient = fileStorageServiceClient;
    }

    @GetMapping
    public String showReviews(Model model) {
        return showReviewsForPage(1, model);
    }

    @GetMapping("/{pageNumber}")
    public String showReviewsForPage(@PathVariable("pageNumber") int pageNumber, Model model) {
        int offset = (pageNumber - 1) * reviewsPerPage;
        model.addAttribute("currentPageNumber", pageNumber);
        model.addAttribute("totalPagesCount", Math.ceil(reviewService.getTotalCount() / reviewsPerPage));
        model.addAttribute("reviews", getReviews(reviewService.getReviews(offset, reviewsPerPage)));
        return "reviews";
    }

    private List<ReviewDTO> getReviews(List<Review> reviews) {
        return reviews.stream().map(review -> {
            ReviewDTO reviewDTO = new ReviewDTO();
            reviewDTO.setCreatedOn(review.getCreatedOn());
            reviewDTO.setLastUpdatedOn(review.getLastUpdatedOn());
            reviewDTO.setMessage(review.getMessage());
            reviewDTO.setRating(review.getRating());
            reviewDTO.setImages(review.getImages().stream().map(fileStorageServiceClient::getFile).collect(Collectors.toList()));
            return reviewDTO;
        }).collect(Collectors.toList());
    }

    public ReviewService getReviewService() {
        return reviewService;
    }

    public void setReviewService(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    public int getReviewsPerPage() {
        return reviewsPerPage;
    }

    public void setReviewsPerPage(int reviewsPerPage) {
        this.reviewsPerPage = reviewsPerPage;
    }
}
