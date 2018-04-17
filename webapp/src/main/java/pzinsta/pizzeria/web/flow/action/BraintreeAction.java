package pzinsta.pizzeria.web.flow.action;

import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Result;
import com.braintreegateway.Transaction;
import com.braintreegateway.TransactionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pzinsta.pizzeria.model.order.Order;

import java.math.BigDecimal;

@Component
public class BraintreeAction {

    private BraintreeGateway braintreeGateway;

    @Autowired
    public BraintreeAction(BraintreeGateway braintreeGateway) {
        this.braintreeGateway = braintreeGateway;
    }

    public String generateClientToken() {
        return braintreeGateway.clientToken().generate();
    }

    public boolean chargeCustomer(String nonce, Order order) {
        BigDecimal amount = order.getCost().getNumber().numberValue(BigDecimal.class);

        TransactionRequest transactionRequest = new TransactionRequest().paymentMethodNonce(nonce)
                .amount(amount).options().submitForSettlement(true).done();


        Result<Transaction> transactionResult = braintreeGateway.transaction().sale(transactionRequest);

        if (transactionResult.isSuccess()) {
            order.setPaymentTransactionId(transactionResult.getTarget().getId());
        }

        return transactionResult.isSuccess();
    }

}
