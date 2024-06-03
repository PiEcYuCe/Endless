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
@Entity(name = OrderDetail.ENTITY_NAME)
@Table(name = OrderDetail.TABLE_NAME)
public class OrderDetail {
    public static final String ENTITY_NAME = "Order_Detail";
    public static final String TABLE_NAME = "OrderDetails";
    public static final String COLUMN_ID_NAME = "OrderDetailID";
    public static final String COLUMN_ORDERID_NAME = "OrderID";
    public static final String COLUMN_PRODUCTVERSIONID_NAME = "ProductVersionID";
    public static final String COLUMN_QUANTITY_NAME = "Quantity";
    public static final String COLUMN_PRICE_NAME = "Price";
    public static final String COLUMN_DISCOUNTPRICE_NAME = "DiscountPrice";


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = COLUMN_ID_NAME, nullable = false)
    private Long id;

    @NotNull
    @Column(name = COLUMN_ORDERID_NAME, nullable = false)
    private Long orderID;

    @NotNull
    @Column(name = COLUMN_PRODUCTVERSIONID_NAME, nullable = false)
    private Long productVersionID;

    @NotNull
    @Column(name = COLUMN_QUANTITY_NAME, nullable = false)
    private Integer quantity;

    @NotNull
    @Column(name = COLUMN_PRICE_NAME, nullable = false, precision = 18, scale = 2)
    private BigDecimal price;

    @NotNull
    @Column(name = COLUMN_DISCOUNTPRICE_NAME, nullable = false, precision = 18, scale = 2)
    private BigDecimal discountPrice;

}