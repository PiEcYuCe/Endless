package com.java5.assignment.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = Voucher.ENTITY_NAME)
@Table(name = Voucher.TABLE_NAME)
public class Voucher {
    public static final String ENTITY_NAME = "VoucherModel";
    public static final String TABLE_NAME = "Vouchers";
    public static final String COLUMN_ID_NAME = "VoucherID";
    public static final String COLUMN_LEASTBILL_NAME = "LeastBill";
    public static final String COLUMN_LEASTDISCOUNT_NAME = "LeastDiscount";
    public static final String COLUMN_BIGGESTDISCOUNT_NAME = "BiggestDiscount";
    public static final String COLUMN_DISCOUNTLEVEL_NAME = "DiscountLevel";
    public static final String COLUMN_DISCOUNTFORM_NAME = "DiscountForm";
    public static final String COLUMN_STARTDATE_NAME = "StartDate";
    public static final String COLUMN_ENDDATE_NAME = "EndDate";


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = COLUMN_ID_NAME, nullable = false)
    private Long id;

    @NotNull
    @Column(name = COLUMN_LEASTBILL_NAME, nullable = false, precision = 18, scale = 2)
    private BigDecimal leastBill;

    @NotNull
    @Column(name = COLUMN_LEASTDISCOUNT_NAME, nullable = false, precision = 18, scale = 2)
    private BigDecimal leastDiscount;

    @NotNull
    @Column(name = COLUMN_BIGGESTDISCOUNT_NAME, nullable = false, precision = 18, scale = 2)
    private BigDecimal biggestDiscount;

    @NotNull
    @Column(name = COLUMN_DISCOUNTLEVEL_NAME, nullable = false)
    private Integer discountLevel;

    @Size(max = 50)
    @NotNull
    @Nationalized
    @Column(name = COLUMN_DISCOUNTFORM_NAME, nullable = false, length = 50)
    private String discountForm;

    @NotNull
    @Column(name = COLUMN_STARTDATE_NAME, nullable = false)
    private LocalDate startDate;

    @NotNull
    @Column(name = COLUMN_ENDDATE_NAME, nullable = false)
    private LocalDate endDate;

    @OneToMany(mappedBy = "voucherID")
    private Set<Order> orders = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "voucherID")
    private Set<User> users = new LinkedHashSet<>();

}