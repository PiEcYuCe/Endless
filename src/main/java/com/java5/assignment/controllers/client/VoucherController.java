package com.java5.assignment.controllers.client;

import com.java5.assignment.entities.Product;
import com.java5.assignment.entities.Voucher;
import com.java5.assignment.jpa.VoucherRepository;
import com.java5.assignment.utils.Page;
import com.java5.assignment.utils.PageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Controller

public class VoucherController {
    @Autowired
    private VoucherRepository voucherRepository;

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

    @GetMapping("/getVoucherInfo/{id}")
    @ResponseBody
    public ResponseEntity<?> getVoucherInfo(@PathVariable Long id) {
        Voucher voucher = voucherRepository.findById(id).orElse(null);
        if (voucher != null) {
            return ResponseEntity.ok(voucher);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/voucher")
    public String editVoucher(@RequestParam("id") long id, Model model) {
        Voucher voucher = voucherRepository.findById(id).orElse(null);
        if (voucher != null) {
            model.addAttribute("voucher", voucher);
            return "admin/layout";
        } else {
            // Xử lý trường hợp voucher không tồn tại
            return "redirect:/voucher"; // Hoặc một trang lỗi khác
        }
    }


}
