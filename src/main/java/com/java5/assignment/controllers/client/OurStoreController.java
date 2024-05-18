package com.java5.assignment.controllers.client;

import com.java5.assignment.content.Page;
import com.java5.assignment.content.PageType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class OurStoreController {
    @ModelAttribute("page")
    public String page() {
        return Page.route.get(PageType.HOME).getUrl();
    }

    @GetMapping("/our-store")
    public String get() {
        return "client/index";
    }
}
