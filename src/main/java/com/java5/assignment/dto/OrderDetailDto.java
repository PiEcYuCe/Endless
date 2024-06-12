package com.java5.assignment.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link com.java5.assignment.entities.OrderDetail}
 */
@Value
public class OrderDetailDto implements Serializable {
    Long id;
    @NotNull
    ProductVersionDto productVersionID;
    @NotNull
    Integer quantity;
    @NotNull
    BigDecimal price;
    @NotNull
    BigDecimal discountPrice;
}