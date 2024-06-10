package com.java5.assignment.model.admin.productVersion;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@Setter
@NoArgsConstructor

public class ProductVersionCreateModel {

    @NotNull(message = "Please enter product ID")
    private long productID;

    @NotBlank(message = "Please enter version name")
    private String versionName;


    @NotNull(message = "Please enter purchase price")
    @DecimalMin("0")
    private BigDecimal purchasePrice;

    @DecimalMin("0")
    @NotNull(message = "Please enter version name")
    private BigDecimal salePrice;

    @Min(value = 0, message = "Quantily cannot be smaller than 0")
    private Integer quantity;


    private boolean status;

    private MultipartFile image;
}
