package com.java5.assignment.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link com.java5.assignment.entities.PromotionDetail}
 */
@Value
public class PromotionDetailDto implements Serializable {
    Long id;
    @NotNull
    Integer discountLevel;
    Set<PromotionProductDto> promotionProducts;
}