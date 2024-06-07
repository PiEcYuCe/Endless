package com.java5.assignment.controllers.client;

import com.java5.assignment.content.Page;
import com.java5.assignment.content.PageType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

public class VoucherController {
    @ModelAttribute("page")
    public Page page() {
        return Page.route.get(PageType.VOUCHER);
    }

    @GetMapping("/voucher")
    public String get() {
        return "client/index";
    }
}
