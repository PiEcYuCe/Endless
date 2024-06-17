package com.java5.assignment.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link com.java5.assignment.entities.ProductVersion}
 */
@Value
public class ProductVersionDto1 implements Serializable {
    Long id;
    @NotNull
    ProductDto1 productID;
    @NotNull
    @Size(max = 255)
    String versionName;
    @NotNull
    BigDecimal purchasePrice;
    @NotNull
    BigDecimal price;
    @NotNull
    Integer quantity;
    @NotNull
    Boolean status;
    String image;
}