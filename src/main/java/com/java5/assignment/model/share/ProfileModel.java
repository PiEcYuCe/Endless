package com.java5.assignment.model.share;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class ProfileModel {
    @NotBlank(message = "Please enter email")
    @Email(message = "Email is in wrong format")
    private String email;

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
