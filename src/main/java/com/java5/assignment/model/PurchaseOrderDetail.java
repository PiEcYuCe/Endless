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
public class PurchaseOrderDetail {
    private int purchaseOrderDetailID;
    private int purchaseOrderID;
    private int productVersionID;
    private int quantity;
    private BigDecimal price;
}
