package gr.aueb.network_book.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

/**
 * Each class has @Configuration , could have methods which return beans
 * and be inserted immediately in IoC Container (IoC container handles the creation and injection of objects,
 * making it easier to maintain and test applications.)
 */

@Configuration  // Define the configuration for creating and managing beans.
@AllArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    private final AuthenticationProvider authenticationProvider;
    private final JwtFilter jwtAuthFilter;

    @Bean // These methods will return instances of the required beans.
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        http
                .cors(withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                                        req.requestMatchers(
                                                        "/auth/**",
                                                        "/v2/api-docs",
                                                        "/v3/api-docs",
                                                        "/v3/api-docs/**",
                                                        "/swagger-resources",
                                                        "/swagger-resources/**",
                                                        "/configuration/ui",
                                                        "/configuration/security",
                                                        "/swagger-ui/**",
                                                        "/webjars/**",
                                                        "/swagger-ui.html"
                                        )
                                                .permitAll()
                                                .anyRequest()
                                                    .authenticated()
                    )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
