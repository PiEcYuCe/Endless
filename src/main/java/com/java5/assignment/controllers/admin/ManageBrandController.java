package com.java5.assignment.controllers.admin;

import com.java5.assignment.content.Page;
import com.java5.assignment.content.PageType;
import com.java5.assignment.model.BrandModel;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ManageBrandController {
    @ModelAttribute("page")
    public Page page() {
        return Page.route.get(PageType.ADMIN_BRAND);
    }

    @GetMapping("/manage-brand")
    public String get() {
        return "admin/layout";
    }

    @PostMapping("/manage-brand")
    public String profile(@Valid BrandModel brandModel, BindingResult error, Model model) {
        if (error.hasErrors()) {
            model.addAttribute("error", error);
        }
        model.addAttribute("brand", brandModel);
        return "admin/layout";
    }
}
