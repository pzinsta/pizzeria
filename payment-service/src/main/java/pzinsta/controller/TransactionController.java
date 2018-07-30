package pzinsta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pzinsta.model.SaleRequest;
import pzinsta.model.TransactionResult;
import pzinsta.service.TransactionService;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/sale")
    public TransactionResult sale(@RequestBody SaleRequest saleRequest) {
        return transactionService.sale(saleRequest);
    }
}
