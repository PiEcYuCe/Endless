package com.java5.assignment.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

/**
 * DTO for {@link com.java5.assignment.entities.Order}
 */
@Value
public class OrderDto implements Serializable {
    Long id;
    @NotNull
    UserDto1 userID;
    VoucherDto1 voucherID;
    @NotNull
    LocalDate orderDate;
    @NotNull
    BigDecimal totalMoney;
    @NotNull
    @Size(max = 50)
    String orderStatus;
    Set<OrderDetailDto> orderDetails;
}