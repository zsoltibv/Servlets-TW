package com.example.servletexample.Repository;

import com.example.servletexample.DatabaseConnectionManager;
import com.example.servletexample.model.Discipline;
import com.example.servletexample.model.Grade;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DisciplineRepository {
    private final Connection connection;
    private static List<Discipline> disciplines = new ArrayList<>();
    private static List<Grade> grades = new ArrayList<>();

    public DisciplineRepository() throws SQLException, ClassNotFoundException {
        // Initialize the database connection in the constructor
        this.connection = DatabaseConnectionManager.getConnection();
    }

    public void addDiscipline(Discipline discipline) {
        String sql = "INSERT INTO disciplines (name, teacher_id) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, discipline.getName());
            preparedStatement.setInt(2, discipline.getTeacherId());

            preparedStatement.executeUpdate();

            // Retrieve the auto-generated discipline ID
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    discipline.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Discipline> getAllDisciplines() {
        List<Discipline> disciplines = new ArrayList<>();
        String sql = "SELECT * FROM disciplines";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Discipline discipline = new Discipline();
                discipline.setId(resultSet.getInt("id"));
                discipline.setName(resultSet.getString("name"));
                discipline.setTeacherId(resultSet.getInt("teacher_id"));

                disciplines.add(discipline);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return disciplines;
    }

    public Discipline getDisciplineById(int disciplineId) {
        String sql = "SELECT * FROM disciplines WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, disciplineId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Discipline discipline = new Discipline();
                    discipline.setId(resultSet.getInt("id"));
                    discipline.setName(resultSet.getString("name"));
                    discipline.setTeacherId(resultSet.getInt("teacher_id"));

                    return discipline;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Discipline> getDisciplinesByProfessor(int professorId) {
        List<Discipline> disciplines = new ArrayList<>();
        String sql = "SELECT * FROM disciplines WHERE teacher_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, professorId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Discipline discipline = new Discipline();
                    discipline.setId(resultSet.getInt("id"));
                    discipline.setName(resultSet.getString("name"));
                    discipline.setTeacherId(resultSet.getInt("teacher_id"));

                    disciplines.add(discipline);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return disciplines;
    }

    public List<Grade> getGradesByDiscipline(int disciplineId) {
        List<Grade> gradesForDiscipline = new ArrayList<>();
        for (Grade grade : grades) {
            if (grade.getDisciplineId() == disciplineId) {
                gradesForDiscipline.add(grade);
            }
        }
        return gradesForDiscipline;
    }

    public void addGrade(Grade grade) {
        // Add the grade to the list
        grades.add(grade);
    }

    public String getDisciplineName(int disciplineId) {
        String disciplineName = null;
        String sql = "SELECT name FROM disciplines WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, disciplineId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    disciplineName = resultSet.getString("name");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return disciplineName;
    }
}
