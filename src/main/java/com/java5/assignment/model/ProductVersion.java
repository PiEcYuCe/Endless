package com.java5.assignment.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductVersion {
    private int productVersionID;
    private int productID;
    private String versionName;
    private BigDecimal purchasePrice;
    private BigDecimal salePrice;
    private int quantity;
    private boolean status;
    private String image;
}
