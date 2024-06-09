package com.java5.assignment.jpa;

import com.java5.assignment.entities.PromotionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PromotionDetailRepository extends JpaRepository<PromotionDetail, Long> {
    @Query("SELECT pd.discountLevel FROM PromotionDetail pd WHERE pd.id = :promotionDetailId")
    Integer findDiscountLevelById(@Param("promotionDetailId") Long promotionDetailId);
}