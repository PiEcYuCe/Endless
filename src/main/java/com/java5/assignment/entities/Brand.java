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
@Entity
@Table(name = "Brands")
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BrandID", nullable = false)
    private Long id;

    @Size(max = 255)
    @NotNull
    @Nationalized
    @Column(name = "Name", nullable = false)
    private String name;

    @Nationalized
    @Lob
    @Column(name = "Logo")
    private String logo;

    @NotNull
    @Column(name = "Status", nullable = false)
    private Boolean status = false;

    @OneToMany(mappedBy = "brandID")
    private Set<Product> products = new LinkedHashSet<>();

}