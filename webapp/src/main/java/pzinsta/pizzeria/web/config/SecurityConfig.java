package pzinsta.pizzeria.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import pzinsta.pizzeria.web.security.handler.RedirectAuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
@ComponentScan("pzinsta.pizzeria.web.security")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        // TODO: change to bcrypt
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/customer/**").hasRole("REGISTERED_CUSTOMER")
                    .anyRequest().permitAll()
                    .and()
                .formLogin()
                    .loginPage("/login")
                    .permitAll()
                    .successHandler(authenticationSuccessHandler())
                     .failureHandler(authenticationFailureHandler())
                    .and()
                .rememberMe()
                    .userDetailsService(userDetailsService)
                    .and()
                .logout()
                    .invalidateHttpSession(false)
                    .logoutSuccessHandler(logoutSuccessHandler())
                    .permitAll();

    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        SavedRequestAwareAuthenticationSuccessHandler savedRequestAwareAuthenticationSuccessHandler = new SavedRequestAwareAuthenticationSuccessHandler();
        savedRequestAwareAuthenticationSuccessHandler.setTargetUrlParameter("returnUrl");
        return  savedRequestAwareAuthenticationSuccessHandler;
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        SimpleUrlLogoutSuccessHandler simpleUrlLogoutSuccessHandler = new SimpleUrlLogoutSuccessHandler();
        simpleUrlLogoutSuccessHandler.setUseReferer(true);
        simpleUrlLogoutSuccessHandler.setDefaultTargetUrl("/");
        return simpleUrlLogoutSuccessHandler;
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        RedirectAuthenticationFailureHandler redirectAuthenticationFailureHandler = new RedirectAuthenticationFailureHandler();
        redirectAuthenticationFailureHandler.setDefaultReturnUrl("/");
        redirectAuthenticationFailureHandler.setQueryParam("loginError");
        return redirectAuthenticationFailureHandler;
    }
}
