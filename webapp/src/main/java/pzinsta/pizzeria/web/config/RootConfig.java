package pzinsta.pizzeria.web.config;

import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Environment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import pzinsta.pizzeria.model.order.Cart;
import pzinsta.pizzeria.service.impl.strategy.RandomTrackNumberGenerationStrategy;
import pzinsta.pizzeria.service.impl.strategy.TrackNumberGenerationStrategy;

import static org.springframework.context.annotation.ScopedProxyMode.TARGET_CLASS;
import static org.springframework.web.context.WebApplicationContext.SCOPE_SESSION;

@Configuration
@Import({DataConfig.class, SecurityConfig.class})
@ComponentScan("pzinsta.pizzeria.service")
@PropertySource("classpath:application.properties")
public class RootConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    @Scope(value = SCOPE_SESSION, proxyMode = TARGET_CLASS)
    public Cart cart() {
        return new Cart();
    }

    @Bean
    public TrackNumberGenerationStrategy trackNumberGenerationStrategy() {
        return new RandomTrackNumberGenerationStrategy();
    }

    @Bean
    public BraintreeGateway braintreeGateway(@Value("${braintree.merchantId}") String merchantId,
                                             @Value("${braintree.publicKey}") String publicKey,
                                             @Value("${braintree.privateKey}") String privateKey) {

        return new BraintreeGateway(Environment.SANDBOX, merchantId, publicKey, privateKey);
    }
}
