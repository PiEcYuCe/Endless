package com.java5.assignment.jpa;

import com.java5.assignment.entities.Product;
import com.java5.assignment.entities.ProductVersion;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductVersionRepository extends JpaRepository<ProductVersion, Long> {
    @Query(value = "SELECT pv " +
            "FROM ProductVersion pv " +
            "JOIN pv.orderDetails od " +
            "JOIN od.orderID o " +
            "JOIN pv.productID p " +
            "WHERE p.categoryID.id = :categoryId " +
            "GROUP BY pv.id, pv.versionName, pv.purchasePrice, pv.price, pv.quantity, pv.status, pv.image, p " + // Thay p.id bằng p
            "ORDER BY SUM(od.quantity) DESC")
    List<ProductVersion> findByCategoryId(@Param("categoryId") Long categoryId, Pageable pageable);
}

