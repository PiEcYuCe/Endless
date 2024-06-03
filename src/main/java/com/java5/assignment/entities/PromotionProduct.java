package com.java5.assignment.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = PromotionProduct.ENTITY_NAME)
@Table(name = PromotionProduct.TABLE_NAME)
public class PromotionProduct {
    public static final String ENTITY_NAME = "Promotion_Product";
    public static final String TABLE_NAME = "PromotionProducts";
    public static final String COLUMN_ID_NAME = "PromotionProductID";
    public static final String COLUMN_PROMOTIONDETAILID_NAME = "PromotionDetailID";
    public static final String COLUMN_PRODUCTVERSIONID_NAME = "ProductVersionID";
    public static final String COLUMN_STATUS_NAME = "Status";


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = COLUMN_ID_NAME, nullable = false)
    private Long id;

    @NotNull
    @Column(name = COLUMN_PROMOTIONDETAILID_NAME, nullable = false)
    private Long promotionDetailID;

    @NotNull
    @Column(name = COLUMN_PRODUCTVERSIONID_NAME, nullable = false)
    private Long productVersionID;

    @Size(max = 50)
    @NotNull
    @Nationalized
    @Column(name = COLUMN_STATUS_NAME, nullable = false, length = 50)
    private String status;

}