package com.example.servletexample.Repository;

import com.example.servletexample.DatabaseConnectionManager;
import com.example.servletexample.model.Grade;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GradeRepository {
    private final Connection connection;

    public GradeRepository() throws SQLException, ClassNotFoundException {
        // Initialize the database connection in the constructor
        this.connection = DatabaseConnectionManager.getConnection();
    }

    public void addGrade(Grade grade) {
        String sql = "INSERT INTO grades (student_id, discipline_id, value, date) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, grade.getStudentId());
            preparedStatement.setInt(2, grade.getDisciplineId());
            preparedStatement.setInt(3, grade.getValue());
            preparedStatement.setDate(4, Date.valueOf(grade.getDate()));

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Grade> getAllGrades() {
        List<Grade> grades = new ArrayList<>();
        String sql = "SELECT * FROM grades";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Grade grade = new Grade();
                grade.setId(resultSet.getInt("id"));
                grade.setStudentId(resultSet.getInt("student_id"));
                grade.setDisciplineId(resultSet.getInt("discipline_id"));
                grade.setValue(resultSet.getInt("value"));
                grade.setDate(resultSet.getDate("date").toLocalDate());

                grades.add(grade);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return grades;
    }

    public List<Grade> getGradesByStudent(int studentId) {
        List<Grade> grades = new ArrayList<>();
        String sql = "SELECT * FROM grades WHERE student_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, studentId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Grade grade = new Grade();
                    grade.setId(resultSet.getInt("id"));
                    grade.setStudentId(resultSet.getInt("student_id"));
                    grade.setDisciplineId(resultSet.getInt("discipline_id"));
                    grade.setValue(resultSet.getInt("value"));
                    grade.setDate(resultSet.getDate("date").toLocalDate());

                    grades.add(grade);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return grades;
    }
}
