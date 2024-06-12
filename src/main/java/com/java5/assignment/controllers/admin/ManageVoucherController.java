package com.java5.assignment.controllers.admin;

import com.java5.assignment.entities.Brand;
import com.java5.assignment.entities.Voucher;
import com.java5.assignment.jpa.VoucherRepository;
import com.java5.assignment.model.ProductModel;
import com.java5.assignment.model.VoucherModel;
import com.java5.assignment.utils.Page;
import com.java5.assignment.utils.PageType;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ManageVoucherController {
    @Autowired
    private VoucherRepository voucherRepository;

    @ModelAttribute("page")
    public Page page() {
        return Page.route.get(PageType.ADMIN_VOUCHER);
    }

    @ModelAttribute("Vouchers")
    public List<Voucher> getVouchers() {
        return voucherRepository.findAll()
                .stream()
                .sorted((o1, o2) -> o2.getId().compareTo(o1.getId()))
                .collect(Collectors.toList());
    }


    @GetMapping("/manage-voucher")
    public String get() {
        return "admin/layout";
    }


    @PostMapping("/manage-voucher")
    public String post(@Valid VoucherModel voucher, BindingResult error, Model model) {

        model.addAttribute("error", error);

        return "admin/layout";
    }


    @PostMapping("/add-voucher")
    public String addVoucher(@Valid VoucherModel voucherModel, BindingResult error, Model model) {
        if (error.hasErrors()) {
            model.addAttribute("error", error);
            return "admin/layout";
        }

        Voucher voucher = new Voucher();
        voucher.setVoucherCode(voucherModel.getVoucherCode());
        voucher.setLeastBill(BigDecimal.valueOf(voucherModel.getLeastBill()));
        voucher.setLeastDiscount(BigDecimal.valueOf(voucherModel.getLeastDiscount()));
        voucher.setBiggestDiscount(BigDecimal.valueOf(voucherModel.getBiggestDiscount()));
        voucher.setDiscountLevel(voucherModel.getDiscountLevel());
        voucher.setDiscountForm(voucherModel.getDiscountForm());
        voucher.setStartDate(voucherModel.getStartDate());
        voucher.setEndDate(voucherModel.getEndDate());

        voucherRepository.save(voucher);

        return "redirect:/manage-voucher";
    }

    @PostMapping("/edit-voucher")
    public String editVoucher(@RequestParam("id") long id, Model model) {
        Voucher voucher = voucherRepository.findById(id).get();
        model.addAttribute("voucher", voucher);
        return "admin/layout";

    }

    @PostMapping("/update-voucher")
    public String updateVoucher(@Valid VoucherModel voucherModel, BindingResult error, Model model,
                                @RequestParam("id") long id) {
        if (error.hasErrors()) {
            model.addAttribute("error", error);
            return "admin/layout";

        }

        Voucher voucher = voucherRepository.findById(id).get();
        voucher.setVoucherCode(voucherModel.getVoucherCode());
        voucher.setLeastBill(BigDecimal.valueOf(voucherModel.getLeastBill()));
        voucher.setLeastDiscount(BigDecimal.valueOf(voucherModel.getLeastDiscount()));
        voucher.setBiggestDiscount(BigDecimal.valueOf(voucherModel.getBiggestDiscount()));
        voucher.setDiscountLevel(voucherModel.getDiscountLevel());
        voucher.setDiscountForm(voucherModel.getDiscountForm());
        voucher.setStartDate(voucherModel.getStartDate());
        voucher.setEndDate(voucherModel.getEndDate());

        voucherRepository.save(voucher);
        return "redirect:/manage-voucher";
    }

    @PostMapping("/delete-voucher")
    public String removeVoucher(@RequestParam("id") long id) {
        Voucher voucher = voucherRepository.findById(id).get();
        voucherRepository.delete(voucher);
        return "redirect:/manage-voucher";
    }

    @GetMapping("/clear-voucher")
    public String clearForm() {
        return "redirect:/manage-voucher";
    }
}
