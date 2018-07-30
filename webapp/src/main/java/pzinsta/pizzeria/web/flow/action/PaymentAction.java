package pzinsta.pizzeria.web.flow.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pzinsta.pizzeria.web.client.PaymentServiceClient;
import pzinsta.pizzeria.web.client.dto.order.Order;
import pzinsta.pizzeria.web.client.dto.payment.SaleRequest;
import pzinsta.pizzeria.web.client.dto.payment.TransactionResult;
import pzinsta.pizzeria.web.client.dto.payment.TransactionStatus;

import javax.money.MonetaryAmount;

@Component
public class PaymentAction {

    private PaymentServiceClient paymentServiceClient;

    @Autowired
    public PaymentAction(PaymentServiceClient paymentServiceClient) {
        this.paymentServiceClient = paymentServiceClient;
    }

    public String generateClientToken() {
        return paymentServiceClient.generateClientToken();
    }

    public TransactionResult chargeCustomer(String nonce, MonetaryAmount amount, Order order) {
        return paymentServiceClient.sale(createSaleRequest(nonce, amount, order));
    }

    public boolean isTransactionSuccessful(TransactionResult transactionResult) {
        return TransactionStatus.SUCCESS.equals(transactionResult.getTransactionStatus());
    }

    private SaleRequest createSaleRequest(String nonce, MonetaryAmount amount, Order order) {
        SaleRequest saleRequest = new SaleRequest();
        saleRequest.setAmount(amount);
        saleRequest.setNonce(nonce);
        saleRequest.setOrderId(order.getId());
        return saleRequest;
    }

    public PaymentServiceClient getPaymentServiceClient() {
        return paymentServiceClient;
    }

    public void setPaymentServiceClient(PaymentServiceClient paymentServiceClient) {
        this.paymentServiceClient = paymentServiceClient;
    }
}
