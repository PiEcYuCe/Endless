package com.java5.assignment.controllers.client;

import com.java5.assignment.content.Page;
import com.java5.assignment.content.PageType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class CartController {
    @ModelAttribute("page")
    public Page page() {
        return Page.route.get(PageType.CART);
    }

    @GetMapping("/cart")
    public String get() {
        return "client/index";
    }
}
