package com.java5.assignment.jpa;

import com.java5.assignment.entities.AttributeValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AttributeValueRepository extends JpaRepository<AttributeValue, Long> {
    @Query(value = "select av from AttributeValue av where av.attributeID.id = :aid")
    List<AttributeValue> getAttributeValueByAttributeID(@Param("aid") Long aid);
}