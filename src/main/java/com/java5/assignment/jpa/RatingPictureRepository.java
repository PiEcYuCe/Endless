package com.java5.assignment.jpa;

import com.java5.assignment.entities.RatingPicture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingPictureRepository extends JpaRepository<RatingPicture, Long> {
}