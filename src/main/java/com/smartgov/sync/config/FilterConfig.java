package com.smartgov.sync.config;

import com.smartgov.sync.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public FilterRegistrationBean<JwtAuthenticationFilter> loggingFilter(){
        FilterRegistrationBean<JwtAuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
        
        registrationBean.setFilter(jwtAuthenticationFilter);
        // Apply JWT validation only under the /api/ prefix
        registrationBean.addUrlPatterns("/api/*");
        // Ensure authentication path is excluded inside the filter itself
        registrationBean.setOrder(1);
        
        return registrationBean;
    }
}
