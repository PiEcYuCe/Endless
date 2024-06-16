package com.java5.assignment.services;

import com.java5.assignment.entities.Order;
import com.java5.assignment.jpa.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashBoardService {
    @Autowired
    OrderRepository orderRepository;

    public List<Order> getOrders() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        return orderRepository.findAll(sort);
    }
}
