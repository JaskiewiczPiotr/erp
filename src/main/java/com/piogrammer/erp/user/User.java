package com.piogrammer.erp.user;

import jakarta.persistence.*;

import com.piogrammer.erp.user.Role;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class User {
    @Id
    @GeneratedValue
    private Long id;

    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
}
