package com.java5.assignment.services;

import com.java5.assignment.dto.CartInfo;
import com.java5.assignment.entities.*;
import com.java5.assignment.jpa.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductVersionService productVersionService;

    public List<CartInfo> getAllByUser(User user, Sort sort) {
        List<Cart> list = cartRepository.findByUserID(user, sort);
        List<CartInfo> cartInfoList = new ArrayList<>();
        for (Cart cart : list) {
            CartInfo cartInfo = new CartInfo();
            cartInfo.setId(cart.getId());
            cartInfo.setQuantity(cart.getQuantity());
            cartInfo.setPrice(cart.getProductVersionID().getPrice());
            cartInfo.setProductVersionID(cart.getProductVersionID().getId());
            cartInfo.setProductVersionName(cart.getProductVersionID().getVersionName());
            cartInfo.setImage(cart.getProductVersionID().getImage());
            cartInfo.setDisCountPrice(calculateDiscountedPrice(cart.getProductVersionID())) ;
            cartInfoList.add(cartInfo);
        }
        return cartInfoList;
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
}
