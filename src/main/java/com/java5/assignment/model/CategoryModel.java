package com.java5.assignment.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryModel {
    private int categoryID;
    @NotBlank(message = "Please enter name")
    private String name;
    @NotBlank(message = "Please enter description")
    private String description;
}
