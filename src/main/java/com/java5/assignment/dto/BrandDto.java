package com.java5.assignment.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.java5.assignment.entities.Brand}
 */
@Value
public class BrandDto implements Serializable {
    Long id;
    @NotNull
    @Size(max = 255)
    String name;
}