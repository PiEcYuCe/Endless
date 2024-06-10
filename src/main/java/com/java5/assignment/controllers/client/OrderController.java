package com.java5.assignment.controllers.client;

import com.java5.assignment.dto.UserDto;
import com.java5.assignment.entities.Order;
import com.java5.assignment.entities.User;
import com.java5.assignment.jpa.OrderRepository;
import com.java5.assignment.jpa.UserRepository;
import com.java5.assignment.utils.Page;
import com.java5.assignment.utils.PageType;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class OrderController {
    @Autowired
    HttpSession session;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;

    @ModelAttribute("page")
    public Page setPageContent() {
        return Page.route.get(PageType.ORDER);
    }

    @ModelAttribute("Orders")
    public List<Order> getOrders() {
        UserDto userDto = (UserDto) session.getAttribute("user");
        return orderRepository.findByUserID(userDto.getId());
    }

    @GetMapping("/order")
    public String goToPage(Model model) {
        UserDto userDto = (UserDto)session.getAttribute("user");
        User user = userRepository.findById(userDto.getId()).get();
        model.addAttribute("user", user);
        return "client/index";
    }

    @PostMapping("/rating")
    public String addOrder(@ModelAttribute("Order") Order order) {
        UserDto userDto = (UserDto)session.getAttribute("user");
        User user = userRepository.findById(userDto.getId()).get();

        return "";
    }
}
