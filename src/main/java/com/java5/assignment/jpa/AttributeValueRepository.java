package com.java5.assignment.jpa;

import com.java5.assignment.entities.AttributeValue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttributeValueRepository extends JpaRepository<AttributeValue, Long> {
}