package com.java5.assignment.model.purchase;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class PurchaseData {
    @NotNull
    LocalDate purchaseDate;
    @NotNull
    String PurchaseStatus = "Active";
    @NotNull
    BigDecimal purchaseTotal;
}