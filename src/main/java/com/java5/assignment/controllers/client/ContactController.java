package com.java5.assignment.controllers.client;

import com.java5.assignment.content.Page;
import com.java5.assignment.content.PageType;
import com.java5.assignment.model.UserModel;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ContactController {
    @ModelAttribute("page")
    public Page setPageContent() {
        return Page.route.get(PageType.CONTACT);
    }

    @GetMapping("/contact")
    public String goToPage() {
        return "client/index";
    }

    @PostMapping("/contact")
    public String profile(@Valid UserModel userModel, BindingResult error, Model model) {
        if (error.hasErrors()) {
            model.addAttribute("error", error);
            System.out.println(error.getFieldError("phone").getDefaultMessage());
        }
        model.addAttribute("user", userModel);
        return "client/index";
    }
}
