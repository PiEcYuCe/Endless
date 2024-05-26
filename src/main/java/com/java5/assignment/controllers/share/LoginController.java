package com.java5.assignment.controllers.share;

import com.java5.assignment.content.Page;
import com.java5.assignment.content.PageType;
import com.java5.assignment.model.User;
import com.java5.assignment.services.CookieService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.Session;
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
    public String goToPage(Model model, @RequestParam(name = "username1", defaultValue = "") String username,
                           @RequestParam(name = "password1", defaultValue = "") String password,
                           @RequestParam(name = "rememberMe", defaultValue = "false") Boolean rememberMe) {
        if (username.isEmpty()) {
            model.addAttribute("error", "Please enter username ");
        } else if (password.isEmpty()) {
            model.addAttribute("error1", "Please enter password");
        }

        if (username.equalsIgnoreCase("admin") && password.equalsIgnoreCase("12345")) {
            if (rememberMe == true) {
                cookieService.setCookie("username", username, 60);
            } else if (rememberMe == false) {
                session.setAttribute("username", username);
                return "redirect:/dashboard";
            }

        }

        if (username.equalsIgnoreCase("user") && password.equalsIgnoreCase("12345")) {
            if (rememberMe == true) {
                cookieService.setCookie("username", username, 60);
            }
            session.setAttribute("username", username);
            return "redirect:/home";
        }

        return "public/login";
    }

    @GetMapping("/logout")
    public String logout(){
        return "redirect:/login";
    }


}
