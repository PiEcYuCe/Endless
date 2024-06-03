package com.java5.assignment.jpa;

import com.java5.assignment.entities.UserVoucher;
import com.java5.assignment.entities.UserVoucherId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserVoucherRepository extends JpaRepository<UserVoucher, UserVoucherId> {
}