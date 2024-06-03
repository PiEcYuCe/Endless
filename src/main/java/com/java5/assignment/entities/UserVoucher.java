package com.java5.assignment.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = UserVoucher.ENTITY_NAME)
@Table(name = UserVoucher.TABLE_NAME)
public class UserVoucher {
    public static final String ENTITY_NAME = "User_Voucher";
    public static final String TABLE_NAME = "UserVouchers";

    @EmbeddedId
    private UserVoucherId id;

    @MapsId("userID")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "UserID", nullable = false)
    private User userID;

    @MapsId("voucherID")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "VoucherID", nullable = false)
    private Voucher voucherID;

}