package com.example.servletexample.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Grade {
    private int id;
    private int studentId;
    private int disciplineId;
    private int value;
    private LocalDate date;

    // Constructors, getters, setters
}
