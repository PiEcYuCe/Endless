package com.java5.assignment.jpa;

import com.java5.assignment.entities.Order;

import com.java5.assignment.entities.User;

import com.java5.assignment.entities.ProductVersion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value= "select or from Order or where or.userID.id = :uid order by or.id desc")
    public List<Order> findAllByUserId(@Param("uid") long id);

    List<Order> findAllByOrderStatus(String status);

    boolean existsByUserID(User user);

    int countByOrderStatus(String status);

    @Query("SELECT SUM(o.totalMoney) FROM Order o WHERE o.orderDate = CURRENT_DATE")
    BigDecimal getRevenueToday();

    @Query("SELECT SUM(o.totalMoney) FROM Order o WHERE o.orderDate >= :startOfWeek")
    BigDecimal getRevenueThisWeek(@Param("startOfWeek") LocalDate startOfWeek);

    @Query("SELECT SUM(o.totalMoney) FROM Order o WHERE o.orderDate >= :startOfMonth")
    BigDecimal getRevenueThisMonth(@Param("startOfMonth") LocalDate startOfMonth);

    @Query("SELECT SUM(o.totalMoney) FROM Order o WHERE o.orderDate >= :startOfYear")
    BigDecimal getRevenueThisYear(@Param("startOfYear") LocalDate startOfYear);

    @Query("select od.productVersionID from OrderDetail od where od.orderID.id   = :oid")
    List<ProductVersion> selectProductVersionList(@Param("oid") long oid);
}