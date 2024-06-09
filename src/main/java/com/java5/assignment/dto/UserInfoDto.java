package com.java5.assignment.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDto implements Serializable {
    Long id;
    @NotNull
    @Size(max = 255)
    String username;
    @Size(max = 255)
    String fullname;
    @Size(max = 11)
    String phone;
    @NotNull
    @Size(max = 255)
    String email;
    String avatar;
    String address;
}