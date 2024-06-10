package com.java5.assignment.model.Order;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderDetailData {
    private long productVersionID;
    private int quantity;
    private BigDecimal price;
    private BigDecimal discountPrice;
}
