package com.java5.assignment.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
public class User {

    private int userID;
    @NotBlank(message = "Please enter username")
    private String username;
    private String password;
    @NotBlank(message = "Please enter email")
    @Email(message = "Email is in wrong format")
    private String email;
    @NotBlank(message = "Please select role")
    private String role;
    private boolean status;
    private List<MultipartFile> image;
}
