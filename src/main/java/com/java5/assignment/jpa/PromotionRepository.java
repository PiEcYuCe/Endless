package com.java5.assignment.jpa;

import com.java5.assignment.entities.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {
    @Query("select p from Promotion p where p.status = true")
    List<Promotion> findAllActive();
}