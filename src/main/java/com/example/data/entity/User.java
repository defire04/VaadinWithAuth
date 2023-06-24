package com.example.data.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "application_user")
public class User {
    @Id
    @Column(name = "username", nullable = false, unique = true)
    @NotEmpty(message = "Username should be not empty!")
    private String username;

    @Column(name = "password")
    @Size(min = 8, message = "Password must be more than 8 characters!")
    private String password;
    @Column(name = "name")
    @NotEmpty(message = "Name should be not empty!")
    private String name;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;
    @Column(name = "email", nullable = false)
    @Email
    @NotEmpty(message = "Email should be not empty!")
    private String email;
    @Column(name = "temp-password")
    private String tempPassword;

    @Column(name = "must-change-password")
    private boolean mustChangePassword = false;

    public User(String username, String password, String name, String email) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getTempPassword() {
        return tempPassword;
    }

    public void setTempPassword(String tempPassword) {
        this.tempPassword = tempPassword;
    }

    public boolean isMustChangePassword() {
        return mustChangePassword;
    }

    public void setMustChangePassword(boolean mustChangePassword) {
        this.mustChangePassword = mustChangePassword;
    }
}