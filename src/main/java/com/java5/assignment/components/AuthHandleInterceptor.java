package com.java5.assignment.components;

import com.java5.assignment.services.AuthService;
import com.java5.assignment.utils.constants;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.codec.binary.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class AuthHandleInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(AuthHandleInterceptor.class);

    @Autowired
    AuthService authService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("Pre handle: {}", request.getRequestURI());

        String uri = request.getRequestURI();
        boolean isAdminRequired = uri.contains("manage") || uri.contains("statistical") || uri.contains("dashboard");

        if (authService.isLogin()) {
            if (authService.isAdmin() || !isAdminRequired) {
                return true;
            } else {
                response.sendRedirect("/logout");
                return false;
            }
        } else {
            String queryString = request.getQueryString() == null ? "" : "?" + request.getQueryString();
            String redirectUrl = String.format("/login?path=%s%s", uri, queryString);
            response.sendRedirect(redirectUrl);
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        logger.info("Post handle: {}", request.getRequestURI());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        logger.info("After completion: {}", request.getRequestURI());
    }
}

