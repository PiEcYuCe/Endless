package com.java5.assignment.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.util.LinkedHashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserID", nullable = false)
    private Long id;

    @Size(max = 255)
    @NotNull
    @Nationalized
    @Column(name = "Username", nullable = false)
    private String username;

    @Size(max = 255)
    @NotNull
    @Nationalized
    @Column(name = "Password", nullable = false)
    private String password;

    @Size(max = 11)
    @Nationalized
    @Column(name = "Phone", length = 11)
    private String phone;

    @Size(max = 255)
    @NotNull
    @Nationalized
    @Column(name = "Email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "Role", nullable = false)
    private Boolean role = false;

    @NotNull
    @Column(name = "Status", nullable = false)
    private Boolean status = false;

    @Nationalized
    @Lob
    @Column(name = "Avatar")
    private String avatar;

    @OneToMany(mappedBy = "userID")
    private Set<Cart> carts = new LinkedHashSet<>();

    @OneToMany(mappedBy = "userID")
    private Set<Order> orders = new LinkedHashSet<>();

    @OneToMany(mappedBy = "userID")
    private Set<Rating> ratings = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "userID")
    private Set<Voucher> vouchers = new LinkedHashSet<>();

}