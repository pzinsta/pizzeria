package pzinsta.pizzeria.web.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataConfig {
    
    @Bean
    public DataSource dataSource() {
        return null;
    }
}
