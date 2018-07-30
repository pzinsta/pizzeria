package pzinsta.config;

import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Environment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BraintreeConfig {

    @Bean
    public BraintreeGateway braintreeGateway(@Value("${braintree.merchantId}") String merchantId,
                                             @Value("${braintree.publicKey}") String publicKey,
                                             @Value("${braintree.privateKey}") String privateKey) {

        return new BraintreeGateway(Environment.SANDBOX, merchantId, publicKey, privateKey);
    }
}
