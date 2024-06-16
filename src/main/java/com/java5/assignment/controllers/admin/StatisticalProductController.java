package com.java5.assignment.controllers.admin;

import com.java5.assignment.dto.ProductVersionStatisticalDTO;
import com.java5.assignment.jpa.ProductVersionRepository;
import com.java5.assignment.utils.Page;
import com.java5.assignment.utils.PageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class StatisticalProductController {

    @Autowired
    ProductVersionRepository productVersionRepository;

    @ModelAttribute("page")
    public Page page() {
        return Page.route.get(PageType.ADMIN_STATISTICAL_PRODUCT);
    }

    @ModelAttribute("brands")
    public List<String> brands() {
        return productVersionRepository.findDistinctBrands();
    }

    @ModelAttribute("categories")
    public List<String> categories() {
        return productVersionRepository.findDistinctCategories();
    }

    @ModelAttribute("statisticalProducts")
    public List<ProductVersionStatisticalDTO> statisticalProducts(
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String category,
            @RequestParam(required = false, defaultValue = "default") String sort) {

        List<Object[]> results = productVersionRepository.findProductVersionsSummary(brand, category, sort);
        List<ProductVersionStatisticalDTO> dtoList = new ArrayList<>();

        for (Object[] result : results) {
            ProductVersionStatisticalDTO dto = new ProductVersionStatisticalDTO();
            dto.setId(((Number) result[0]).longValue());
            dto.setVersionName((String) result[1]);
            dto.setProductName((String) result[2]);
            dto.setBrand((String) result[3]);
            dto.setCategory((String) result[4]);
            dto.setUnitsSold(((Number) result[5]).longValue());
            dto.setInventory(((Number) result[6]).longValue());
            dto.setCostPrice((BigDecimal) result[7]);
            dto.setSellingPrice((BigDecimal) result[8]);
            dto.setRevenue((BigDecimal) result[9]);
            dtoList.add(dto);
        }

        return dtoList;
    }


    @GetMapping("/statistical-product")
    public String get() {
        return "admin/layout";
    }

}
