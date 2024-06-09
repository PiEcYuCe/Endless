package com.java5.assignment.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductVersionModel {
    private int productVersionID;

    private long productID;
    @NotBlank(message = "Please enter version name")

    private String versionName;
    @NotNull(message = "Please enter purchase price")
    @DecimalMin("0")
    private BigDecimal purchasePrice;
    @DecimalMin("0")
    @NotNull(message = "Please enter version name")
    private BigDecimal salePrice;
    @NotNull(message = "Please enter quantity")
    private Integer quantity;
    private boolean status;
    private String image;
}
