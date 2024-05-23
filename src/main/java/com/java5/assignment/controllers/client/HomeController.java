package com.java5.assignment.controllers.client;

import com.java5.assignment.content.Page;
import com.java5.assignment.content.PageType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller("")
public class HomeController {
    @ModelAttribute("page")
    public Page page() {
        return Page.route.get(PageType.HOME);
    }

    @GetMapping("/home")
    public String get() {
        return "client/index";
    }

    @GetMapping("/login2")
    public String get2() {
        return "public/login2";
    }
//    @GetMapping("/about")
//    public String get3() {
//        return "client/about_us";
//    }

    @PostMapping("/home")
    public String post(@ModelAttribute("page") Page page) {
        return "client/index";
    }
}


