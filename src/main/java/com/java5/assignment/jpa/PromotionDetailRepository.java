package com.java5.assignment.jpa;

import com.java5.assignment.entities.PromotionDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionDetailRepository extends JpaRepository<PromotionDetail, Long> {
}