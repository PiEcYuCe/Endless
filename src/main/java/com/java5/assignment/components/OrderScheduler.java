package com.java5.assignment.components;

import com.java5.assignment.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class OrderScheduler {
    @Autowired
    private OrderService orderService;

    // Chạy phương thức updateOrderStatus sau mỗi 23 giờ 55 phút (cron expression: "0 55 23 * * *")
    @Scheduled(cron = "0 55 23 * * *")
    public void updateOrderStatusJob() {
        orderService.updateOrderStatus();
    }
}
