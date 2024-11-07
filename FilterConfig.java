package com.example.project.config;

import com.example.project.filter.CustomHostFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<CustomHostFilter> loggingFilter() {
        FilterRegistrationBean<CustomHostFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new CustomHostFilter());
        registrationBean.addUrlPatterns("/auth/*"); // Apply filter to these routes
        return registrationBean;
    }
}
