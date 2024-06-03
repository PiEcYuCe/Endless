package com.java5.assignment.controllers.share;

import com.java5.assignment.content.Page;
import com.java5.assignment.content.PageType;
import com.java5.assignment.model.RegisterModel;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {
    @ModelAttribute("page")
    public Page setPageContent() {
        return Page.route.get(PageType.LOGIN);
    }

    @GetMapping("/sign-up")
    public String goToPage() {
        return "public/login";
    }

    @PostMapping("/sign-up")
    public String signUp(@Valid RegisterModel registerModel, BindingResult error, Model model) {
        if(error.hasErrors()) {
            model.addAttribute("registerError", error);
            model.addAttribute("page", "register");
            return "public/login";
        }
        return "public/login";
    }

}
