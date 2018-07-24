package pzinsta.pizzeria.web.config;

import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.DelegatingLogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;
import org.springframework.web.filter.CompositeFilter;
import pzinsta.pizzeria.web.security.handler.RedirectAuthenticationFailureHandler;

import javax.servlet.Filter;
import java.util.LinkedHashMap;

@Configuration
@EnableOAuth2Client
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private OAuth2ClientContext oauth2ClientContext;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http

                .authorizeRequests()
                .antMatchers("/login/**").permitAll()
                .antMatchers("/customer/**").hasRole("REGISTERED_CUSTOMER")
                .anyRequest().permitAll()
                .and()
                .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and()
                .addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class)
                // .oauth2Login()
                //     .clientRegistrationRepository(clientRegistrationRepository()).
                //     .permitAll()
                //     .and()
                .logout()
                .logoutSuccessHandler(logoutSuccessHandler())
                .permitAll();

    }

    private Filter ssoFilter() {
        CompositeFilter filter = new CompositeFilter();
        filter.setFilters(ImmutableList.of(ssoFilter(internal(), "/login")));
        return filter;
    }
    // todo https://spring.io/guides/tutorials/spring-boot-oauth2/ add more oath2 providers, we could use authentication success handlers to add/update out users in the users microservice etc.

    private Filter ssoFilter(ClientResources client, String path) {
        OAuth2ClientAuthenticationProcessingFilter filter = new OAuth2ClientAuthenticationProcessingFilter(path);
        OAuth2RestTemplate template = new OAuth2RestTemplate(client.getClient(), oauth2ClientContext);
        filter.setRestTemplate(template);
        UserInfoTokenServices tokenServices = new UserInfoTokenServices(
                client.getResource().getUserInfoUri(), client.getClient().getClientId());
        tokenServices.setRestTemplate(template);
        filter.setTokenServices(tokenServices);
        filter.setAuthenticationSuccessHandler(authenticationSuccessHandler());
        return filter;
    }

    @Bean
    @ConfigurationProperties("internal")
    public ClientResources internal() {
        return new ClientResources();
    }

    @Bean
    public FilterRegistrationBean oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(filter);
        registration.setOrder(-100);
        return registration;
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        SavedRequestAwareAuthenticationSuccessHandler savedRequestAwareAuthenticationSuccessHandler = new SavedRequestAwareAuthenticationSuccessHandler();
        savedRequestAwareAuthenticationSuccessHandler.setTargetUrlParameter("returnUrl");
        return savedRequestAwareAuthenticationSuccessHandler;
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        ContentNegotiationStrategy contentNegotiationStrategy = new HeaderContentNegotiationStrategy();

        MediaTypeRequestMatcher jsonMediaTypeRequestMatcher = new MediaTypeRequestMatcher(contentNegotiationStrategy, MediaType.APPLICATION_JSON);
        jsonMediaTypeRequestMatcher.setUseEquals(true);

        LinkedHashMap<RequestMatcher, LogoutSuccessHandler> matcherToHandler = new LinkedHashMap<>();
        matcherToHandler.put(jsonMediaTypeRequestMatcher, new HttpStatusReturningLogoutSuccessHandler());

        DelegatingLogoutSuccessHandler delegatingLogoutSuccessHandler = new DelegatingLogoutSuccessHandler(matcherToHandler);

        SimpleUrlLogoutSuccessHandler simpleUrlLogoutSuccessHandler = new SimpleUrlLogoutSuccessHandler();
        simpleUrlLogoutSuccessHandler.setUseReferer(true);
        simpleUrlLogoutSuccessHandler.setDefaultTargetUrl("/");

        delegatingLogoutSuccessHandler.setDefaultLogoutSuccessHandler(simpleUrlLogoutSuccessHandler);

        return delegatingLogoutSuccessHandler;
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        RedirectAuthenticationFailureHandler redirectAuthenticationFailureHandler = new RedirectAuthenticationFailureHandler();
        redirectAuthenticationFailureHandler.setDefaultReturnUrl("/");
        redirectAuthenticationFailureHandler.setQueryParam("loginError");
        return redirectAuthenticationFailureHandler;
    }

    class ClientResources {

        @NestedConfigurationProperty
        private AuthorizationCodeResourceDetails client = new AuthorizationCodeResourceDetails();

        @NestedConfigurationProperty
        private ResourceServerProperties resource = new ResourceServerProperties();

        public AuthorizationCodeResourceDetails getClient() {
            return client;
        }

        public ResourceServerProperties getResource() {
            return resource;
        }
    }
}
