package pzinsta.pizzeria.web.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pzinsta.pizzeria.model.order.Order;
import pzinsta.pizzeria.model.order.Review;
import pzinsta.pizzeria.service.OrderService;
import pzinsta.pizzeria.service.dto.ReviewDTO;
import pzinsta.pizzeria.service.exception.OrderNotFoundException;
import pzinsta.pizzeria.web.form.ReviewForm;

import javax.validation.Valid;
import java.util.Optional;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/review/order")
public class ReviewController {

    private final static int[] RATINGS = IntStream.rangeClosed(1, 10).toArray();

    private OrderService orderService;

    @Autowired
    public ReviewController(OrderService orderService) {
        this.orderService = orderService;
    }

    @ModelAttribute("ratings")
    public int[] ratings() {
        return RATINGS;
    }

    @GetMapping
    public String showOrderSearchForm() {
        return "orderReviewSearchForm";
    }

    @PostMapping
    public String processOrderSearchForm(@RequestParam("trackNumber") String trackNumber, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("trackNumber", StringUtils.trim(trackNumber));
        return "redirect:/review/order/{trackNumber}";
    }

    @GetMapping("/{trackNumber}")
    public String showOrderReviewSubmissionForm(@PathVariable("trackNumber") String trackNumber, Model model, @RequestParam(name = "returnUrl", defaultValue = "/reviews") String returnUrl) {
        Order order = orderService.getOrderByTrackNumber(trackNumber);
        model.addAttribute("order", order);
        model.addAttribute("reviewForm", getReviewForm(order));
        return "orderReviewSubmissionForm";
    }

    private ReviewForm getReviewForm(Order order) {
        return Optional.ofNullable(order.getReview()).map(ReviewController::transformReviewToReviewForm).orElseGet(ReviewForm::new);
    }

    @PostMapping("/{trackNumber}")
    public String processOrderReviewSubmissionForm(@PathVariable("trackNumber") String trackNumber, @ModelAttribute("reviewForm") @Valid ReviewForm reviewForm, BindingResult bindingResult, @RequestParam(name = "returnUrl", defaultValue = "/reviews") String returnUrl, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()) {
            return "orderReviewSubmissionForm";
        }
        ReviewDTO reviewDTO = transformReviewFormToReviewDTO(reviewForm);
        orderService.addReviewToOrderByTrackNumber(trackNumber, reviewDTO);
        return "redirect:" + returnUrl;
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public String handleOrderNotFoundException(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("orderNotFound", Boolean.TRUE);
        return "redirect:/review/order";
    }

    private ReviewDTO transformReviewFormToReviewDTO(ReviewForm reviewForm) {
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setMessage(reviewForm.getMessage());
        reviewDTO.setRating(reviewForm.getRating());
        return reviewDTO;
    }

    private static ReviewForm transformReviewToReviewForm(Review review) {
        ReviewForm reviewForm = new ReviewForm();
        reviewForm.setMessage(review.getMessage());
        reviewForm.setRating(review.getRating());
        return reviewForm;
    }
}
