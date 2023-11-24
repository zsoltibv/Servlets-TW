package com.example.servletexample.Repository;

import com.example.servletexample.DatabaseConnectionManager;
import com.example.servletexample.model.User;
import lombok.NoArgsConstructor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserRepository {
    private static final String SELECT_ALL_USERS = "SELECT * FROM users";
    private static final String INSERT_USER = "INSERT INTO users (email, password, role) VALUES (?, ?, ?)";
    private static final String SELECT_USER_BY_EMAIL_AND_PASSWORD = "SELECT * FROM users WHERE email = ? AND password = ?";

    private static Connection connection = null;

    public UserRepository() throws SQLException, ClassNotFoundException {
        // Initialize the database connection in the constructor
        this.connection = DatabaseConnectionManager.getConnection();
    }

    public static void addUser(User user) {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_USER)) {

            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPass());
            statement.setString(3, user.getRole().toString());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions appropriately
            throw new RuntimeException(e);
        }
    }

    public static Optional<User> findUserByEmailAndPassword(String email, String password) {
        try (Connection connection = DatabaseConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_EMAIL_AND_PASSWORD)) {

            statement.setString(1, email);
            statement.setString(2, password);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Integer userId = resultSet.getInt("id");
                    String userEmail = resultSet.getString("email");
                    String userPassword = resultSet.getString("password");
                    String userRole = resultSet.getString("role");

                    // Create and return a User object based on the database result
                    return Optional.of(new User(userId, userEmail, userPassword, userRole));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions appropriately
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    public static List<User> getUsers() {
        List<User> users = new ArrayList<>();

        try (Connection connection = DatabaseConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_USERS);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Integer userId = (Integer) resultSet.getObject("id");
                String userEmail = resultSet.getString("email");
                String userPassword = resultSet.getString("password");

                User user = new User(userId, userEmail, userPassword);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions appropriately
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return users;
    }
}
