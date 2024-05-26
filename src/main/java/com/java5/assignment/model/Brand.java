package com.java5.assignment.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Brand {
    private int brandID;
    @NotBlank(message = "Please enter the brand name")
    private String name;
    private String logo;
    private boolean status;
}
