package com.java5.assignment.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "PromotionDetails")
public class PromotionDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PromotionDetailID", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PromotionID", nullable = false)
    private Promotion promotionID;

    @NotNull
    @Column(name = "DiscountLevel", nullable = false)
    private Integer discountLevel;

    @OneToMany(mappedBy = "promotionDetailID")
    private Set<PromotionProduct> promotionProducts = new LinkedHashSet<>();

}