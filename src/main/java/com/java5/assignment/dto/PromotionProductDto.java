package com.java5.assignment.dto;

import com.java5.assignment.entities.Brand;
import com.java5.assignment.entities.Category;
import com.java5.assignment.entities.ProductVersion;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.java5.assignment.entities.PromotionProduct}
 */
@Value
public class PromotionProductDto implements Serializable {
    Long id;
    private ProductVersionDto1 productVersionID;
}