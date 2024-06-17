package com.java5.assignment.services;

import com.java5.assignment.dto.*;
import com.java5.assignment.entities.*;
import com.java5.assignment.jpa.PromotionDetailRepository;
import com.java5.assignment.jpa.PromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PromotionService {
    @Autowired
    private PromotionRepository promotionRepository;

    public List<PromotionDto> getPromotionsDtoActive() {
        List<Promotion> promotions = promotionRepository.findAllActive();
        List<PromotionDto> promotionDtos = new ArrayList<>();
        for (Promotion promotion : promotions) {
            Set<PromotionDetailDto> promotionDetailDtos = new HashSet<>();
            Set<PromotionDetail> promotionDetails = promotion.getPromotionDetails();
            for(PromotionDetail promotionDetail : promotionDetails) {
                Set<PromotionProduct> promotionProducts = promotionDetail.getPromotionProducts();
                Set<PromotionProductDto> promotionProductDtos = new HashSet<>();
                for(PromotionProduct promotionProduct : promotionProducts) {
                    ProductVersion productVersion = promotionProduct.getProductVersionID();
                    Product prod = productVersion.getProductID();
                    Brand brand = prod.getBrandID();
                    Category category = prod.getCategoryID();
                    BrandDto brandDto = new BrandDto(brand.getId(), brand.getName());
                    CategoryDto categoryDto = new CategoryDto(category.getId(), category.getName());
                    ProductDto1 productDto1 = new ProductDto1(prod.getId(), categoryDto, brandDto, prod.getName(), prod.getDescription(), prod.getStatus());
                    ProductVersionDto1 productVersionDto1 = new ProductVersionDto1(productVersion.getId(), productDto1, productVersion.getVersionName(),
                            productVersion.getPurchasePrice(), productVersion.getPrice(), productVersion.getQuantity(), productVersion.getStatus(), productVersion.getImage());
                    promotionProductDtos.add(new PromotionProductDto(promotionProduct.getId(), productVersionDto1));
                }
                promotionDetailDtos.add(new PromotionDetailDto(promotionDetail.getId(), promotionDetail.getDiscountLevel(), promotionProductDtos));
            }
            promotionDtos.add(new PromotionDto(promotion.getId(), promotion.getName(), promotion.getStartDate(), promotion.getEndDate(), promotion.getStatus(), promotionDetailDtos));
        }
        return promotionDtos;
    }


}
