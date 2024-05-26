package com.java5.assignment.controllers.admin;

import com.java5.assignment.content.Page;
import com.java5.assignment.content.PageType;
import com.java5.assignment.model.Product;
import com.java5.assignment.model.ProductVersion;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ManageProductController {
    @ModelAttribute("page")
    public Page page() {
        return Page.route.get(PageType.ADMIN_PRODUCT);
    }

    @GetMapping("/manage-product")
    public String get() {

        return "admin/layout";
    }

    @PostMapping("/manage-product")
    public String profile(@Valid Product pro, BindingResult error, Model model) {
        if (error.hasErrors()) {
            model.addAttribute("error", error);
        }
        model.addAttribute("pro", pro);
        return "admin/layout";
    }

}
