package com.java5.assignment.model.Order;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class OrderData {
    long userID;
    long voucherID = -1;
    LocalDate orderDate;
    BigDecimal totalMoney;
    String orderStatus;
}
