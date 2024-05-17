package com.mcarchieve.mcarchieve.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupDto {
    private String email;
    private String password;

    public SignupDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}