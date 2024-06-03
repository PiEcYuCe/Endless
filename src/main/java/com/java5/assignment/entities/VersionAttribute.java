package com.java5.assignment.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = VersionAttribute.ENTITY_NAME)
@Table(name = VersionAttribute.TABLE_NAME)
public class VersionAttribute {
    public static final String ENTITY_NAME = "Version_Attribute";
    public static final String TABLE_NAME = "VersionAttributes";
    public static final String COLUMN_ID_NAME = "VersionAttributeID";
    public static final String COLUMN_PRODUCTVERSIONID_NAME = "ProductVersionID";
    public static final String COLUMN_ATTRIBUTEVALUEID_NAME = "AttributeValueID";


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = COLUMN_ID_NAME, nullable = false)
    private Long id;

    @NotNull
    @Column(name = COLUMN_PRODUCTVERSIONID_NAME, nullable = false)
    private Long productVersionID;

    @NotNull
    @Column(name = COLUMN_ATTRIBUTEVALUEID_NAME, nullable = false)
    private Long attributeValueID;

}