package com.java5.assignment.jpa;

import com.java5.assignment.entities.ProductVersion;
import com.java5.assignment.entities.Rating;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    @Query("SELECT AVG(r.ratingValue) FROM Rating r WHERE r.productVersionID.id = :productVersionId")
    Double findAverageRatingByProductVersionId(@Param("productVersionId") Long productVersionId);
}