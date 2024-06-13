package com.java5.assignment.model.client.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductPage {
    private int size = 9;
    private int page = 0;

    List<Long> cat = new ArrayList<>();
    List<Long> brand = new ArrayList<>();

    private BigDecimal minPrice;
    private BigDecimal maxPrice;



}
