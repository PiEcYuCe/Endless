package com.java5.assignment.jpa;

import com.java5.assignment.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value= "select or from Order or where or.userID.id = :uid")
    public List<Order> findByUserID(@Param("uid") long id);
}