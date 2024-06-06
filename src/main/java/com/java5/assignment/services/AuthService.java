package com.java5.assignment.services;

import com.java5.assignment.dto.UserDto;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    HttpSession session;

    @Autowired
    CookieService cookieService;

    public boolean isLogin() {
        UserDto userDto = (UserDto) session.getAttribute("user");
        return userDto != null;
    }

    public boolean isAdmin() {
        UserDto userDto = (UserDto) session.getAttribute("user");
        return userDto.getRole();
    }

    public boolean isStatus(){
        UserDto userDto = (UserDto) session.getAttribute("user");
        return userDto.getStatus();
    }


}

