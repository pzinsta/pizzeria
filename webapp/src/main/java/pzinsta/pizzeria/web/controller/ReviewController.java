package pzinsta.pizzeria.web.controller;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pzinsta.pizzeria.model.order.Order;
import pzinsta.pizzeria.model.order.Review;
import pzinsta.pizzeria.service.OrderService;
import pzinsta.pizzeria.service.dto.ReviewDTO;
import pzinsta.pizzeria.service.exception.OrderNotFoundException;
import pzinsta.pizzeria.web.client.FileStorageServiceClient;
import pzinsta.pizzeria.web.client.dto.File;
import pzinsta.pizzeria.web.form.ReviewForm;
import pzinsta.pizzeria.web.validator.ReviewFormValidator;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/review/order")
public class ReviewController {

    private static final int[] RATINGS = IntStream.rangeClosed(1, 10).toArray();

    private OrderService orderService;
    private FileStorageServiceClient fileStorageServiceClient;
    private ReviewFormValidator reviewFormValidator;

    @Autowired
    public ReviewController(OrderService orderService, FileStorageServiceClient fileStorageServiceClient, ReviewFormValidator reviewFormValidator) {
        this.orderService = orderService;
        this.fileStorageServiceClient = fileStorageServiceClient;
        this.reviewFormValidator = reviewFormValidator;
    }

    @InitBinder("reviewForm")
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(reviewFormValidator);
        webDataBinder.setBindEmptyMultipartFiles(false);
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
    public String processOrderSearchForm(@RequestParam("trackingNumber") String trackingNumber, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("trackingNumber", StringUtils.trim(trackingNumber));
        return "redirect:/review/order/{trackingNumber}";
    }

    @GetMapping("/{trackingNumber}")
    public String showOrderReviewSubmissionForm(@PathVariable("trackingNumber") String trackingNumber, Model model, @RequestParam(name = "returnUrl", defaultValue = "/reviews") String returnUrl) {
        Order order = orderService.getOrderByTrackingNumber(trackingNumber);
        model.addAttribute("order", order);
        model.addAttribute("reviewForm", getReviewForm(order));
        return "orderReviewSubmissionForm";
    }

    private ReviewForm getReviewForm(Order order) {
        return Optional.ofNullable(order.getReview()).map(ReviewController::transformReviewToReviewForm).orElseGet(ReviewForm::new);
    }

    @PostMapping("/{trackingNumber}")
    public String processOrderReviewSubmissionForm(@PathVariable("trackingNumber") String trackingNumber, @ModelAttribute("reviewForm") @Valid ReviewForm reviewForm, BindingResult bindingResult, @RequestParam(name = "returnUrl", defaultValue = "/reviews") String returnUrl, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()) {
            return "orderReviewSubmissionForm";
        }
        ReviewDTO reviewDTO = transformReviewFormToReviewDTO(reviewForm);
        orderService.addReviewToOrderByTrackingNumber(trackingNumber, reviewDTO);
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
        reviewDTO.setFiles(processImages(reviewForm));
        return reviewDTO;
    }

    private List<Long> processImages(ReviewForm reviewForm) {
        return reviewForm.getImages().stream().map(this::saveImage).filter(Optional::isPresent)
                    .map(Optional::get).map(File::getId).collect(Collectors.toList());
    }

    private Optional<File> saveImage(MultipartFile multipartFile) {
        try {
            return Optional.ofNullable(fileStorageServiceClient.saveFile(IOUtils.toByteArray(multipartFile.getInputStream()), multipartFile.getContentType()));
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    private static ReviewForm transformReviewToReviewForm(Review review) {
        ReviewForm reviewForm = new ReviewForm();
        reviewForm.setMessage(review.getMessage());
        reviewForm.setRating(review.getRating());
        return reviewForm;
    }

    public ReviewFormValidator getReviewFormValidator() {
        return reviewFormValidator;
    }

    public void setReviewFormValidator(ReviewFormValidator reviewFormValidator) {
        this.reviewFormValidator = reviewFormValidator;
    }
}
