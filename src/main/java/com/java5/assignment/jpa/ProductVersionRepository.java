package com.java5.assignment.jpa;

import com.java5.assignment.entities.ProductVersion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductVersionRepository extends JpaRepository<ProductVersion, Long> {
}