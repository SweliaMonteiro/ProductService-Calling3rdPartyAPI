package com.example.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;


// @Configuration annotation indicates that a class declares one or more @Bean methods and
// may be processed by the Spring container to generate bean definitions and service requests for those beans at runtime
@Configuration
public class SecurityConfig {

    // This method creates a SecurityFilterChain bean that configures the security of the application
    // All requests to ProductService will pass through this filter chain
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        /*
        ---- USED TO ONLY AUTHENTICATE ALL THE REQUESTS COMING TO PRODUCT SERVICE ----
        http
                // The authorizeHttpRequests method is used to authenticate all the requests coming to ProductService
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().authenticated()
                )
                // The oauth2ResourceServer method is used to configure the OAuth 2.0 Resource Server
                .oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()));
        return http.build();

         */

        http
                // Any requests that matches /products/{id} will go through this filter chain
                .authorizeHttpRequests(authorize -> authorize
                        // RequestMatchers are used to authorize the requests based on the request path i.e. /products/{id}
                        // HasAnyAuthority is used to authorize the requests based on the authorities i.e. SCOPE_ADMIN

                        // Comment this line while using Redis as we are not using authentication while testing Redis
                        .requestMatchers("/products/{id}").hasAnyAuthority("SCOPE_ADMIN")
                        // Any other requests will be permitted
                        .anyRequest().permitAll()
                )
                // The oauth2ResourceServer method is used to configure the OAuth 2.0 Resource Server
                .oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()));
        return http.build();
    }

}
