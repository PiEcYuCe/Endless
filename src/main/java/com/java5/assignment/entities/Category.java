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
@Entity(name = Category.ENTITY_NAME)
@Table(name = Category.TABLE_NAME)
public class Category {
    public static final String ENTITY_NAME = "CategoryModel";
    public static final String TABLE_NAME = "Categories";
    public static final String COLUMN_ID_NAME = "CategoryID";
    public static final String COLUMN_NAME_NAME = "Name";
    public static final String COLUMN_DESCRIPTION_NAME = "Description";


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
    @Column(name = COLUMN_DESCRIPTION_NAME)
    private String description;

    @OneToMany(mappedBy = "categoryID")
    private Set<Product> products = new LinkedHashSet<>();

}