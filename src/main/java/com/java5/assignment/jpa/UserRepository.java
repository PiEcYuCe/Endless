package com.java5.assignment.jpa;

import com.java5.assignment.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByEmail(String email);

    @Query("SELECT u FROM User u WHERE (u.username = :userKey OR u.email = :userKey OR u.phone = :userKey) AND u.status = true")
    User findByUserKey(@Param("userKey") String userKey);


}