package com.java5.assignment.services;

import com.java5.assignment.dto.*;
import com.java5.assignment.entities.Order;
import com.java5.assignment.entities.OrderDetail;
import com.java5.assignment.entities.User;
import com.java5.assignment.entities.Voucher;
import com.java5.assignment.jpa.OrderRepository;
import com.java5.assignment.jpa.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;

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

    public List<OrderDto> getAllOrdersDto() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        List<Order> orders = orderRepository.findAll(sort);
        List<OrderDto> orderDtos = new ArrayList<>();
        for (Order order : orders) {
            User user = order.getUserID();
            UserDto1 userDto1 = new UserDto1(user.getId(), user.getUsername(), user.getFullname(), user.getPhone(), user.getEmail(), user.getAddress());

            Voucher voucher = order.getVoucherID();
            VoucherDto1 voucherDto1 = voucher !=null ? new VoucherDto1(voucher.getId(), voucher.getVoucherCode(), voucher.getLeastBill(), voucher.getLeastDiscount(), voucher.getBiggestDiscount(), voucher.getDiscountLevel(), voucher.getDiscountForm()) : null;

            Set<OrderDetailDto> orderDetails = new HashSet<>();
            for(OrderDetail orderDetail : order.getOrderDetails()) {
                ProductVersionDto prodDto = new ProductVersionDto(orderDetail.getProductVersionID().getId(), orderDetail.getProductVersionID().getVersionName(), orderDetail.getPrice(), orderDetail.getProductVersionID().getImage());
                OrderDetailDto odt = new OrderDetailDto(orderDetail.getId(), prodDto, orderDetail.getQuantity(), orderDetail.getPrice(), orderDetail.getDiscountPrice());
                orderDetails.add(odt);
            }
            orderDtos.add(new OrderDto(order.getId(), userDto1, voucherDto1, order.getOrderDate(), order.getTotalMoney(), order.getOrderStatus(), orderDetails));
        }
        return orderDtos;
    }


}
