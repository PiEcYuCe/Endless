package com.java5.assignment.model;

import com.java5.assignment.validation.OnCreate;
import com.java5.assignment.validation.OnUpdate;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

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


    private String password;

    @NotBlank(message = "Please enter email")
    @Email(message = "Email is in wrong format")
    private String email;

    @NotBlank(message = "Please enter phone")
    private String phone;

    private boolean role;

    @NotBlank(message = "Please enter address")
    private String address;

    private boolean status;

    private MultipartFile avatar;
}
