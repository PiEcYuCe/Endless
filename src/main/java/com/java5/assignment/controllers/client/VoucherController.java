package com.java5.assignment.controllers.client;

import com.java5.assignment.entities.Voucher;
import com.java5.assignment.jpa.VoucherRepository;
import com.java5.assignment.utils.Page;
import com.java5.assignment.utils.PageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Controller

public class VoucherController {
    @Autowired
    VoucherRepository voucherRepository;

    @ModelAttribute("page")
    public Page page() {
        return Page.route.get(PageType.VOUCHER);
    }

    @GetMapping("/voucher")
    public String get(Model model) {
        List<Voucher> vouchers = voucherRepository.findAll();
        model.addAttribute("vouchers", vouchers);
        return "client/index";
    }


}
