package com.java5.assignment.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.util.LinkedHashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = Product.ENTITY_NAME)
@Table(name = Product.TABLE_NAME)
public class Product {
    public static final String ENTITY_NAME = "ProductModel";
    public static final String TABLE_NAME = "Products";
    public static final String COLUMN_ID_NAME = "ProductID";
    public static final String COLUMN_CATEGORYID_NAME = "CategoryID";
    public static final String COLUMN_BRANDID_NAME = "BrandID";
    public static final String COLUMN_NAME_NAME = "Name";
    public static final String COLUMN_DESCRIPTION_NAME = "Description";
    public static final String COLUMN_STATUS_NAME = "Status";


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = COLUMN_ID_NAME, nullable = false)
    private Long id;

    @NotNull
    @Column(name = COLUMN_CATEGORYID_NAME, nullable = false)
    private Long categoryID;

    @NotNull
    @Column(name = COLUMN_BRANDID_NAME, nullable = false)
    private Long brandID;

    @Size(max = 255)
    @NotNull
    @Nationalized
    @Column(name = COLUMN_NAME_NAME, nullable = false)
    private String name;

    @Nationalized
    @Lob
    @Column(name = COLUMN_DESCRIPTION_NAME)
    private String description;

    @NotNull
    @Column(name = COLUMN_STATUS_NAME, nullable = false)
    private Boolean status = false;

    @OneToMany(mappedBy = "productID")
    private Set<ProductVersion> productVersions = new LinkedHashSet<>();

    @OneToMany(mappedBy = "productID")
    private Set<Rating> ratings = new LinkedHashSet<>();

}