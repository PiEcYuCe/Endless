package com.java5.assignment.services;

import com.java5.assignment.dto.VoucherDto;
import com.java5.assignment.entities.Voucher;
import com.java5.assignment.jpa.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {
    @Autowired
    private VoucherRepository voucherRepository;

    public VoucherDto entityToDto(Voucher voucher) {
        return new VoucherDto(voucher.getId(), voucher.getVoucherCode(), voucher.getLeastBill(), voucher.getLeastDiscount(), voucher.getBiggestDiscount(),
        voucher.getDiscountLevel(), voucher.getDiscountForm(), voucher.getStartDate(), voucher.getEndDate());
    }
}
