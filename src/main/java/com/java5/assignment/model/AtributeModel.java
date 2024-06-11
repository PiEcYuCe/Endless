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
public class AtributeModel {
    private int attributeID;

    @NotBlank(message = "Please enter Attribute Name")
    private String attributeName;

    private String attributeNote;


}
