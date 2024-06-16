package com.java5.assignment.jpa;

import com.java5.assignment.dto.AttributeDto;
import com.java5.assignment.entities.VersionAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface VersionAttributeRepository extends JpaRepository<VersionAttribute, Long> {


    List<VersionAttribute> findAllByOrderByIdDesc();



    @Query("SELECT v.attributeValueID.attributeID.attributeName, v.attributeValueID.value " +
            "FROM VersionAttribute v WHERE v.productVersionID.id = :id")
    List<Object[]> findByProductVersionID(@Param("id") Long id);
}