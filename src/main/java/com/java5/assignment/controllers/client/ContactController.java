package com.java5.assignment.controllers.client;

import com.java5.assignment.content.Page;
import com.java5.assignment.content.PageType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ContactController {
    @ModelAttribute("page")
    public String setPageContent() {
        return Page.route.get(PageType.CONTACT).getUrl();
    }

    @GetMapping("/contact")
    public String goToPage() {
        return "client/index";
    }

    @PostMapping("/contact")
    public String goToPageSubmit() {
        return "client/index";
    }
}
