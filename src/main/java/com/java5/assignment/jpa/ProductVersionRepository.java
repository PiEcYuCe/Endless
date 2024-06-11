package com.java5.assignment.jpa;

import com.java5.assignment.entities.AttributeValue;
import com.java5.assignment.entities.Product;
import com.java5.assignment.entities.ProductVersion;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductVersionRepository extends JpaRepository<ProductVersion, Long> {
    @Query(value = "SELECT pv " +
            "FROM ProductVersion pv " +
            "JOIN pv.orderDetails od " +
            "JOIN od.orderID o " +
            "JOIN pv.productID p " +
            "WHERE pv.status = true and p.categoryID.id = :categoryId " +
            "GROUP BY pv.id, pv.versionName, pv.purchasePrice, pv.price, pv.quantity, pv.status, pv.image, p " +
            "ORDER BY SUM(od.quantity) DESC")
    List<ProductVersion> findByCategoryId(@Param("categoryId") Long categoryId, Pageable pageable);

    ProductVersion findById(long id);

    List<ProductVersion> findByStatus(boolean status);

    @Query(value = "SELECT " +
            "    pv.ProductVersionID AS ID, " +
            "    pv.VersionName, " +
            "    p.Name AS ProductName, " +
            "    b.Name AS Brand, " +
            "    c.Name AS Category, " +
            "    ISNULL(SUM(od.Quantity), 0) AS UnitsSold, " +
            "    pv.Quantity AS Inventory, " +
            "    pv.PurchasePrice AS CostPrice, " +
            "    pv.Price AS SellingPrice, " +
            "    ISNULL(SUM(od.Price * od.Quantity), 0) AS Revenue " +
            "FROM " +
            "    ProductVersions pv " +
            "INNER JOIN Products p ON pv.ProductID = p.ProductID " +
            "INNER JOIN Brands b ON p.BrandID = b.BrandID " +
            "INNER JOIN Categories c ON p.CategoryID = c.CategoryID " +
            "LEFT JOIN OrderDetails od ON pv.ProductVersionID = od.ProductVersionID " +
            "GROUP BY " +
            "    pv.ProductVersionID, pv.VersionName, p.Name, b.Name, c.Name, pv.Quantity, pv.PurchasePrice, pv.Price;",
            nativeQuery = true)
    List<Object[]> findProductVersionsSummary();


    @Query("SELECT MIN(pv.price) FROM ProductVersion pv")
    BigDecimal findMinPrice();

    @Query("SELECT MAX(pv.price) FROM ProductVersion pv")
    BigDecimal findMaxPrice();


    @Query("SELECT pv FROM ProductVersion pv WHERE pv.id = :productID")
    List<ProductVersion> findByProductID(@Param("productID") Long productID);


