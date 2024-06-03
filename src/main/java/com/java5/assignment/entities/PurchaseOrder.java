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
@Entity(name = PurchaseOrder.ENTITY_NAME)
@Table(name = PurchaseOrder.TABLE_NAME)
public class PurchaseOrder {
    public static final String ENTITY_NAME = "Purchase_Order";
    public static final String TABLE_NAME = "PurchaseOrders";
    public static final String COLUMN_ID_NAME = "PurchaseOrderID";
    public static final String COLUMN_PURCHASEDATE_NAME = "PurchaseDate";
    public static final String COLUMN_PURCHASEORDERSTATUS_NAME = "PurchaseOrderStatus";
    public static final String COLUMN_TOTALMONEY_NAME = "TotalMoney";


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = COLUMN_ID_NAME, nullable = false)
    private Long id;

    @NotNull
    @Column(name = COLUMN_PURCHASEDATE_NAME, nullable = false)
    private LocalDate purchaseDate;

    @Size(max = 50)
    @NotNull
    @Nationalized
    @Column(name = COLUMN_PURCHASEORDERSTATUS_NAME, nullable = false, length = 50)
    private String purchaseOrderStatus;

    @NotNull
    @Column(name = COLUMN_TOTALMONEY_NAME, nullable = false, precision = 18, scale = 2)
    private BigDecimal totalMoney;

    @OneToMany(mappedBy = "purchaseOrderID")
    private Set<PurchaseOrderDetail> purchaseOrderDetails = new LinkedHashSet<>();

}