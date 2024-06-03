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
@Table(name = "AttributeValues")
public class AttributeValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AttributeValueID", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "AttributeID", nullable = false)
    private Attribute attributeID;

    @Size(max = 255)
    @NotNull
    @Nationalized
    @Column(name = "\"Value\"", nullable = false)
    private String value;

    @OneToMany(mappedBy = "attributeValueID")
    private Set<VersionAttribute> versionAttributes = new LinkedHashSet<>();

}