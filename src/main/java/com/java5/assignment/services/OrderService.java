package com.java5.assignment.services;

import com.java5.assignment.entities.Order;
import com.java5.assignment.jpa.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    public BigDecimal getRevenueToday() {
        return orderRepository.getRevenueToday();
    }

    public BigDecimal getRevenueThisWeek() {
        LocalDate startOfWeek = LocalDate.now().with(DayOfWeek.MONDAY);
        return orderRepository.getRevenueThisWeek(startOfWeek);
    }

    public BigDecimal getRevenueThisMonth() {
        LocalDate startOfMonth = LocalDate.now().withDayOfMonth(1);
        return orderRepository.getRevenueThisMonth(startOfMonth);
    }

    public BigDecimal getRevenueThisYear() {
        LocalDate startOfYear = LocalDate.now().withDayOfYear(1);
        return orderRepository.getRevenueThisYear(startOfYear);
    }
}
