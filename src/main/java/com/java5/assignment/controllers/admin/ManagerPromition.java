package com.java5.assignment.controllers.admin;

import com.java5.assignment.content.Page;
import com.java5.assignment.content.PageType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class ManagerPromition {
    @ModelAttribute("page")
    public Page page() {
        return Page.route.get(PageType.ADMIN_PROMOTION);
    }

    @GetMapping("/manage-promotion")
    public String get() {

        return "admin/layout";
    }
}



