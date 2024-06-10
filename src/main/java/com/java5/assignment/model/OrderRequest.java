package com.java5.assignment.model;

import com.java5.assignment.entities.Order;
import com.java5.assignment.entities.OrderDetail;
import com.java5.assignment.model.Order.OrderData;
import com.java5.assignment.model.Order.OrderDetailData;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderRequest {
    private OrderData orderData;
    private List<OrderDetailData> orderDetailData;
}
