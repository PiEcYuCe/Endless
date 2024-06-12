package com.java5.assignment.controllers.admin;

import com.java5.assignment.entities.Order;
import com.java5.assignment.jpa.OrderRepository;
import com.java5.assignment.utils.Page;
import com.java5.assignment.utils.PageType;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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


    @ModelAttribute("page")
    public Page page() {
        return Page.route.get(PageType.ADMIN_DASHBOARD);
    }

    @ModelAttribute("orders")
    public List<Order> orders() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        return orderRepository.findAll(sort);
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

}
