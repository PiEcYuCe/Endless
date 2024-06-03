package com.java5.assignment.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = Order.ENTITY_NAME)
@Table(name = Order.TABLE_NAME)
public class Order {
    public static final String ENTITY_NAME = "OrderModel";
    public static final String TABLE_NAME = "Orders";
    public static final String COLUMN_ID_NAME = "OrderID";
    public static final String COLUMN_USERID_NAME = "UserID";
    public static final String COLUMN_VOUCHERID_NAME = "VoucherID";
    public static final String COLUMN_ORDERDATE_NAME = "OrderDate";
    public static final String COLUMN_TOTALMONEY_NAME = "TotalMoney";
    public static final String COLUMN_ORDERSTATUS_NAME = "OrderStatus";


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = COLUMN_ID_NAME, nullable = false)
    private Long id;

    @NotNull
    @Column(name = COLUMN_USERID_NAME, nullable = false)
    private Long userID;

    @Column(name = COLUMN_VOUCHERID_NAME)
    private Long voucherID;

    @NotNull
    @Column(name = COLUMN_ORDERDATE_NAME, nullable = false)
    private LocalDate orderDate;

    @NotNull
    @Column(name = COLUMN_TOTALMONEY_NAME, nullable = false, precision = 18, scale = 2)
    private BigDecimal totalMoney;

    @Size(max = 50)
    @NotNull
    @Nationalized
    @Column(name = COLUMN_ORDERSTATUS_NAME, nullable = false, length = 50)
    private String orderStatus;

    @OneToMany(mappedBy = "orderID")
    private Set<OrderDetail> orderDetails = new LinkedHashSet<>();

}