package com.java5.assignment.jpa;

import com.java5.assignment.entities.Cart;
import com.java5.assignment.entities.User;
import org.hibernate.boot.model.source.spi.Sortable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByUserID(User user, Sort sort);

    @Query(value = "select c from Cart c where c.userID.id = :uid and c.productVersionID.id = :prodID")
    Cart findByUserIDAndProdVID(@Param("uid") long userID, @Param("prodID") Long prodID);

}