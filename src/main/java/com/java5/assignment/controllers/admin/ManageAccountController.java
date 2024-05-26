package com.java5.assignment.controllers.admin;

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
public class ManageAccountController {
    @ModelAttribute("page")
    public Page page() {
        return Page.route.get(PageType.ADMIN_ACCOUNT);
    }

    @GetMapping("/manage-account")
    public String get() {
        return "admin/layout";
    }

    @PostMapping("/manage-account")
    public String profile(@Valid User user, BindingResult error, Model model) {
        if (error.hasErrors()) {
            model.addAttribute("error", error);
        }
        model.addAttribute("user", user);
        return "admin/layout";
    }
}
