package com.java5.assignment.controllers.admin;

import com.java5.assignment.dto.*;
import com.java5.assignment.entities.Order;
import com.java5.assignment.entities.OrderDetail;
import com.java5.assignment.entities.Voucher;
import com.java5.assignment.jpa.OrderDetailRepository;
import com.java5.assignment.jpa.OrderRepository;
import com.java5.assignment.jpa.ProductVersionRepository;
import com.java5.assignment.jpa.UserVoucherRepository;
import com.java5.assignment.model.Order.OrderRequest;
import com.java5.assignment.services.*;
import com.java5.assignment.utils.ErrorModal;
import com.java5.assignment.utils.Page;
import com.java5.assignment.utils.PageType;
import com.java5.assignment.utils.SuccessModal;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    OrderService orderService;

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Autowired
    AuthService authService;

    @ModelAttribute("page")
    public Page page() {
        return Page.route.get(PageType.ADMIN_ORDER);
    }

    @ModelAttribute("productVersions")
    public List<ProductInfoDTO> productVersions() {
        return productVersionService.getAllProductActive();
    }

    @ModelAttribute("orderDetails")
    public List<OrderDetail> orderDetails() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        return orderDetailRepository.findAll(sort);
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

    @PostMapping("/api/addNewOrder")
    @ResponseBody
    public boolean createOrderWithDetails(@RequestBody OrderRequest orderRequest, Model model) {
        try{
            orderDetailService.saveOrderAndDetail(orderRequest.getOrderData(), orderRequest.getOrderDetailData());
            SuccessModal successModal = new SuccessModal("Create order successful !");
            model.addAttribute("successModal", successModal);
            return true;
        }
        catch(Exception e){
            ErrorModal errorModal = new ErrorModal("Create order error");
            model.addAttribute("errorModal", errorModal);
            e.printStackTrace();
            return false;
        }
    }

    @GetMapping("/manage-order")
    public String get(Model model) throws IOException {
        return "admin/layout";
    }

    @GetMapping("/api/get-all-order")
    @ResponseBody
    public List<OrderDto> getListOrders(){
        if(!authService.isAdmin()){
            UserDto userDto = (UserDto) session.getAttribute("user");
            return orderService.getAllOrdersDtoByUserID(userDto.getId());
        }
        return orderService.getAllOrdersDto();
    }
}
