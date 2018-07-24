package pzinsta.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
import pzinsta.exception.OrderNotFoundException;
import pzinsta.model.Order;
import pzinsta.service.OrderService;

import javax.money.MonetaryAmount;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    public Order findById(@PathVariable("id") Long id) {
        return orderService.findById(id);
    }

    @GetMapping(params = "trackingNumber")
    public Order findByTrackingNumber(@RequestParam("trackingNumber") String trackingNumber) {
        return orderService.findByTrackingNumber(trackingNumber);
    }

    @GetMapping(params = "customerId")
    public Iterable<Order> findAllByCustomerId(@RequestParam("customerId") Long customerId) {
        return orderService.findAllByCustomerId(customerId);
    }

    @PostMapping
    public Order post(@RequestBody Order order) {
        return orderService.post(order);
    }

    @GetMapping("/{id}/cost")
    public MonetaryAmount calculateCost(@PathVariable("id") Long id) {
        return orderService.calculateCostById(id);
    }

    @PostMapping("/cost")
    public MonetaryAmount calculateCost(@RequestBody Order order) {
        return orderService.calculateCost(order);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleOrderNotFoundException(OrderNotFoundException e) {
        return e.getMessage();
    }
}
