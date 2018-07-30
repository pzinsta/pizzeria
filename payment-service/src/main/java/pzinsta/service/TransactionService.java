package pzinsta.service;

import pzinsta.model.SaleRequest;
import pzinsta.model.TransactionResult;

public interface TransactionService {

    TransactionResult sale(SaleRequest saleRequest);
}
