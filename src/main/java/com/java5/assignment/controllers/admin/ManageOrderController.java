package com.java5.assignment.controllers.admin;

import com.java5.assignment.dto.ProductInfoDTO;
import com.java5.assignment.dto.UserDto;
import com.java5.assignment.dto.UserInfoDto;
import com.java5.assignment.dto.VoucherDto;
import com.java5.assignment.entities.Order;
import com.java5.assignment.entities.OrderDetail;
import com.java5.assignment.entities.UserVoucher;
import com.java5.assignment.entities.Voucher;
import com.java5.assignment.jpa.OrderRepository;
import com.java5.assignment.jpa.ProductVersionRepository;
import com.java5.assignment.jpa.UserVoucherRepository;
import com.java5.assignment.model.OrderRequest;
import com.java5.assignment.services.OrderDetailService;
import com.java5.assignment.services.ProductVersionService;
import com.java5.assignment.services.UserService;
import com.java5.assignment.services.VoucherService;
import com.java5.assignment.utils.Page;
import com.java5.assignment.utils.PageType;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ManageOrderController {
    @Autowired
    HttpSession session;

    @Autowired
    ProductVersionRepository productVersionRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductVersionService productVersionService;

    @Autowired
    private UserService userService;

    @Autowired
    UserVoucherRepository userVoucherRepository;

    @Autowired
    VoucherService voucherService;

    @Autowired
    OrderDetailService orderDetailService;

    @ModelAttribute("page")
    public Page page() {
        return Page.route.get(PageType.ADMIN_ORDER);
    }

    @ModelAttribute("productVersions")
    public List<ProductInfoDTO> productVersions() {
        return productVersionService.getAllProductActive();
    }

    @GetMapping("/api/productVersions")
    @ResponseBody
    public List<ProductInfoDTO> getProductVersions() {
        return productVersionService.getAllProductActive();
    }

    @GetMapping("/api/userInfo")
    @ResponseBody
    public UserInfoDto getUserInfo(@RequestParam("userKey") String userKey) {
        return userService.getUserInfo(userKey);
    }

    @GetMapping("/api/getVoucherByAmountOrder")
    @ResponseBody
    public List<VoucherDto> getVoucherByAmountOrder(@RequestParam("amount") BigDecimal amount) {
        List<Voucher> vouchers =  userVoucherRepository.findByAmount( amount);
        List<VoucherDto> voucherDtos = new ArrayList<>();
        for (Voucher voucher : vouchers) {
            voucherDtos.add(voucherService.entityToDto(voucher));
        }
        return voucherDtos;
    }

    @GetMapping("/manage-order")
    public String get(Model model) throws IOException {
        return "admin/layout";
    }

    @PostMapping("/api/addNewOrder")
    @ResponseBody
    public boolean createOrderWithDetails(@RequestBody OrderRequest orderRequest) {
        try{
            orderDetailService.saveOrderAndDetail(orderRequest.getOrderData(), orderRequest.getOrderDetailData());
            return true;
        }
        catch(Exception e){
            return false;
        }
    }
}
