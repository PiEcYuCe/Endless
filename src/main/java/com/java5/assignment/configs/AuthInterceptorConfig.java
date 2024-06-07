package com.java5.assignment.configs;

import com.java5.assignment.components.AuthHandleInterceptor;
import com.java5.assignment.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AuthInterceptorConfig implements WebMvcConfigurer {
    @Autowired
    AuthHandleInterceptor authHandleInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authHandleInterceptor)
                .excludePathPatterns("/error", "/ui/**", "/images/**", "/css/**", "/js/**", "/home/**","/product/**","/about-us", "/contact", "/logout", "/login", "/register");
    }
}