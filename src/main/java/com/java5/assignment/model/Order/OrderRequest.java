package com.java5.assignment.model.Order;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderRequest {
    private OrderData orderData;
    private List<OrderDetailData> orderDetailData;
}
