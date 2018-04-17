package pzinsta.pizzeria.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pzinsta.pizzeria.service.OrderService;
import pzinsta.pizzeria.service.exception.OrderNotFoundException;

@Controller
@RequestMapping("/order/track")
public class OrderTrackingController {

    private OrderService orderService;

    @Autowired
    public OrderTrackingController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public String showOrderTrackerSearchForm() {
        return "orderTrackerSearchForm";
    }

    @PostMapping
    public String processOrderTrackerSearch(@RequestParam("trackNumber") String trackNumber, Model model) {
        model.addAttribute("trackNumber", trackNumber);
        return "redirect:/order/track/{trackNumber}";
    }

    @GetMapping("/{trackNumber}")
    public String showOrderTracker(@PathVariable("trackNumber") String trackNumber, Model model) {
        model.addAttribute("order", orderService.getOrderByTrackNumber(trackNumber));
        return "showOrderTracker";
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public String handleOrderNotFoundException(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("trackNumberNotFound", Boolean.TRUE);
        return "redirect:/order/track";
    }
}
