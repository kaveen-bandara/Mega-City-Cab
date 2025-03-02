package com.megaCityCab.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AdminLoginRequest {

    @NotBlank(message = "Username is required!")
    private String username;

    @NotBlank(message = "Password is required!")
    @Size(min = 6, message = "Password must be at least 6 characters long!")
    private String password;
}
