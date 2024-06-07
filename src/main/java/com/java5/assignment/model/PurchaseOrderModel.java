package com.java5.assignment.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderModel {
    private int purchaseOrderID;
    private Date purchaseDate;
    private String purchaseOrderStatus;
}
