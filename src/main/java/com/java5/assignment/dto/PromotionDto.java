package com.java5.assignment.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

/**
 * DTO for {@link com.java5.assignment.entities.Promotion}
 */
@Value
public class PromotionDto implements Serializable {
    Long id;
    @NotNull
    @Size(max = 255)
    String name;
    @NotNull
    LocalDate startDate;
    @NotNull
    LocalDate endDate;
    @NotNull
    Boolean status;
    Set<PromotionDetailDto> promotionDetails;
}