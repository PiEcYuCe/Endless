package com.java5.assignment.controllers.share;

import com.java5.assignment.entities.Order;
import com.java5.assignment.jpa.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class DemoController {
    @Autowired
    private OrderRepository orderRepo;

    @ResponseBody
    @GetMapping("/demo")
    public List<Order> demo() {
        return orderRepo.findAll();
    }
}
