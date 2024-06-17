package com.java5.assignment.jpa;

import com.java5.assignment.entities.PromotionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PromotionDetailRepository extends JpaRepository<PromotionDetail, Long> {

    @Query("SELECT pd FROM PromotionDetail pd WHERE pd.promotionID.id = :pid")
    List<PromotionDetail> findAllByPromotionID(@Param("pid") Long promotionDetailId);
}