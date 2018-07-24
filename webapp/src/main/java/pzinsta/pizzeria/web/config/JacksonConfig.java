package pzinsta.pizzeria.web.config;

import com.fasterxml.jackson.databind.Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.jackson.datatype.money.MoneyModule;
import pzinsta.pizzeria.web.util.jackson.PageJacksonModule;

@Configuration
public class JacksonConfig {
    @Bean
    public Module moneyModule() {
        return new MoneyModule().withDefaultFormatting();
    }

    @Bean
    public Module pageModule() {
        return new PageJacksonModule();
    }
}
