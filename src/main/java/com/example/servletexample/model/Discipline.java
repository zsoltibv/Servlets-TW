package com.example.servletexample.model;

import lombok.Data;

import java.util.List;

@Data
public class Discipline {
    private int id;
    private String name;
    private int teacherId; // ID of the teacher associated with this discipline
    private List<Grade> grades; // List of grades associated with this discipline

    // Constructors, getters, setters
}
