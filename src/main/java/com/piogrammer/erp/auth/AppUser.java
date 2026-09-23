package com.piogrammer.erp.auth;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
public class AppUser {

    @Id
    @GeneratedValue
    @Getter
    @Setter
    private Long id;

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

    private String username;

    private String password;



    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Enumerated(EnumType.STRING)
    private Role role;

    // getters setters
}