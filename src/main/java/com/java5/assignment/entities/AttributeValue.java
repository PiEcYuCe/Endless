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
@Entity(name = AttributeValue.ENTITY_NAME)
@Table(name = AttributeValue.TABLE_NAME)
public class AttributeValue {
    public static final String ENTITY_NAME = "Attribute_Value";
    public static final String TABLE_NAME = "AttributeValues";
    public static final String COLUMN_ID_NAME = "AttributeValueID";
    public static final String COLUMN_ATTRIBUTEID_NAME = "AttributeID";
    public static final String COLUMN_VALUE_NAME = "\"Value\"";


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = COLUMN_ID_NAME, nullable = false)
    private Long id;

    @NotNull
    @Column(name = COLUMN_ATTRIBUTEID_NAME, nullable = false)
    private Long attributeID;

    @Size(max = 255)
    @NotNull
    @Nationalized
    @Column(name = COLUMN_VALUE_NAME, nullable = false)
    private String value;

    @OneToMany(mappedBy = "attributeValueID")
    private Set<VersionAttribute> versionAttributes = new LinkedHashSet<>();

}