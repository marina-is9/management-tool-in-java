package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.Duration;

@Configuration
@EnableWebSecurity
public class AppServiceProvider extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Enable CORS globally for all endpoints
        http.cors().and()
            .authorizeRequests()
            .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            .anyRequest().authenticated();
        
        // Enable Passport-like token expiration (using JWT or similar)
        http.oauth2Login()
            .tokenEndpoint()
            .accessTokenResponseClient(clientCredentialsTokenResponseClient());

        // Example: Set token expiration in 30 days, using JWT expiration as an example
        // Spring Security would typically handle token expiration, not something you'd set like Passport
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*") // Adjust as needed for your CORS policy
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .maxAge(3600); // Cache CORS preflight response for 1 hour
            }
        };
    }

    // Example method to simulate Passport-like token expiration
    private static OAuth2AccessTokenResponseClient<?> clientCredentialsTokenResponseClient() {
        // Configure OAuth2 token expiration here, depending on how you're issuing JWT tokens
        // For example, using NimbusJwtDecoder to configure expiration
        // You can use Spring Security's OAuth2 or JWT configurations
    }
}
