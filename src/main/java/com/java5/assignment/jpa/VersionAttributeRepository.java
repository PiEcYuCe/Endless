package com.java5.assignment.jpa;

import com.java5.assignment.entities.VersionAttribute;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VersionAttributeRepository extends JpaRepository<VersionAttribute, Long> {
}