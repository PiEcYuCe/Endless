package com.java5.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductVersionStatisticalDTO {
    private Long id;
    private String versionName;
    private String productName;
    private String brand;
    private String category;
    private Long unitsSold;
    private Long inventory;
    private BigDecimal costPrice;
    private BigDecimal sellingPrice;
    private BigDecimal revenue;
}
