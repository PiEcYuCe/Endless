package com.java5.assignment.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = PromotionDetail.ENTITY_NAME)
@Table(name = PromotionDetail.TABLE_NAME)
public class PromotionDetail {
    public static final String ENTITY_NAME = "Promotion_Detail";
    public static final String TABLE_NAME = "PromotionDetails";
    public static final String COLUMN_ID_NAME = "PromotionDetailID";
    public static final String COLUMN_PROMOTIONID_NAME = "PromotionID";
    public static final String COLUMN_DISCOUNTLEVEL_NAME = "DiscountLevel";


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = COLUMN_ID_NAME, nullable = false)
    private Long id;

    @NotNull
    @Column(name = COLUMN_PROMOTIONID_NAME, nullable = false)
    private Long promotionID;

    @NotNull
    @Column(name = COLUMN_DISCOUNTLEVEL_NAME, nullable = false)
    private Integer discountLevel;

    @OneToMany(mappedBy = "promotionDetailID")
    private Set<PromotionProduct> promotionProducts = new LinkedHashSet<>();

}