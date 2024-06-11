package com.java5.assignment.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {

    private int userID;
    @NotBlank(message = "Please enter username")
    private String username;
    @NotBlank(message = "Please enter fullname")
    private String fullName;

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

    @NotBlank(message = "Please enter email")
    @Email(message = "Email is in wrong format")
    private String email;
    //@Pattern(regexp = "^0[0-9]{9}$", message = "Phone number is in wrong format")

    @NotBlank(message = "Please enter phone")
    private String phone;


    private boolean role;
    @NotBlank(message = "Please enter address")
    private String address;

    private boolean status;


    private MultipartFile avatar;

}
