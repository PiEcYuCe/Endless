package com.java5.assignment.services;

import com.java5.assignment.entities.PromotionDetail;
import com.java5.assignment.jpa.PromotionDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromotionDetailService {
    @Autowired
    private PromotionDetailRepository promotionDetailRepository;

}
