package com.java5.assignment.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Promotion {
    private int promotionID;
    private String name;
    private String description;
    private Date startDate;
    private Date endDate;
    private boolean status;
    private String poster;
}
