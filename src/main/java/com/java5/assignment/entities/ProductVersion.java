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
import java.util.LinkedHashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = ProductVersion.ENTITY_NAME)
@Table(name = ProductVersion.TABLE_NAME)
public class ProductVersion {
    public static final String ENTITY_NAME = "Product_Version";
    public static final String TABLE_NAME = "ProductVersions";
    public static final String COLUMN_ID_NAME = "ProductVersionID";
    public static final String COLUMN_PRODUCTID_NAME = "ProductID";
    public static final String COLUMN_VERSIONNAME_NAME = "VersionName";
    public static final String COLUMN_PURCHASEPRICE_NAME = "PurchasePrice";
    public static final String COLUMN_PRICE_NAME = "Price";
    public static final String COLUMN_QUANTITY_NAME = "Quantity";
    public static final String COLUMN_STATUS_NAME = "Status";
    public static final String COLUMN_IMAGE_NAME = "Image";


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = COLUMN_ID_NAME, nullable = false)
    private Long id;

    @NotNull
    @Column(name = COLUMN_PRODUCTID_NAME, nullable = false)
    private Long productID;

    @Size(max = 255)
    @NotNull
    @Nationalized
    @Column(name = COLUMN_VERSIONNAME_NAME, nullable = false)
    private String versionName;

    @NotNull
    @Column(name = COLUMN_PURCHASEPRICE_NAME, nullable = false, precision = 18, scale = 2)
    private BigDecimal purchasePrice;

    @NotNull
    @Column(name = COLUMN_PRICE_NAME, nullable = false, precision = 18, scale = 2)
    private BigDecimal price;

    @NotNull
    @Column(name = COLUMN_QUANTITY_NAME, nullable = false)
    private Integer quantity;

    @NotNull
    @Column(name = COLUMN_STATUS_NAME, nullable = false)
    private Boolean status = false;

    @Nationalized
    @Lob
    @Column(name = COLUMN_IMAGE_NAME)
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