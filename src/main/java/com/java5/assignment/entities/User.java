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
@Entity(name = User.ENTITY_NAME)
@Table(name = User.TABLE_NAME)
public class User {
    public static final String ENTITY_NAME = "UserModel";
    public static final String TABLE_NAME = "Users";
    public static final String COLUMN_ID_NAME = "UserID";
    public static final String COLUMN_USERNAME_NAME = "Username";
    public static final String COLUMN_PASSWORD_NAME = "Password";
    public static final String COLUMN_PHONE_NAME = "Phone";
    public static final String COLUMN_EMAIL_NAME = "Email";
    public static final String COLUMN_ROLE_NAME = "Role";
    public static final String COLUMN_STATUS_NAME = "Status";
    public static final String COLUMN_AVATAR_NAME = "Avatar";


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = COLUMN_ID_NAME, nullable = false)
    private Long id;

    @Size(max = 255)
    @NotNull
    @Nationalized
    @Column(name = COLUMN_USERNAME_NAME, nullable = false)
    private String username;

    @Size(max = 255)
    @NotNull
    @Nationalized
    @Column(name = COLUMN_PASSWORD_NAME, nullable = false)
    private String password;

    @Size(max = 11)
    @Nationalized
    @Column(name = COLUMN_PHONE_NAME, length = 11)
    private String phone;

    @Size(max = 255)
    @NotNull
    @Nationalized
    @Column(name = COLUMN_EMAIL_NAME, nullable = false)
    private String email;

    @NotNull
    @Column(name = COLUMN_ROLE_NAME, nullable = false)
    private Boolean role = false;

    @NotNull
    @Column(name = COLUMN_STATUS_NAME, nullable = false)
    private Boolean status = false;

    @Nationalized
    @Lob
    @Column(name = COLUMN_AVATAR_NAME)
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