package com.java5.assignment.model.share.login;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterModel {
    @NotEmpty(message = "Please enter username here!")
    @NotBlank(message = "Username cannot be blank")
    @Pattern(
            regexp = "^[A-Za-z\\d]+$",
            message = "Username can only contain letters and numbers"
    )
    @Pattern(
            regexp = "^.{5,}$",
            message = "Username must be at least 5 characters long"
    )
    @Pattern(
            regexp = "^(?=.*[A-Za-z]).*$",
            message = "Username must contain at least one letter"
    )
    @Pattern(
            regexp = "^(?!\\d+$).*$",
            message = "Username must not be entirely numeric"
    )
    private String username;

    @NotEmpty(message = "Please enter email here!")
    @Email(message = "Invalid email format")
    private String email;

    @NotEmpty(message = "Please enter password here!")
    @NotBlank(message = "Password cannot be blank")
    @Pattern(
            regexp = "^.{8,}$",
            message = "Password must be at least 8 characters long"
    )
    @Pattern(
            regexp = "^(?=.*[a-z]).*$",
            message = "Password must contain at least one lowercase letter"
    )
    @Pattern(
            regexp = "^(?=.*[A-Z]).*$",
            message = "Password must contain at least one uppercase letter"
    )
    @Pattern(
            regexp = "^(?=.*\\d).*$",
            message = "Password must contain at least one digit"
    )
    @Pattern(
            regexp = "^(?=.*[@$!%*?&]).*$",
            message = "Password must contain at least one special character"
    )
    private String password;

    @NotBlank(message = "Please repeat the password")
    private String repeatPassword;
}
