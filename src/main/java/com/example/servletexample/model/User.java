package com.example.servletexample.model;

import com.example.servletexample.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;
import java.util.UUID;

@Data
@AllArgsConstructor
public class User {
    public Integer id;
    private String email;
    private String pass;
    public Role role;

    public User() {
    }

    public User(String email, String pass, String role) {
        this.email = email;
        this.pass = pass;
        this.role = "TEACHER".equals(role) ? Role.TEACHER : Role.STUDENT;
    }

    public User(Integer id, String email, String pass) {
        this.id = id;
        this.email = email;
        this.pass = pass;
    }

    public User(Integer id, String email, String pass, String role) {
        this.id = id;
        this.email = email;
        this.pass = pass;
        this.role = "TEACHER".equals(role) ? Role.TEACHER : Role.STUDENT;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", pass='" + pass + '\'' +
                ", role=" + role +
                '}';
    }
}