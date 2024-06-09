package com.java5.assignment.services;

import com.java5.assignment.jpa.PromotionDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PromotionDetailService {
    @Autowired
    private PromotionDetailRepository promotionDetailRepository;

    public Integer getDiscountLevel(Long promotionDetailId) {
        return promotionDetailRepository.findDiscountLevelById(promotionDetailId);
    }
}
