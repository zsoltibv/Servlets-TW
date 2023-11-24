package com.example.servletexample.model;

import com.example.servletexample.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

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

    public User(String email, String pass) {
        this.email = email;
        this.pass = pass;
    }

    public User(Integer id, String email, String pass) {
        this.id = id;
        this.email = email;
        this.pass = pass;
    }
}