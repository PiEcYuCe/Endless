package com.java5.assignment.controllers.admin;

import com.java5.assignment.dto.CartInfo;
import com.java5.assignment.dto.OrderDto;
import com.java5.assignment.entities.Order;
import com.java5.assignment.entities.OrderDetail;
import com.java5.assignment.jpa.OrderDetailRepository;
import com.java5.assignment.jpa.OrderRepository;
import com.java5.assignment.services.CartService;
import com.java5.assignment.services.EmailService;
import com.java5.assignment.services.OrderService;
import com.java5.assignment.utils.*;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class DashBoardController {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Autowired
    CartService cartService;

    @Autowired
    OrderService orderService;

    @Autowired
    EmailService emailService;

    @ModelAttribute("page")
    public Page page() {
        return Page.route.get(PageType.ADMIN_DASHBOARD);
    }

    @ModelAttribute("orders")
    public List<OrderDto> orders() {
        return orderService.getAllOrdersDto();
    }

    @GetMapping("/dashboard")
    public String get() {
        return "admin/layout";
    }

    @PostMapping("/admin/cancel/order")
    public String cancelOrder(@RequestParam("orderId") long orderId, @RequestParam(name = "check", defaultValue = "false") boolean check , RedirectAttributes redirectAttributes) {
        if(check){
            Order order = orderRepository.findById(orderId).orElse(null);
            if (order != null && order.getOrderStatus().equals("Processing")) {
                order.setOrderStatus("Cancelled");
                try{
                    orderRepository.save(order);
                    SuccessModal successModal = new SuccessModal("Order cancellation successful !");
                    emailService.sendCancelOrder(order.getId(), order.getUserID().getEmail());
                    redirectAttributes.addFlashAttribute("successModal", successModal);
                    return "redirect:/dashboard";
                } catch (MessagingException e) {
                    ErrorModal errorModal = new ErrorModal("We cannot find this order information!");
                    redirectAttributes.addFlashAttribute("errorModal", errorModal);
                    return "redirect:/dashboard";
                }
            }
            else {
                ErrorModal errorModal = new ErrorModal("Orders that have been processed or previously canceled");
                redirectAttributes.addFlashAttribute("errorModal", errorModal);
                return "redirect:/dashboard";
            }
        }
        else{
            String message = "Do you want to delete this order?";
            String text = "Confirm";
            String link = "/admin/cancel/order?orderId="+orderId;
            WarningModal warningModal = new WarningModal(message, text, link);
            redirectAttributes.addFlashAttribute("warningModal", warningModal);
            return "redirect:/dashboard";
        }
    }

    @PostMapping("/admin/confirm/order")
    public String confirmOrder(@RequestParam("orderId") long orderId, @RequestParam(name = "check", defaultValue = "false") boolean check ,RedirectAttributes redirectAttributes) {
        if (check) {
            Order order = orderRepository.findById(orderId).orElse(null);
            if (order != null) {
                if (order.getOrderStatus().equals("Processing")) {
                    order.setOrderStatus("Shipping");
                    orderRepository.save(order);
                    SuccessModal successModal = new SuccessModal("Order confirmation successful!");
                    redirectAttributes.addFlashAttribute("successModal", successModal);
                } else {
                    ErrorModal errorModal = new ErrorModal("Order has already been cancelled and cannot be confirmed.");
                    redirectAttributes.addFlashAttribute("errorModal", errorModal);
                }
            } else {
                ErrorModal errorModal = new ErrorModal("Order not found.");
                redirectAttributes.addFlashAttribute("errorModal", errorModal);
            }
        } else {
            String message = "Do you want to confirm this order?";
            String text = "Confirm";
            String link = "/admin/confirm/order?orderId=" + orderId;
            WarningModal warningModal = new WarningModal(message, text, link);
            redirectAttributes.addFlashAttribute("warningModal", warningModal);
        }

        return "redirect:/dashboard";
    }


    @GetMapping("/api/get-order")
    @ResponseBody
    public OrderDto getOrder(@RequestParam("orderId") long orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            return orderService.getOrderDto(order);
        }
        return null;
    }

    @GetMapping("/api/get-order-detail")
    @ResponseBody
    public List<CartInfo> getOrderDetail(@RequestParam("orderId") long orderID) {
        List<OrderDetail> list = orderDetailRepository.findAllByOrderID(orderID);
        for (OrderDetail orderDetail : list) {
            orderDetail.setProductVersionID(orderDetail.getProductVersionID());
        }
        return cartService.getByOrder(list);
    }

}