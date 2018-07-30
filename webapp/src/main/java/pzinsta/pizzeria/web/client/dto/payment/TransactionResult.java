package pzinsta.pizzeria.web.client.dto.payment;

import java.io.Serializable;

public class TransactionResult implements Serializable {
    private String transactionId;
    private TransactionStatus transactionStatus;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }
}
