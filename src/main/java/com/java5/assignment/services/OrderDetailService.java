package com.java5.assignment.services;

import com.java5.assignment.entities.Order;
import com.java5.assignment.entities.OrderDetail;
import com.java5.assignment.entities.Voucher;
import com.java5.assignment.jpa.*;
import com.java5.assignment.model.Order.OrderData;
import com.java5.assignment.model.Order.OrderDetailData;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderDetailService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    VoucherRepository voucherRepository;

    @Autowired
    ProductVersionRepository productVersionRepository;

    @Transactional
    public List<OrderDetail> saveOrderAndDetail(OrderData orderData, List<OrderDetailData> orderDetailData) {
        Order order = new Order();
        order.setUserID(userRepository.getById(orderData.getUserID()));
        if (orderData.getVoucherID()!=-1) {
            order.setVoucherID(voucherRepository.getById(orderData.getVoucherID()));
        }
        order.setOrderDate(orderData.getOrderDate());
        order.setTotalMoney(orderData.getTotalMoney());
        order.setOrderStatus(orderData.getOrderStatus());

        Order savedOrder = orderRepository.save(order);

        List<OrderDetail> orderDetails = new ArrayList<>();

        for (OrderDetailData orderDetaildt : orderDetailData) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderID(savedOrder);
            orderDetail.setProductVersionID(productVersionRepository.getById(orderDetaildt.getProductVersionID()));
            orderDetail.setQuantity(orderDetaildt.getQuantity());
            orderDetail.setPrice(orderDetaildt.getPrice());
            orderDetail.setDiscountPrice(orderDetaildt.getDiscountPrice());
            orderDetails.add(orderDetail);
        }

        return orderDetailRepository.saveAll(orderDetails);
    }

    public List<OrderDetail> saveOrderDetails(Long orderId, List<OrderDetail> orderDetails) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        for (OrderDetail orderDetail : orderDetails) {
            orderDetail.setOrderID(order);
        }
        return orderDetailRepository.saveAll(orderDetails);
    }
}
