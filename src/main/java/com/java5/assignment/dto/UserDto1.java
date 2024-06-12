package com.java5.assignment.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.java5.assignment.entities.User}
 */
@Value
public class UserDto1 implements Serializable {
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
    String address;
}