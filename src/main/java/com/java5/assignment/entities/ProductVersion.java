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
import java.util.LinkedHashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Table(name = "ProductVersions")
public class ProductVersion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProductVersionID", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ProductID", nullable = false)
    private Product productID;

    @Size(max = 255)
    @NotNull
    @Nationalized
    @Column(name = "VersionName", nullable = false)
    private String versionName;

    @NotNull
    @Column(name = "PurchasePrice", nullable = false, precision = 18, scale = 2)
    private BigDecimal purchasePrice;

    @NotNull
    @Column(name = "Price", nullable = false, precision = 18, scale = 2)
    private BigDecimal price;

    @NotNull
    @Column(name = "Quantity", nullable = false)
    private Integer quantity;

    @NotNull
    @Column(name = "Status", nullable = false)
    private Boolean status = false;

    @Nationalized
    @Lob
    @Column(name = "Image")
    private String image;

    @OneToMany(mappedBy = "productVersionID")
    private Set<Cart> carts = new LinkedHashSet<>();

    @OneToMany(mappedBy = "productVersionID")
    private Set<OrderDetail> orderDetails = new LinkedHashSet<>();

    @OneToMany(mappedBy = "productVersionID")
    private Set<PromotionProduct> promotionProducts = new LinkedHashSet<>();

    @OneToMany(mappedBy = "productVersionID")
    private Set<PurchaseOrderDetail> purchaseOrderDetails = new LinkedHashSet<>();

    @OneToMany(mappedBy = "productVersionID")
    private Set<VersionAttribute> versionAttributes = new LinkedHashSet<>();

}