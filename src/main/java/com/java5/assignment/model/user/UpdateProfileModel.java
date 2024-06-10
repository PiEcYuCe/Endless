package com.java5.assignment.model.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProfileModel {
    @NotBlank(message = "Please enter fullname")
    private String fullname;
    @NotBlank(message = "Please enter email")
    @Email(message = "Email is in wrong format")
    private  String email;
    @NotNull(message = "Please enter phone")
    private String phone;
    @NotBlank(message = "Please enter address")
    private String address;
    private MultipartFile avatar;
}
