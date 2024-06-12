package com.java5.assignment.services;

import com.java5.assignment.dto.ProductInfoDTO;
import com.java5.assignment.entities.*;
import com.java5.assignment.jpa.ProductRepository;
import com.java5.assignment.jpa.ProductVersionRepository;
import com.java5.assignment.jpa.PromotionProductRepository;
import com.java5.assignment.jpa.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProductVersionService {
    @Autowired
    private ProductVersionRepository productVersionRepository;

    @Autowired
    private PromotionProductRepository promotionProductRepository;

    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private ProductRepository productRepository;

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
            productInfo.setPurchasePrice(productVersion.getPurchasePrice());
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

            BigDecimal price = productVersion.getPrice();
            if (price != null && discountedPrice != null && price.compareTo(BigDecimal.ZERO) != 0) {
                BigDecimal discountPercentage = BigDecimal.ONE
                        .subtract(discountedPrice.divide(price, 2, RoundingMode.HALF_UP))
                        .multiply(BigDecimal.valueOf(100));
                productInfo.setDiscountPercentage(discountPercentage.intValue());
            } else {
                productInfo.setDiscountPercentage(0);
            }

            productInfoList.add(productInfo);
        }
        return productInfoList;
    }



    public List<ProductInfoDTO> getAllProductInfoPage(Pageable pageable) {
        List<ProductVersion> productVersions = productVersionRepository.findAll(pageable).getContent();

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

            BigDecimal price = productVersion.getPrice();
            if (price != null && discountedPrice != null && price.compareTo(BigDecimal.ZERO) != 0) {
                BigDecimal discountPercentage = BigDecimal.ONE
                        .subtract(discountedPrice.divide(price, 2, RoundingMode.HALF_UP))
                        .multiply(BigDecimal.valueOf(100));
                productInfo.setDiscountPercentage(discountPercentage.intValue());
            } else {
                productInfo.setDiscountPercentage(0);
            }

            productInfoList.add(productInfo);
        }
        return productInfoList;
    }


    public ProductInfoDTO getProductInfoById(Long productId) {
        Optional<ProductVersion> optionalProductVersion = productVersionRepository.findById(productId);
        if (optionalProductVersion.isPresent()) {
            ProductVersion productVersion = optionalProductVersion.get();
            ProductInfoDTO productInfoDTO = new ProductInfoDTO();
            productInfoDTO.setId(productVersion.getId());
            productInfoDTO.setVersionName(productVersion.getVersionName());
            productInfoDTO.setPrice(productVersion.getPrice());
            productInfoDTO.setDiscountedPrice(calculateDiscountedPrice(productVersion));
            productInfoDTO.setImage(productVersion.getImage());
            productInfoDTO.setAverageRating(calculateAverageRating(productVersion));
            productInfoDTO.setPurchasePrice(productVersion.getPurchasePrice());
            int discountPercentage = calculateDiscountPercentage(productVersion.getPrice(), productInfoDTO.getDiscountedPrice());
            productInfoDTO.setDiscountPercentage(discountPercentage);

            return productInfoDTO;
        }
        return null;
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

    public ProductInfoDTO convertToProductInfoDTO(Product product) {
        ProductInfoDTO productInfoDTO = new ProductInfoDTO();
        productInfoDTO.setId(product.getId());
        productInfoDTO.setVersionName(product.getName());
        return productInfoDTO;
    }

    public Page<ProductInfoDTO> getAllProducts(Pageable pageable) {
        Page<Product> productPage = productRepository.findAll(pageable);
        return productPage.map(this::convertToProductInfoDTO);
    }

}
