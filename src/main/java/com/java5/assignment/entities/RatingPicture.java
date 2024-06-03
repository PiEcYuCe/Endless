package com.java5.assignment.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = RatingPicture.ENTITY_NAME)
@Table(name = RatingPicture.TABLE_NAME)
public class RatingPicture {
    public static final String ENTITY_NAME = "Rating_Picture";
    public static final String TABLE_NAME = "RatingPictures";
    public static final String COLUMN_ID_NAME = "PictureID";
    public static final String COLUMN_RATINGID_NAME = "RatingID";
    public static final String COLUMN_PICTURE_NAME = "Picture";


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = COLUMN_ID_NAME, nullable = false)
    private Long id;

    @NotNull
    @Column(name = COLUMN_RATINGID_NAME, nullable = false)
    private Long ratingID;

    @Nationalized
    @Lob
    @Column(name = COLUMN_PICTURE_NAME)
    private String picture;

}