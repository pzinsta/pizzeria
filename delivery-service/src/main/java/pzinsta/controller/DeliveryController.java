package pzinsta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pzinsta.exception.DeliveryNotFoundException;
import pzinsta.model.Delivery;
import pzinsta.service.DeliveryService;

import javax.money.MonetaryAmount;

@RestController
@RequestMapping("/deliveries")
public class DeliveryController {

    private DeliveryService deliveryService;

    @Autowired
    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @GetMapping("/{id}")
    public Delivery findById(@PathVariable("id") Long id) {
        return deliveryService.findById(id);
    }

    @GetMapping("/{id}/cost")
    public MonetaryAmount calculateCostById(@PathVariable("id") Long id) {
        return deliveryService.calculateCostById(id);
    }

    @GetMapping(params = "orderId")
    public Delivery findByOrderId(@RequestParam("orderId") Long orderId) {
        return deliveryService.findByOrderId(orderId);
    }

    @PostMapping
    public Delivery create(@RequestBody Delivery delivery) {
        return deliveryService.create(delivery);
    }

    @PutMapping("/{id}")
    public Delivery update(@PathVariable("id") Long id, @RequestBody Delivery delivery) {
        return deliveryService.update(id, delivery);
    }

    @PostMapping("/cost")
    public MonetaryAmount calculateCost(@RequestBody Delivery delivery) {
        return deliveryService.calculateCost(delivery);
    }

    @ExceptionHandler(DeliveryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleDeliveryNotFoundException(DeliveryNotFoundException e) {
        return e.getMessage();
    }

    public DeliveryService getDeliveryService() {
        return deliveryService;
    }

    public void setDeliveryService(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }
}
