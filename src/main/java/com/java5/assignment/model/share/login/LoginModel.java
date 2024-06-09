package com.java5.assignment.model.share.login;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginModel {
    @NotEmpty(message = "Please enter username here!")
    private String username;
    @NotEmpty(message = "Please enter password here!")
    private String password;
    private boolean rememberMe;
}
