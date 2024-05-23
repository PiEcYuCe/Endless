package com.java5.assignment.controllers.share;

import com.java5.assignment.content.Page;
import com.java5.assignment.content.PageType;
import com.java5.assignment.model.User;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProfileController {
    @ModelAttribute("page")
    public Page setPageContent() {
        return Page.route.get(PageType.PROFILE);
    }

    @GetMapping("/profile")
    public String goToPage() {
        return "client/index";
    }

    @PostMapping("/profile")
    public String profile(@Valid User user, BindingResult error, Model model) {
        if (error.hasErrors()) {
            model.addAttribute("error", error);
        }
        model.addAttribute("user", user);
        return "client/index";
    }
}
