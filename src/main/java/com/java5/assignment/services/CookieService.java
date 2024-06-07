package com.java5.assignment.services;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CookieService {
    @Autowired
    HttpServletResponse resp;

    @Autowired
    HttpServletRequest req;

    @Autowired
    EncodeService encode;

    public void setCookie(String cookieName, String cookieValue, int time) {
        String encodedCookieValue = encode.hashCode(cookieValue);
        Cookie cookie = new Cookie(cookieName, encodedCookieValue);
        cookie.setMaxAge(time * 60 * 60);
        resp.addCookie(cookie);
    }

    public String getCookie(String cookieName) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookieName.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}

