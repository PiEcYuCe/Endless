package com.java5.assignment.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.java5.assignment.entities.Product}
 */
@Value
public class ProductDto1 implements Serializable {
    Long id;
    @NotNull
    CategoryDto categoryID;
    @NotNull
    BrandDto brandID;
    @NotNull
    @Size(max = 255)
    String name;
    String description;
    @NotNull
    Boolean status;
}