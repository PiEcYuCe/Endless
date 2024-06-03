package com.java5.assignment.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
    @NotNull(message = "Please enter password")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$\n",
            message = "Password are uppercase, lowercase, number, special characters and include 6")
    private String password;
    @NotBlank(message = "Please enter email")
    @Email(message = "Email is in wrong format")
    private String email;
    //@Pattern(regexp = "^0[0-9]{9}$", message = "Phone number is in wrong format")
    @NotNull(message = "Please enter phone")
    private String phone;
    @NotBlank(message = "Please select role")
    private String role;
    @NotBlank(message = "Please enter address")
    private String address;
    @NotBlank(message = "Please select status")
    private String status;
    @NotNull(message = "Please select image")
    private List<MultipartFile> image;
}
