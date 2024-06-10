package com.java5.assignment.model.purchase;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PurchaseDetailData {
    @NotNull
    long productVersionID;
    @NotNull
    int quantity;
    @NotNull
    BigDecimal price;
}
