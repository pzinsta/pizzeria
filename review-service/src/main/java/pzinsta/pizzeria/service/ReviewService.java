package pzinsta.pizzeria.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pzinsta.pizzeria.exception.ReviewNotFoundException;
import pzinsta.pizzeria.model.Review;
import pzinsta.pizzeria.repository.ReviewRepository;

@Service
public class ReviewService {

    private ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public Iterable<Review> findAll() {
        return reviewRepository.findAll();
    }

    public Page<Review> findAll(Pageable pageable) {
        return reviewRepository.findAll(pageable);
    }

    public Review findById(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException(id));
    }

    public Review findByOrderId(Long orderId) {
        return reviewRepository.findByOrderId(orderId)
                .orElseThrow(() -> new ReviewNotFoundException(String.format("Review with order ID %s not found.", orderId)));
    }

    public Review save(Review review) {
        return reviewRepository.save(composeReview(review));
    }

    private Review composeReview(Review review) {
        Review mergedReview = reviewRepository.findByOrderId(review.getOrderId())
                .orElseGet(Review::new);
        mergedReview.setImages(review.getImages());
        mergedReview.setMessage(review.getMessage());
        mergedReview.setOrderId(review.getOrderId());
        mergedReview.setRating(review.getRating());
        return mergedReview;
    }

    public ReviewRepository getReviewRepository() {
        return reviewRepository;
    }

    public void setReviewRepository(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }
}
