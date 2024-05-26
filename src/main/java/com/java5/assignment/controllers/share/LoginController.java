package com.java5.assignment.controllers.share;

import com.java5.assignment.content.Page;
import com.java5.assignment.content.PageType;
import com.java5.assignment.services.CookieService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String goToPage(Model model, @RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("rememberMe") Boolean rememberMe) {
         if(username == null){
            model.addAttribute("error", "Please enter username and password");
         }
         else if(password == null){
             model.addAttribute("error", "Please enter username and password");
         }

        if(username.equalsIgnoreCase("admin")&& password.equalsIgnoreCase("12345")){
            if(rememberMe == true){
                cookieService.setCookie("username", username, 60);
            }
            session.setAttribute("username", username);
            return "redirect:/dashboard";
        }

        if(username.equalsIgnoreCase("user")&& password.equalsIgnoreCase("12345")){
            if(rememberMe == true){
                cookieService.setCookie("username", username, 60);
            }
            session.setAttribute("username", username);
            return "redirect:/home";
        }

        return "public/login";
    }
}
