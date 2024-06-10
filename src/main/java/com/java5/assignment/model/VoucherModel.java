package com.java5.assignment.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VoucherModel {
    private int voucherID;

    @NotBlank(message = "Please enter voucher code")
    private String voucherCode;

    @NotNull(message = "Please enter least bill amount")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than 0")
    private Double leastBill;

    @NotNull(message = "Please enter least discount")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than 0")
    private Double leastDiscount;

    @NotNull(message = "Please enter biggest discount")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than 0")
    private Double biggestDiscount;

    @NotNull(message = "Please enter discount level")
    @Min(value = 0, message = "Discount level must be non-negative")
    private Integer discountLevel;

    @NotBlank(message = "Please enter discount form")
    private String discountForm;

    @NotNull(message = "Please enter start date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @NotNull(message = "Please enter end date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;


}
