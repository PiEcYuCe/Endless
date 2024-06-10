package com.java5.assignment.jpa;

import com.java5.assignment.entities.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    @Query(value = "SELECT " +
            "ROW_NUMBER() OVER (ORDER BY o.OrderDate) AS STT, " +
            "o.OrderDate AS Time, " +
            "COUNT(DISTINCT o.OrderID) AS NumberOfInvoices, " +
            "ISNULL(SUM(od.Quantity), 0) AS NumberOfProductsSold, " +
            "ISNULL(SUM(od.Quantity * od.Price), 0) AS TotalRevenue " +
            "FROM Orders o " +
            "LEFT JOIN OrderDetails od ON o.OrderID = od.OrderID " +
            "GROUP BY o.OrderDate " +
            "ORDER BY o.OrderDate;",
            nativeQuery = true)
    List<Object[]> findDailyOrderStatistics();

}