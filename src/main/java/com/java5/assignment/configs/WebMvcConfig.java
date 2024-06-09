package com.java5.assignment.configs;

import com.java5.assignment.components.AccountInterceptor;
import com.java5.assignment.components.CategoryInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private AccountInterceptor accountInterceptor;

    @Autowired
    private CategoryInterceptor categoryInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(accountInterceptor);
        registry.addInterceptor(categoryInterceptor);
    }

}
