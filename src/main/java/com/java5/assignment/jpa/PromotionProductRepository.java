package com.java5.assignment.jpa;

import com.java5.assignment.entities.PromotionProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionProductRepository extends JpaRepository<PromotionProduct, Long> {
}