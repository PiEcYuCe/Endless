package com.java5.assignment.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderModel {
    private int orderID;
    private int userID;
    private int voucherID;
    private Date orderDate;
    private BigDecimal totalMoney;
    private String orderStatus;
}
