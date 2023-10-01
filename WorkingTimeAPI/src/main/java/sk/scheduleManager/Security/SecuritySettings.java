package sk.scheduleManager.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.AbstractRequestMatcherRegistry;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecuritySettings {

    @Autowired
    private AuthReqFilter authReqFilter;

    private static final String[] endpointsToPermit = {
            "/Employees/LogIn"
    };

    @Bean
    public WebSecurityCustomizer WebSecurityCustomizer() {

        return (web) -> web.ignoring().requestMatchers(endpointsToPermit);
    }

    @Bean
    public AuthReqFilter GetAuthFilter() {
        return new AuthReqFilter();
    }
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain FilterChain(HttpSecurity http) throws Exception {


        http.authorizeRequests(x -> x.requestMatchers("**").permitAll().anyRequest().authenticated())
            .sessionManagement(x -> x.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(GetAuthFilter(), BasicAuthenticationFilter.class);

        http.csrf().disable();

        return http.build();
    }
}
