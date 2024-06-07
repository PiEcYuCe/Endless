package com.java5.assignment.controllers.client;

import com.java5.assignment.utils.Page;
import com.java5.assignment.utils.PageType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class ProductDetailController {
    @ModelAttribute("page")
    public Page setPageContent() {
        return Page.route.get(PageType.DETAIL_PRODUCT);
    }

    @GetMapping("/product/detail")
    public String productDetail() {

        return "client/index";
    }
}
