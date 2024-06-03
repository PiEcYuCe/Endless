package com.java5.assignment.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = Promotion.ENTITY_NAME)
@Table(name = Promotion.TABLE_NAME)
public class Promotion {
    public static final String ENTITY_NAME = "PromotionModel";
    public static final String TABLE_NAME = "Promotions";
    public static final String COLUMN_ID_NAME = "PromotionID";
    public static final String COLUMN_NAME_NAME = "Name";
    public static final String COLUMN_DESCRIPTION_NAME = "Description";
    public static final String COLUMN_STARTDATE_NAME = "StartDate";
    public static final String COLUMN_ENDDATE_NAME = "EndDate";
    public static final String COLUMN_STATUS_NAME = "Status";
    public static final String COLUMN_POSTER_NAME = "Poster";


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

    @NotNull
    @Column(name = COLUMN_STARTDATE_NAME, nullable = false)
    private LocalDate startDate;

    @NotNull
    @Column(name = COLUMN_ENDDATE_NAME, nullable = false)
    private LocalDate endDate;

    @NotNull
    @Column(name = COLUMN_STATUS_NAME, nullable = false)
    private Boolean status = false;

    @Size(max = 255)
    @Nationalized
    @Column(name = COLUMN_POSTER_NAME)
    private String poster;

    @OneToMany(mappedBy = "promotionID")
    private Set<PromotionDetail> promotionDetails = new LinkedHashSet<>();

}