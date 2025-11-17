package com.learning.backend.security.model;

import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private String password;
}
