package pzinsta.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AccountsResourceConfig extends ResourceServerConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .antMatcher("/accounts/**")
                .authorizeRequests().anyRequest().permitAll()
                .and().csrf().disable();
    }

}
