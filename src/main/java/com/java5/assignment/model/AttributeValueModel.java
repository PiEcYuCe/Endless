package com.java5.assignment.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AttributeValueModel {
    private long attributeValueID;

    @Min(value = 0, message = "Please select Attribute Name")
    private long attributeID;

    @NotBlank(message = "Please enter Attribute value")
    private String value;
}
