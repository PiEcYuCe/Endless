package com.java5.assignment.jpa;

import com.java5.assignment.entities.UserVoucher;
import com.java5.assignment.entities.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface UserVoucherRepository extends JpaRepository<UserVoucher, Long> {
    @Query(value = "select uv from UserVoucher uv where uv.userID.id = :uid")
    List<UserVoucher> findByUserID(@Param("uid")long uid);

    @Query(value = "SELECT uv.voucherID FROM UserVoucher uv WHERE uv.voucherID.startDate < CURRENT_TIMESTAMP AND uv.voucherID.endDate > CURRENT_TIMESTAMP AND uv.voucherID.biggestDiscount <= :amount")
    List<Voucher> findByAmount(@Param("amount") BigDecimal amount);

    @Query(value = "select distinct uv.voucherID from UserVoucher uv")
    List<UserVoucher> findAll();

}