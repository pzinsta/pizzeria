package pzinsta.service.impl;

import com.braintreegateway.BraintreeGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pzinsta.service.ClientTokenGenerationService;

@Service
public class BraintreeClientTokenGenerationService implements ClientTokenGenerationService {

    private BraintreeGateway braintreeGateway;

    @Autowired
    public BraintreeClientTokenGenerationService(BraintreeGateway braintreeGateway) {
        this.braintreeGateway = braintreeGateway;
    }

    @Override
    public String generateClientToken() {
        return braintreeGateway.clientToken().generate();
    }
}
