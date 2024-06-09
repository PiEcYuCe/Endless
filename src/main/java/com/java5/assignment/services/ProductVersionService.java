package com.java5.assignment.services;

import com.java5.assignment.dto.ProductInfoDTO;
import com.java5.assignment.entities.*;
import com.java5.assignment.jpa.ProductVersionRepository;
import com.java5.assignment.jpa.PromotionProductRepository;
import com.java5.assignment.jpa.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class ProductVersionService {
    @Autowired
    private ProductVersionRepository productVersionRepository;

    @Autowired
    private PromotionProductRepository promotionProductRepository;

    @Autowired
    private RatingRepository ratingRepository;

    public List<ProductInfoDTO> getProductInfoByCategory(Category category, Pageable pageable) {
        List<ProductVersion> productVersions = productVersionRepository.findByCategoryId(category.getId(), pageable);

        List<ProductInfoDTO> productInfoList = new ArrayList<>();
        for (ProductVersion productVersion : productVersions) {
            ProductInfoDTO productInfo = new ProductInfoDTO();
            productInfo.setId(productVersion.getId());
            productInfo.setVersionName(productVersion.getVersionName());
            productInfo.setPrice(productVersion.getPrice());

            BigDecimal discountedPrice = calculateDiscountedPrice(productVersion);
            productInfo.setDiscountedPrice(discountedPrice);
            productInfo.setImage(productVersion.getImage());

            double averageRating = calculateAverageRating(productVersion);
            productInfo.setAverageRating(averageRating);

            int discountPercentage = calculateDiscountPercentage(productVersion.getPrice(), discountedPrice);
            productInfo.setDiscountPercentage(discountPercentage);

            productInfoList.add(productInfo);
        }
        return productInfoList;
    }

    public List<ProductInfoDTO> getAllProductActive() {
        List<ProductVersion> productVersions = productVersionRepository.findByStatus(true);

        List<ProductInfoDTO> productInfoList = new ArrayList<>();
        for (ProductVersion productVersion : productVersions) {
            ProductInfoDTO productInfo = new ProductInfoDTO();
            productInfo.setId(productVersion.getId());
            productInfo.setVersionName(productVersion.getVersionName());
            productInfo.setPrice(productVersion.getPrice());

            BigDecimal discountedPrice = calculateDiscountedPrice(productVersion);
            productInfo.setDiscountedPrice(discountedPrice);
            productInfo.setImage(productVersion.getImage());

            double averageRating = calculateAverageRating(productVersion);
            productInfo.setAverageRating(averageRating);

            productInfoList.add(productInfo);
        }
        return productInfoList;
    }

    public List<ProductInfoDTO> getAllProductInfo() {
        List<ProductVersion> productVersions = productVersionRepository.findAll();

        List<ProductInfoDTO> productInfoList = new ArrayList<>();
        for (ProductVersion productVersion : productVersions) {
            ProductInfoDTO productInfo = new ProductInfoDTO();
            productInfo.setId(productVersion.getId());
            productInfo.setVersionName(productVersion.getVersionName());
            productInfo.setPrice(productVersion.getPrice());

            BigDecimal discountedPrice = calculateDiscountedPrice(productVersion);
            productInfo.setDiscountedPrice(discountedPrice);
            productInfo.setImage(productVersion.getImage());

            double averageRating = calculateAverageRating(productVersion);
            productInfo.setAverageRating(averageRating);

            productInfoList.add(productInfo);
        }
        return productInfoList;
    }


    private int calculateDiscountPercentage(BigDecimal originalPrice, BigDecimal discountedPrice) {
        if (originalPrice == null || discountedPrice == null || originalPrice.compareTo(BigDecimal.ZERO) == 0) {
            return 0;
        }
        return originalPrice.subtract(discountedPrice)
                .divide(originalPrice, 2, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100))
                .intValue();
    }

    public BigDecimal calculateDiscountedPrice(ProductVersion productVersion) {
        Set<PromotionProduct> promotionProducts = productVersion.getPromotionProducts();

        if(promotionProducts.isEmpty()){
            return BigDecimal.ZERO;
        }

        BigDecimal discountedPrice = productVersion.getPrice();
        for (PromotionProduct promotionProduct : promotionProducts) {
            PromotionDetail promotionDetail = promotionProduct.getPromotionDetailID();
            BigDecimal discountPercentage = BigDecimal.valueOf(promotionDetail.getDiscountLevel()).divide(BigDecimal.valueOf(100));
            BigDecimal discountAmount = productVersion.getPrice().multiply(discountPercentage);
            discountedPrice = discountedPrice.subtract(discountAmount);
        }

        return discountedPrice;
    }

    public double calculateAverageRating(ProductVersion productVersion) {
        Set<Rating> ratings = productVersion.getRatings();

        if (ratings.isEmpty()) {
            return 0;
        }

        int totalRating = 0;
        for (Rating rating : ratings) {
            totalRating += rating.getRatingValue();
        }

        double averageRating = (double) totalRating / ratings.size();
        return averageRating;
    }

}
