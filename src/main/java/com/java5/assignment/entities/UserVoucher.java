package com.java5.assignment.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Table(name = "UserVouchers")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserVoucher {
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