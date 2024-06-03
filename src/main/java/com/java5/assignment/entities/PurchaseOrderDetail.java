package com.java5.assignment.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = PurchaseOrderDetail.ENTITY_NAME)
@Table(name = PurchaseOrderDetail.TABLE_NAME)
public class PurchaseOrderDetail {
    public static final String ENTITY_NAME = "Purchase_Order_Detail";
    public static final String TABLE_NAME = "PurchaseOrderDetails";
    public static final String COLUMN_ID_NAME = "PurchaseOrderDetailID";
    public static final String COLUMN_PURCHASEORDERID_NAME = "PurchaseOrderID";
    public static final String COLUMN_PRODUCTVERSIONID_NAME = "ProductVersionID";
    public static final String COLUMN_QUANTITY_NAME = "Quantity";
    public static final String COLUMN_PRICE_NAME = "Price";


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = COLUMN_ID_NAME, nullable = false)
    private Long id;

    @NotNull
    @Column(name = COLUMN_PURCHASEORDERID_NAME, nullable = false)
    private Long purchaseOrderID;

    @NotNull
    @Column(name = COLUMN_PRODUCTVERSIONID_NAME, nullable = false)
    private Long productVersionID;

    @NotNull
    @Column(name = COLUMN_QUANTITY_NAME, nullable = false)
    private Integer quantity;

    @NotNull
    @Column(name = COLUMN_PRICE_NAME, nullable = false, precision = 18, scale = 2)
    private BigDecimal price;

}