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
@Table(name = "Attributes")
public class Attribute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AttributeID", nullable = false)
    private Long id;

    @Size(max = 255)
    @NotNull
    @Nationalized
    @Column(name = "AttributeName", nullable = false)
    private String attributeName;

    @Size(max = 255)
    @Nationalized
    @Column(name = "AttributeNote")
    private String attributeNote;

    @OneToMany(mappedBy = "attributeID")
    private Set<AttributeValue> attributeValues = new LinkedHashSet<>();

}