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
@Entity(name = Attribute.ENTITY_NAME)
@Table(name = Attribute.TABLE_NAME)
public class Attribute {
    public static final String ENTITY_NAME = "Attribute";
    public static final String TABLE_NAME = "Attributes";
    public static final String COLUMN_ID_NAME = "AttributeID";
    public static final String COLUMN_ATTRIBUTENAME_NAME = "AttributeName";
    public static final String COLUMN_ATTRIBUTENOTE_NAME = "AttributeNote";


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = COLUMN_ID_NAME, nullable = false)
    private Long id;

    @Size(max = 255)
    @NotNull
    @Nationalized
    @Column(name = COLUMN_ATTRIBUTENAME_NAME, nullable = false)
    private String attributeName;

    @Size(max = 255)
    @Nationalized
    @Column(name = COLUMN_ATTRIBUTENOTE_NAME)
    private String attributeNote;

    @OneToMany(mappedBy = "attributeID")
    private Set<AttributeValue> attributeValues = new LinkedHashSet<>();

}