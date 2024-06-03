package com.java5.assignment.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
@Entity(name = Rating.ENTITY_NAME)
@Table(name = Rating.TABLE_NAME)
public class Rating {
    public static final String ENTITY_NAME = "RatingModel";
    public static final String TABLE_NAME = "Ratings";
    public static final String COLUMN_ID_NAME = "RatingID";
    public static final String COLUMN_PRODUCTID_NAME = "ProductID";
    public static final String COLUMN_USERID_NAME = "UserID";
    public static final String COLUMN_RATING_NAME = "RatingModel";
    public static final String COLUMN_COMMENT_NAME = "Comment";


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = COLUMN_ID_NAME, nullable = false)
    private Long id;

    @NotNull
    @Column(name = COLUMN_PRODUCTID_NAME, nullable = false)
    private Long productID;

    @NotNull
    @Column(name = COLUMN_USERID_NAME, nullable = false)
    private Long userID;

    @NotNull
    @Column(name = COLUMN_RATING_NAME, nullable = false)
    private Integer rating;

    @Nationalized
    @Lob
    @Column(name = COLUMN_COMMENT_NAME)
    private String comment;

    @OneToMany(mappedBy = "ratingID")
    private Set<RatingPicture> ratingPictures = new LinkedHashSet<>();

}