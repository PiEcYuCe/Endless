package com.java5.assignment.jpa;

import com.java5.assignment.entities.Category;
import com.java5.assignment.entities.Product;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsByName(String name);

    List<Product> findByCategoryID(Category categoryID, Pageable pageable);

    @Query(value = """
            SELECT p
            FROM Product p
            JOIN p.productVersions pv
            JOIN pv.orderDetails od
            GROUP BY p.id, p.name, p.description, p.status, p.categoryID, p.brandID
            ORDER BY SUM(od.quantity) DESC
            """)
    List<Product> findBestSellers(Pageable pageable);

    @Query(value = """
            SELECT pv.productID
            FROM Rating r
            JOIN r.productVersionID pv
            GROUP BY pv.productID
            HAVING AVG(r.ratingValue) BETWEEN 4 AND 5
            ORDER BY COUNT(r.id) DESC
            """)
    List<Product> findTopRatedProducts(Pageable pageable);

    @Query(value = """
            SELECT od.productVersionID.productID
            FROM OrderDetail od
            JOIN od.orderID o
            WHERE o.id IN (
                SELECT o2.id
                FROM Order o2
                ORDER BY o2.orderDate DESC
                LIMIT 100
            )
            GROUP BY od.productVersionID.productID
            ORDER BY SUM(od.quantity) DESC
            """)
    List<Product> findTopSellingProductsInLast100Orders(Pageable pageable);
}