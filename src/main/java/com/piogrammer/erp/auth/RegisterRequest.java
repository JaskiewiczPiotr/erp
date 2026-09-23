package com.piogrammer.erp.auth;

import lombok.Getter;
import lombok.Setter;

public class RegisterRequest {


    @Getter
    @Setter
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String password;

    // getters setters
}