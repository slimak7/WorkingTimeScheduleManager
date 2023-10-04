package sk.scheduleManager.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecuritySettings {

    @Autowired
    private AuthReqFilter authReqFilter;

    private static final String[] endpointsToIgnoreAuth = {
            "/Employees/LogIn"
    };

    private static final String[] endpointAvailableForAdmin = {
          "/Employees/Add",
          "/Schedule/CreateNew",
          "/Schedule/GetSchedule"
    };

    @Bean
    public WebSecurityCustomizer WebSecurityCustomizer() {

        return (web) -> web.ignoring().requestMatchers(endpointsToIgnoreAuth);
    }

    @Bean
    public AuthReqFilter GetAuthFilter() {
        return new AuthReqFilter();
    }
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain FilterChain(HttpSecurity http) throws Exception {


        http.authorizeRequests(x -> x.requestMatchers(endpointAvailableForAdmin).hasAuthority(UserRoles.ADMIN.GetRoleName())
                        .anyRequest().authenticated())
            .sessionManagement(x -> x.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(GetAuthFilter(), BasicAuthenticationFilter.class);

        http.csrf().disable();

        return http.build();
    }

    @Bean
    public RoleHierarchy RoleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        String hierarchy = UserRoles.ADMIN.GetRoleName() + " > " + UserRoles.DEFAULT.GetRoleName();
        roleHierarchy.setHierarchy(hierarchy);
        return roleHierarchy;
    }

    @Bean
    public DefaultWebSecurityExpressionHandler WebSecurityExpressionHandler() {
        DefaultWebSecurityExpressionHandler expressionHandler = new DefaultWebSecurityExpressionHandler();
        expressionHandler.setRoleHierarchy(RoleHierarchy());
        return expressionHandler;
    }
}
