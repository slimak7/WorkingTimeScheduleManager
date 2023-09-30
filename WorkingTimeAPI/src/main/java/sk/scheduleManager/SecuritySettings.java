package sk.scheduleManager;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecuritySettings {

    private static final String[] endpointsToPermit = {
            "/Employees/LogIn"
    };

    @Bean
    public WebSecurityCustomizer WebSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(endpointsToPermit);
    }

}
