package com.java5.assignment.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VersionAttributeModel {
    private long versionAttributeID;
    @Min(value = 0, message = "Please select productVersionID")
    private long productVersionID;

    @Min(value = 0, message = "Please select attributeValueID")
    private long attributeValueID;
}
