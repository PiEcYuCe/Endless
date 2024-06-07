package com.java5.assignment.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BrandModel {
    private int brandID;
    @NotBlank(message = "Please enter the brand name")
    private String name;
    private MultipartFile logo;
    private boolean status;
}
