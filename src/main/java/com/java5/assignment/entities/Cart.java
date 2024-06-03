package com.java5.assignment.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = Cart.ENTITY_NAME)
@Table(name = Cart.TABLE_NAME)
public class Cart {
    public static final String ENTITY_NAME = "CartModel";
    public static final String TABLE_NAME = "Carts";
    public static final String COLUMN_ID_NAME = "CartID";
    public static final String COLUMN_USERID_NAME = "UserID";
    public static final String COLUMN_PRODUCTVERSIONID_NAME = "ProductVersionID";
    public static final String COLUMN_QUANTITY_NAME = "Quantity";


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = COLUMN_ID_NAME, nullable = false)
    private Long id;

    @NotNull
    @Column(name = COLUMN_USERID_NAME, nullable = false)
    private Long userID;

    @NotNull
    @Column(name = COLUMN_PRODUCTVERSIONID_NAME, nullable = false)
    private Long productVersionID;

    @NotNull
    @Column(name = COLUMN_QUANTITY_NAME, nullable = false)
    private Integer quantity;

}