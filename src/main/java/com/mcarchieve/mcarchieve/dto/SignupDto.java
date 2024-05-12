package com.mcarchieve.mcarchieve.dto;

public class SignupDto {

    public SignupDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    //    @NotNull
//    @Size(min = 3, max = 50)
    private String email;

    //    @NotNull
//    @Size(min = 3, max = 100)
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}