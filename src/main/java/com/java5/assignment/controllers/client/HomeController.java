package com.java5.assignment.controllers.client;

import com.java5.assignment.content.Page;
import com.java5.assignment.content.PageType;
import com.java5.assignment.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller("")
public class HomeController {
    @Autowired
    HttpSession session;

    @ModelAttribute("page")
    public Page page() {
        return Page.route.get(PageType.HOME);
    }


    @ModelAttribute("account")
    public UserDto account() {
        UserDto user = (UserDto) session.getAttribute("user");
        return user;
    }

    @GetMapping("/home")
    public String get() {
        return "client/index";
    }




    @PostMapping("/home")
    public String post(@ModelAttribute("page") Page page) {
        return "client/index";
    }
}


