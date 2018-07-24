package pzinsta.pizzeria.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pzinsta.pizzeria.web.client.FileStorageServiceClient;
import pzinsta.pizzeria.web.client.ReviewServiceClient;
import pzinsta.pizzeria.web.client.dto.Review;
import pzinsta.pizzeria.web.model.ReviewDTO;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/reviews")
public class ReviewsController {

    private ReviewServiceClient reviewServiceClient;
    private FileStorageServiceClient fileStorageServiceClient;

    @Value("${reviews.per.page}")
    private int reviewsPerPage;

    @Autowired
    public ReviewsController(ReviewServiceClient reviewServiceClient, FileStorageServiceClient fileStorageServiceClient) {
        this.reviewServiceClient = reviewServiceClient;
        this.fileStorageServiceClient = fileStorageServiceClient;
    }

    @GetMapping
    public String showReviews(Model model) {
        return showReviewsForPage(1, model);
    }

    @GetMapping("/{pageNumber}")
    public String showReviewsForPage(@PathVariable("pageNumber") int pageNumber, Model model) {
        Page<Review> reviews = reviewServiceClient.findAll(reviewsPerPage, pageNumber - 1);
        model.addAttribute("currentPageNumber", pageNumber);
        model.addAttribute("totalPagesCount", reviews.getTotalPages());
        model.addAttribute("reviews", getReviews(reviews.getContent()));
        return "reviews";
    }

    private List<ReviewDTO> getReviews(Collection<Review> reviewResources) {
        return reviewResources.stream()
                .map(review -> {
                    ReviewDTO reviewDTO = new ReviewDTO();
                    reviewDTO.setCreatedOn(review.getCreatedOn());
                    reviewDTO.setLastUpdatedOn(review.getLastUpdatedOn());
                    reviewDTO.setMessage(review.getMessage());
                    reviewDTO.setRating(review.getRating());
                    reviewDTO.setImages(review.getImages().stream().map(fileStorageServiceClient::getFile).collect(Collectors.toList()));
                    return reviewDTO;
                }).collect(Collectors.toList());
    }

    public int getReviewsPerPage() {
        return reviewsPerPage;
    }

    public void setReviewsPerPage(int reviewsPerPage) {
        this.reviewsPerPage = reviewsPerPage;
    }
}
