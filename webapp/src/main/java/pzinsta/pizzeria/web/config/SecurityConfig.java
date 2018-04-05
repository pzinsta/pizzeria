package pzinsta.pizzeria.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import pzinsta.pizzeria.web.security.handler.RedirectAuthenticationFailureHandler;

@EnableWebSecurity
@ComponentScan("pzinsta.pizzeria.web.security")
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        // TODO: change to bcrypt
        return NoOpPasswordEncoder.getInstance();
    }

    // @Override
    // protected void configure(HttpSecurity http) throws Exception {
    //     http
    //             .authorizeRequests()
    //             .antMatchers("/resources/**").permitAll()
    //             .antMatchers("/staff/manager/**").hasRole("MANAGER")
    //             .antMatchers("/staff/deliveryperson/**").hasRole("DELIVERYPERSON")
    //             //.anyRequest().authenticated()
    //             .and()
    //             .formLogin()
    //             .loginPage("/login")
    //             .permitAll()
    //             .and()
    //             .logout()
    //             .permitAll();
    // }

    // @Autowired
    // public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    //     auth
    //             .inMemoryAuthentication()
    //             .withUser("user").password("{noop}password").roles("USER");
    // }

    // @Configuration
    // @Order(1)
    // public static class ManagerWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
    //     protected void configure(HttpSecurity http) throws Exception {
    //         http
    //                 .antMatcher("/staff/manager/**")
    //                 .authorizeRequests()
    //                 .anyRequest().hasRole("MANAGER")
    //                 .and()
    //                 .formLogin().loginPage("/login").permitAll()
    //                 .and().logout().permitAll();
    //     }
    // }

    @Order(1)
    @Configuration
    public static class CustomerWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .antMatcher("/customer/**").authorizeRequests()
                    .anyRequest().hasRole("REGISTERED_CUSTOMER")
                    .and()
                    .formLogin().loginPage("/login").permitAll()
                    .and()
                    .logout().permitAll();
        }
    }

    @Configuration
    public static class DefaultWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests().anyRequest().permitAll()
                    .and()
                    .formLogin().loginPage("/login")
                    .permitAll()
                    .successHandler(authenticationSuccessHandler())
                    .failureHandler(authenticationFailureHandler())
                    .and()
                    .logout().invalidateHttpSession(false).logoutSuccessHandler(logoutSuccessHandler()).permitAll();
        }
    }

    @Bean
    public static AuthenticationSuccessHandler authenticationSuccessHandler() {
        SavedRequestAwareAuthenticationSuccessHandler savedRequestAwareAuthenticationSuccessHandler = new SavedRequestAwareAuthenticationSuccessHandler();
        savedRequestAwareAuthenticationSuccessHandler.setUseReferer(true);
        return  savedRequestAwareAuthenticationSuccessHandler;
    }

    @Bean
    public static LogoutSuccessHandler logoutSuccessHandler() {
        SimpleUrlLogoutSuccessHandler simpleUrlLogoutSuccessHandler = new SimpleUrlLogoutSuccessHandler();
        simpleUrlLogoutSuccessHandler.setUseReferer(true);
        simpleUrlLogoutSuccessHandler.setDefaultTargetUrl("/");
        return simpleUrlLogoutSuccessHandler;
    }

    @Bean
    public static AuthenticationFailureHandler authenticationFailureHandler() {
        RedirectAuthenticationFailureHandler redirectAuthenticationFailureHandler = new RedirectAuthenticationFailureHandler();
        redirectAuthenticationFailureHandler.setDefaultReturnUrl("/");
        redirectAuthenticationFailureHandler.setReturnUrlParameterName("returnUrl");
        redirectAuthenticationFailureHandler.setQueryParam("loginError");
        return redirectAuthenticationFailureHandler;
    }
}
