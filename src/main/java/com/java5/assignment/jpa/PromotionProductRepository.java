package com.java5.assignment.jpa;

import com.java5.assignment.entities.PromotionProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PromotionProductRepository extends JpaRepository<PromotionProduct, Long> {

}