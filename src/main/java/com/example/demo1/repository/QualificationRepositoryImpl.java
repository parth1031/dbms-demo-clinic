package com.example.demo1.repository;

import com.example.demo1.model.Qualification;
import com.example.demo1.repository.QualificationRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QualificationRepositoryImpl implements QualificationRepository {
    private final String jdbcURL = "jdbc:mysql://localhost:3306/your_database"; // Change to your database URL
    private final String jdbcUsername = "your_username"; // Change to your database username
    private final String jdbcPassword = "your_password"; // Change to your database password

    // SQL queries
    private static final String INSERT_QUALIFICATION_SQL = "INSERT INTO qualifications (doctorID, degree) VALUES (?, ?)";
    private static final String SELECT_QUALIFICATION_BY_ID = "SELECT doctorID, degree FROM qualifications WHERE doctorID = ?";
    private static final String SELECT_ALL_QUALIFICATIONS = "SELECT * FROM qualifications";
    private static final String UPDATE_QUALIFICATION_SQL = "UPDATE qualifications SET degree = ? WHERE doctorID = ?";
    private static final String DELETE_QUALIFICATION_SQL = "DELETE FROM qualifications WHERE doctorID = ?";

    @Override
    public void save(Qualification qualification) {
        try (Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUALIFICATION_SQL)) {
            preparedStatement.setInt(1, qualification.getDoctorID());
            preparedStatement.setString(2, qualification.getDegree());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Qualification findById(int doctorID) {
        Qualification qualification = null;
        try (Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUALIFICATION_BY_ID)) {
            preparedStatement.setInt(1, doctorID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                qualification = new Qualification();
                qualification.setDoctorID(resultSet.getInt("doctorID"));
                qualification.setDegree(resultSet.getString("degree"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return qualification;
    }

    @Override
    public List<Qualification> findAll() {
        List<Qualification> qualifications = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_QUALIFICATIONS);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Qualification qualification = new Qualification();
                qualification.setDoctorID(resultSet.getInt("doctorID"));
                qualification.setDegree(resultSet.getString("degree"));
                qualifications.add(qualification);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return qualifications;
    }

    @Override
    public void update(Qualification qualification) {
        try (Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUALIFICATION_SQL)) {
            preparedStatement.setString(1, qualification.getDegree());
            preparedStatement.setInt(2, qualification.getDoctorID());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int doctorID) {
        try (Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUALIFICATION_SQL)) {
            preparedStatement.setInt(1, doctorID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
