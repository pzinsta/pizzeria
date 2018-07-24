package pzinsta.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pzinsta.service.strategy.DeliveryCostCalculationStrategy;
import pzinsta.service.strategy.impl.FixedDeliveryCostCalculationStrategy;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import java.math.BigDecimal;

@Configuration
public class AppConfig {

    @Bean
    public DeliveryCostCalculationStrategy deliveryCostCalculationStrategy(@Value("${delivery.cost}") BigDecimal cost) {
        MonetaryAmount deliveryCost = Monetary.getDefaultAmountFactory().setNumber(cost).setCurrency("USD").create();
        return new FixedDeliveryCostCalculationStrategy(deliveryCost);
    }
}
