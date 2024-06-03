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
@Entity(name = Brand.ENTITY_NAME)
@Table(name = Brand.TABLE_NAME)
public class Brand {
    public static final String ENTITY_NAME = "BrandModel";
    public static final String TABLE_NAME = "Brands";
    public static final String COLUMN_ID_NAME = "BrandID";
    public static final String COLUMN_NAME_NAME = "Name";
    public static final String COLUMN_LOGO_NAME = "Logo";
    public static final String COLUMN_STATUS_NAME = "Status";


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = COLUMN_ID_NAME, nullable = false)
    private Long id;

    @Size(max = 255)
    @NotNull
    @Nationalized
    @Column(name = COLUMN_NAME_NAME, nullable = false)
    private String name;

    @Nationalized
    @Lob
    @Column(name = COLUMN_LOGO_NAME)
    private String logo;

    @NotNull
    @Column(name = COLUMN_STATUS_NAME, nullable = false)
    private Boolean status = false;

    @OneToMany(mappedBy = "brandID")
    private Set<Product> products = new LinkedHashSet<>();

}