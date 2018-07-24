package pzinsta.pizzeria.web.controller;

import org.apache.commons.lang3.StringUtils;
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
import pzinsta.pizzeria.web.client.DeliveryServiceClient;
import pzinsta.pizzeria.web.client.OrderServiceClient;
import pzinsta.pizzeria.web.client.dto.order.Order;
import pzinsta.pizzeria.web.exception.OrderNotFoundException;

@Controller
@RequestMapping("/order/track")
public class OrderTrackingController {

    private OrderServiceClient orderServiceClient;
    private DeliveryServiceClient deliveryServiceClient;

    @Autowired
    public OrderTrackingController(OrderServiceClient orderServiceClient, DeliveryServiceClient deliveryServiceClient) {
        this.orderServiceClient = orderServiceClient;
        this.deliveryServiceClient = deliveryServiceClient;
    }

    @GetMapping
    public String showOrderTrackerSearchForm() {
        return "orderTrackerSearchForm";
    }

    @PostMapping
    public String processOrderTrackerSearch(@RequestParam("trackingNumber") String trackingNumber, Model model) {
        model.addAttribute("trackingNumber", StringUtils.trim(trackingNumber));
        return "redirect:/order/track/" + StringUtils.trim(trackingNumber);
    }

    @GetMapping("/{trackingNumber}")
    public String showOrderTracker(@PathVariable("trackingNumber") String trackingNumber, Model model) {
        model.addAttribute("order", findOrderByTrackingNumber(trackingNumber));
        model.addAttribute("delivery", deliveryServiceClient.findByOrderId(findOrderByTrackingNumber(trackingNumber).getId()).orElse(null));
        return "showOrderTracker";
    }

    private Order findOrderByTrackingNumber(@PathVariable("trackingNumber") String trackingNumber) {
        return orderServiceClient.findByTrackingNumber(trackingNumber)
                    .orElseThrow(() -> new OrderNotFoundException(String.format("Order with tracking number %s not found.", trackingNumber)));
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public String handleOrderNotFoundException(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("trackingNumberNotFound", Boolean.TRUE);
        return "redirect:/order/track";
    }
}
