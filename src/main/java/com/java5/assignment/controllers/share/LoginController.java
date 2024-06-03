package com.java5.assignment.controllers.share;

import com.java5.assignment.content.Page;
import com.java5.assignment.content.PageType;
import com.java5.assignment.model.LoginModel;
import com.java5.assignment.services.CookieService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    @Autowired
    CookieService cookieService;

    @Autowired
    HttpSession session;

    @ModelAttribute("page")
    public Page setPageContent() {
        return Page.route.get(PageType.LOGIN);
    }

    @GetMapping("/login")
    public String login() {
        return "public/login";
    }

    @PostMapping("/login")
    public String goToPage(Model model, @Valid LoginModel loginModel, BindingResult error) {
        if(error.hasErrors()) {
            model.addAttribute("loginError", error);
            return "public/login";
        }
        return "public/login";
    }

}
