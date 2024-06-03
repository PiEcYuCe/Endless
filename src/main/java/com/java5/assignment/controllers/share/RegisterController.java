package com.java5.assignment.controllers.share;

import com.java5.assignment.content.Page;
import com.java5.assignment.content.PageType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

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

//    @PostMapping("/sign-up")
//    public String registerUser(@Valid @ModelAttribute("user") UserModel user, BindingResult errors) {
//        if (errors.hasErrors()) {
//            return "public/login";
//        }
//        // Handle user registration logic here
//        return "redirect:/login"; // Redirect to success page after successful registration
//    }
}
