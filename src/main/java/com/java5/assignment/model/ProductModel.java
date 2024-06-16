package com.java5.assignment.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductModel {
    @NotBlank(message = "Please enter product name")
    private String productName;


    private String productDescription;

    @Min(value = 0, message = "Please select Category")
    private long productCategory;

    @Min(value = 0, message = "Please select Brand")
    private long productBrand;

    private boolean productStatus;
}
