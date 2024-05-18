package com.java5.assignment.controllers.share;

import com.java5.assignment.content.Page;
import com.java5.assignment.content.PageType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class RegisterController {
    @ModelAttribute("page")
    public String setPageContent() {
        return Page.route.get(PageType.REGISTER).getUrl();
    }

    @GetMapping("/register")
    public String goToPage() {
        return "client/index";
    }
}
