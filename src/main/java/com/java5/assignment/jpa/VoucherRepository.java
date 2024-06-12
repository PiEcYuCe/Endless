package com.java5.assignment.jpa;

import com.java5.assignment.entities.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface VoucherRepository extends JpaRepository<Voucher, Long> {
    @Query(value = "select uv.voucherID from UserVoucher uv where uv.userID.id = :uid")
    List<Voucher> findAllByUserID(@Param("uid") Long id);

}