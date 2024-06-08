package com.java5.assignment.jpa;

import com.java5.assignment.entities.Category;
import com.java5.assignment.entities.Product;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryID(Category categoryID, Pageable pageable);
}