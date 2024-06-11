package com.java5.assignment.jpa;

import com.java5.assignment.entities.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;


public interface VoucherRepository extends JpaRepository<Voucher, Long> {

}