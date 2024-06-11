package com.java5.assignment.dto;

import com.java5.assignment.entities.ProductVersion;
import com.java5.assignment.entities.User;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CartInfo {
    private Long id;
    private long productVersionID;
    private String productVersionName;
    private String image;
    private int quantity;
    BigDecimal price;
    BigDecimal disCountPrice;
}
