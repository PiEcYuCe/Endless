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
    private static final long serialVersionUID = -235753044003355306L;
    @NotNull
    @Column(name = "UserID", nullable = false)
    private Long userID;

    @NotNull
    @Column(name = "VoucherID", nullable = false)
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