package com.java5.assignment.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductInfoDTO {
    private Long id;
    private String versionName;
    private BigDecimal price;
    private BigDecimal discountedPrice;
    private BigDecimal purchasePrice;
    private int discountPercentage;
    private String image;
    private double averageRating;
}
