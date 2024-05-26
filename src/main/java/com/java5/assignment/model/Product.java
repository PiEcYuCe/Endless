package com.java5.assignment.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @NotEmpty(message = "Product name cannot be empty")
    private String productName;

    @NotEmpty(message = "Product description cannot be empty")
    private String productDescription;

    @NotNull(message = "Please select a category")
    private Integer productCategory;

    @NotNull(message = "Please select a brand")
    private Integer productBrand;

    private boolean productStatus;
}
