package com.java5.assignment.controllers.share;

import com.java5.assignment.content.Page;
import com.java5.assignment.content.PageType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

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
}
