package com.java5.assignment.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link com.java5.assignment.entities.Voucher}
 */
@Value
public class VoucherDto1 implements Serializable {
    Long id;
    @NotNull
    @Size(max = 50)
    String voucherCode;
    @NotNull
    BigDecimal leastBill;
    @NotNull
    BigDecimal leastDiscount;
    @NotNull
    BigDecimal biggestDiscount;
    @NotNull
    Integer discountLevel;
    @NotNull
    @Size(max = 50)
    String discountForm;
}