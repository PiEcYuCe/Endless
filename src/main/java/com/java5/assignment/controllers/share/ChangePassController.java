package com.java5.assignment.controllers.share;

import com.java5.assignment.content.Page;
import com.java5.assignment.content.PageType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class ChangePassController {
    @ModelAttribute("page")
    public Page setPageContent() {
        return Page.route.get(PageType.CHANGE_PASSWORD);
    }

    @GetMapping("/change-password")
    public String goToPage() {
        return "client/index";
    }
}
