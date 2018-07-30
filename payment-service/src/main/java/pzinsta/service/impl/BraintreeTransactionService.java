package pzinsta.service.impl;

import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Result;
import com.braintreegateway.Transaction;
import com.braintreegateway.Transaction.Status;
import com.braintreegateway.TransactionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pzinsta.model.SaleRequest;
import pzinsta.model.TransactionResult;
import pzinsta.model.TransactionStatus;
import pzinsta.service.TransactionService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.EnumSet;
import java.util.Optional;
import java.util.Set;

@Service
public class BraintreeTransactionService implements TransactionService {

    private static final Set<Status> TRANSACTION_SUCCESS_STATUSES = EnumSet.of(
            Status.AUTHORIZED,
            Status.AUTHORIZING,
            Status.SETTLED,
            Status.SETTLEMENT_CONFIRMED,
            Status.SETTLEMENT_PENDING,
            Status.SETTLING,
            Status.SUBMITTED_FOR_SETTLEMENT
    );

    private BraintreeGateway braintreeGateway;

    @Autowired
    public BraintreeTransactionService(BraintreeGateway braintreeGateway) {
        this.braintreeGateway = braintreeGateway;
    }

    @Override
    public TransactionResult sale(SaleRequest saleRequest) {
        Result<Transaction> saleTransactionResult = braintreeGateway.transaction()
                .sale(saleTransactionRequest(saleRequest));
        return extractTransactionResult(saleTransactionResult);
    }

    private TransactionRequest saleTransactionRequest(SaleRequest saleRequest) {

        return new TransactionRequest()
                .orderId(Long.toString(saleRequest.getOrderId()))
                .paymentMethodNonce(saleRequest.getNonce())
                .amount(extractAmount(saleRequest))
                .options().submitForSettlement(true).done();
    }

    private BigDecimal extractAmount(SaleRequest saleRequest) {
        return saleRequest.getAmount().getNumber().numberValue(BigDecimal.class).setScale(2, RoundingMode.HALF_EVEN);
    }

    private TransactionResult extractTransactionResult(Result<Transaction> saleTransactionResult) {
        TransactionResult transactionResult = new TransactionResult();
        transactionResult.setTransactionStatus(extractTransactionStatus(saleTransactionResult));
        transactionResult.setTransactionId(Optional.ofNullable(saleTransactionResult.getTarget()).map(Transaction::getId).orElse(""));
        return transactionResult;
    }

    private TransactionStatus extractTransactionStatus(Result<Transaction> transactionResult) {
        if (transactionResult.isSuccess()) {
            return isTransactionSuccessful(transactionResult.getTarget()) ? TransactionStatus.SUCCESS : TransactionStatus.FAILURE;
        }
        return TransactionStatus.FAILURE;
    }

    private boolean isTransactionSuccessful(Transaction transaction) {
        return TRANSACTION_SUCCESS_STATUSES.contains(transaction.getStatus());
    }

}
