package com.java5.assignment.jpa;

import com.java5.assignment.entities.AttributeValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AttributeValueRepository extends JpaRepository<AttributeValue, Long> {
    @Query("SELECT av FROM AttributeValue av JOIN av.attributeID a WHERE a.attributeName = :attributeName ORDER BY av.value")
    List<AttributeValue> findValuesByAttributeName(@Param("attributeName") String attributeName);
}