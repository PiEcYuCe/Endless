package com.java5.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RevenueStatisticsDTO {
    private Long id;
    private Date time;
    private Long numberOfInvoices;
    private Long numberOfProductsSold;
    private BigDecimal totalRevenue;
}
