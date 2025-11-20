package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
    name = "users",
    indexes = {
        @Index(columnList = "username", name = "idx_users_username"),
        @Index(columnList = "email", name = "idx_users_email")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, length = 80)
    private String firstName;

    @Column(nullable = false, length = 80)
    private String lastName;

    @Column(nullable = false, unique = true, length = 120)
    private String email;

    @Column(nullable = false, length = 120)
    private String password;

    @PrePersist
    @PreUpdate
    private void normalize() {
        if (email != null) {
            email = email.trim().toLowerCase();
        }
        if (username != null) {
            username = username.trim().toLowerCase();
        }
    }
}