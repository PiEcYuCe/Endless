package com.java5.assignment.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RatingModel {
    private int ratingID;
    private int productID;
    private int userID;
    private int rating;
    private String comment;
}
