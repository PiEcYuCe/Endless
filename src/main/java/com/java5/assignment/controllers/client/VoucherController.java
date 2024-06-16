package com.java5.assignment.controllers.client;

import com.java5.assignment.dto.UserDto;
import com.java5.assignment.entities.Voucher;
import com.java5.assignment.jpa.VoucherRepository;
import com.java5.assignment.services.UserService;
import com.java5.assignment.utils.Page;
import com.java5.assignment.utils.PageType;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Controller

public class VoucherController {
    @Autowired
    VoucherRepository voucherRepository;

    @Autowired
    HttpSession session;

    @Autowired
    UserService userService;

    @ModelAttribute("page")
    public Page page() {
        return Page.route.get(PageType.VOUCHER);
    }

    @GetMapping("/voucher")
    public String get(Model model) {
        UserDto userDto = (UserDto) session.getAttribute("user");
        List<Voucher> allVouchers = voucherRepository.findAllActive();
        List<Voucher> saveVouchers = userService.getSavedVouchers(userDto.getId());
        List<Voucher> notSaveVouchers = userService.getNotSavedVouchers(userDto.getId(), allVouchers);
        model.addAttribute("saveVouchers", saveVouchers);
        model.addAttribute("notSaveVouchers", saveVouchers);

        return "client/index";
    }

    @GetMapping("/saveVouchers")
    @ResponseBody
    public List<Voucher> saveVouchers() {
        UserDto userDto = (UserDto) session.getAttribute("user");
        List<Voucher> allVouchers = voucherRepository.findAllActive();
        List<Voucher> saveVouchers = userService.getSavedVouchers(userDto.getId());
        List<Voucher> notSaveVouchers = userService.getNotSavedVouchers(userDto.getId(), allVouchers);
        return saveVouchers;
    }

    @GetMapping("/notSaveVouchers")
    @ResponseBody
    public List<Voucher> notSaveVouchers() {
        UserDto userDto = (UserDto) session.getAttribute("user");
        List<Voucher> allVouchers = voucherRepository.findAllActive();
        List<Voucher> saveVouchers = userService.getSavedVouchers(userDto.getId());
        List<Voucher> notSaveVouchers = userService.getNotSavedVouchers(userDto.getId(), allVouchers);
        return notSaveVouchers;
    }


}
