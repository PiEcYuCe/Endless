package com.java5.assignment.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Table(name = "PurchaseOrders")
public class PurchaseOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PurchaseOrderID", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "PurchaseDate", nullable = false)
    private LocalDate purchaseDate;

    @Size(max = 50)
    @NotNull
    @Nationalized
    @Column(name = "PurchaseOrderStatus", nullable = false, length = 50)
    private String purchaseOrderStatus;

    @NotNull
    @Column(name = "TotalMoney", nullable = false, precision = 18, scale = 2)
    private BigDecimal totalMoney;

    @OneToMany(mappedBy = "purchaseOrderID")
    private Set<PurchaseOrderDetail> purchaseOrderDetails = new LinkedHashSet<>();

}