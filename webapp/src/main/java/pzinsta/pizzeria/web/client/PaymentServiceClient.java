package pzinsta.pizzeria.web.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pzinsta.pizzeria.web.client.dto.payment.SaleRequest;
import pzinsta.pizzeria.web.client.dto.payment.TransactionResult;

@Component
@FeignClient(
        name = "payment-service",
        decode404 = true
)
public interface PaymentServiceClient {

    @GetMapping("/clientToken")
    public String generateClientToken();

    @PostMapping("/transactions/sale")
    public TransactionResult sale(@RequestBody SaleRequest saleRequest);
}
