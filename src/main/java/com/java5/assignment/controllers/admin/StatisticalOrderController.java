package com.java5.assignment.controllers.admin;

import com.java5.assignment.jpa.OrderDetailRepository;
import com.java5.assignment.jpa.OrderRepository;
import com.java5.assignment.jpa.ProductVersionRepository;
import com.java5.assignment.services.OrderService;
import com.java5.assignment.utils.Page;
import com.java5.assignment.utils.PageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;

@Controller
public class StatisticalOrderController {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderDetailRepository orderDetailRepository;
    @Autowired
    private ProductVersionRepository productVersionRepository;

    @ModelAttribute("page")
    public Page page() {
        return Page.route.get(PageType.ADMIN_STATISTICAL_ORDER);
    }

    @GetMapping("/statistical-order")
    public String get() {
        return "admin/layout";
    }

    @GetMapping("/api/count-order-wating")
    @ResponseBody
    public int getCountOrderWating() {
        return orderRepository.countByOrderStatus("Processing");
    }

    @GetMapping("/api/count-order-shipping")
    @ResponseBody
    public int getCountOrderShipping() {
        return orderRepository.countByOrderStatus("Shipping");
    }

    @GetMapping("/api/count-order-dlivered")
    @ResponseBody
    public int getCountOrderDiverled() {
        return orderRepository.countByOrderStatus("Delivered");
    }

    @GetMapping("/api/count-order-cancel")
    @ResponseBody
    public int getCountOrderCancel() {
        return orderRepository.countByOrderStatus("Cancelled");
    }

    @GetMapping("/api/revenue-today")
    public ResponseEntity<BigDecimal> getRevenueToday() {
        BigDecimal revenueToday = orderService.getRevenueToday();
        return ResponseEntity.ok(revenueToday);
    }

    @GetMapping("/api/revenue-this-week")
    public ResponseEntity<BigDecimal> getRevenueThisWeek() {
        BigDecimal revenueThisWeek = orderService.getRevenueThisWeek();
        return ResponseEntity.ok(revenueThisWeek);
    }

    @GetMapping("/api/revenue-this-month")
    public ResponseEntity<BigDecimal> getRevenueThisMonth() {
        BigDecimal revenueThisMonth = orderService.getRevenueThisMonth();
        return ResponseEntity.ok(revenueThisMonth);
    }

    @GetMapping("/api/revenue-this-year")
    public ResponseEntity<BigDecimal> getRevenueThisYear() {
        BigDecimal revenueThisYear = orderService.getRevenueThisYear();
        return ResponseEntity.ok(revenueThisYear);
    }

    @GetMapping("/api/product-sold")
    @ResponseBody
    public Long getProductSold() {
        return orderDetailRepository.countProductSold();
    }

    @GetMapping("/api/product-shipping")
    @ResponseBody
    public Long getShippingProducts() {
        return orderDetailRepository.countProductShipping();
    }

    @GetMapping("/api/product-low-stock")
    @ResponseBody
    public Long getLowStockProducts() {
        return productVersionRepository.countProductVersionsLowStockProducts();
    }

    @GetMapping("/api/product-out-of-stock")
    @ResponseBody
    public Long getOutOfStockProducts() {
        return productVersionRepository.countProductVersionsOutOfStockProducts();
    }

}
