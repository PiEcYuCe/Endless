package com.java5.assignment.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class UserVoucherId implements Serializable {
    public static final String COLUMN_USERID_NAME = "UserID";
    public static final String COLUMN_VOUCHERID_NAME = "VoucherID";
    private static final long serialVersionUID = 2936525196983193874L;

    @NotNull
    @Column(name = COLUMN_USERID_NAME, nullable = false)
    private Long userID;

    @NotNull
    @Column(name = COLUMN_VOUCHERID_NAME, nullable = false)
    private Long voucherID;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserVoucherId entity = (UserVoucherId) o;
        return Objects.equals(this.voucherID, entity.voucherID) &&
                Objects.equals(this.userID, entity.userID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(voucherID, userID);
    }

}