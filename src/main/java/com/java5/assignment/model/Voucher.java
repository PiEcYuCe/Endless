package com.java5.assignment.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Voucher {
    private int voucherID;
    private BigDecimal leastBill;
    private BigDecimal leastDiscount;
    private BigDecimal biggestDiscount;
    private int discountLevel;
    private String discountForm;
    private Date startDate;
    private Date endDate;
}
