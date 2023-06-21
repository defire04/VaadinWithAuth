package com.example.data.entity;


import jakarta.persistence.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "application_user")
public class User {
    @Id
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    private String password;
    private String name;
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "username"))
    @Enumerated(EnumType.STRING)
    private final Set<Role> roles = new HashSet<>(Collections.singletonList(Role.USER));

    private String email;

    public User(String username, String password, String name, String email) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;

//        this.role = "ROLE_USER";
//        this.role = "ROLE_ADMIN";
    }

    public User() {

    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addRole(Role newRole) {
        roles.add(newRole);
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//    public String getRole() {
//        return role;
//    }
//
//    public void setRole(String role) {
//        this.role = "ROLE_" + role;
//    }
}