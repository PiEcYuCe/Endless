package com.java5.assignment.controllers.admin;

import com.java5.assignment.dto.CartInfo;
import com.java5.assignment.dto.OrderDto;
import com.java5.assignment.entities.Order;
import com.java5.assignment.entities.OrderDetail;
import com.java5.assignment.jpa.OrderDetailRepository;
import com.java5.assignment.jpa.OrderRepository;
import com.java5.assignment.services.CartService;
import com.java5.assignment.services.OrderService;
import com.java5.assignment.utils.Page;
import com.java5.assignment.utils.PageType;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/cancel/order")
    public String cancelOrder(@RequestParam("orderId") long orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            order.setOrderStatus("Cancelled");
            orderRepository.save(order);
        }
        return "admin/layout";
    }

    @PostMapping("/confirm/order")
    public String confirmOrder(@RequestParam("orderId") long orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            order.setOrderStatus("Shipping");
            orderRepository.save(order);
        }
        return "admin/layout";
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
        for(OrderDetail orderDetail : list) {
            orderDetail.setProductVersionID(orderDetail.getProductVersionID());
        }
        return cartService.getByOrder(list);
    }

}