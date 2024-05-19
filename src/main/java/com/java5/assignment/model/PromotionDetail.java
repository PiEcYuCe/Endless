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
public class PromotionDetail {
    private int promotionDetailID;
    private int promotionID;
    private BigDecimal minimumBills;
    private BigDecimal decreaseTheLeast;
    private BigDecimal decreasedTheMost;
    private int discountLevel;
    private String formOfDiscount;
}
