package com.java5.assignment.services;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CookieService {
    @Autowired
    HttpServletResponse resp;

    @Autowired
    HttpSession session;

    @Autowired
    HttpServletRequest req;

    public List<CookieService> getCookies() {
        Cookie[] cookies = req.getCookies();
        if(cookies != null) {
            List<CookieService> cookiesServices = new ArrayList<CookieService>();
            for(Cookie cookie : cookies) {
                CookieService cookieService = new CookieService();
            }
            return cookiesServices;
        }
        else{
            return null;
        }
    }

    public void setCookie(String cookieName, String cookieValue, int time) {
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setMaxAge(time*60*60);
        resp.addCookie(cookie);
    }

    public String getCookie(String cookieName) {
        Cookie[] cookies = req.getCookies();
        if(cookies != null) {
            for(Cookie cookie : cookies) {
                if(cookie.getName().equals(cookieName)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
