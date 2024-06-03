package com.java5.assignment.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterModel {
    @NotBlank(message = "Username cannot be blank")
    @Pattern(regexp = "^(?=.*[A-Za-z])[A-Za-z\\d]{5,}$", message = "Username must contain at least a letter and be at least 5 characters long")
    private String username;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "Password requires lowercase, uppercase, digit, and special character")
    private String password;

    @NotBlank(message = "Please repeat the password")
    private String repeatPassword;
}
