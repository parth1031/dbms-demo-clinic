package com.example.demo1.repository;

import com.example.demo1.model.Prescription;
import com.example.demo1.repository.PrescriptionRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrescriptionRepositoryImpl implements PrescriptionRepository {
    private final String jdbcURL = "jdbc:mysql://localhost:3306/your_database"; // Change to your database URL
    private final String jdbcUsername = "your_username"; // Change to your database username
    private final String jdbcPassword = "your_password"; // Change to your database password

    // SQL queries
    private static final String INSERT_PRESCRIPTION_SQL = "INSERT INTO prescriptions (appointmentID, dosage) VALUES (?, ?)";
    private static final String SELECT_PRESCRIPTION_BY_ID = "SELECT prescriptionID, appointmentID, dosage FROM prescriptions WHERE prescriptionID = ?";
    private static final String SELECT_ALL_PRESCRIPTIONS = "SELECT * FROM prescriptions";
    private static final String UPDATE_PRESCRIPTION_SQL = "UPDATE prescriptions SET appointmentID = ?, dosage = ? WHERE prescriptionID = ?";
    private static final String DELETE_PRESCRIPTION_SQL = "DELETE FROM prescriptions WHERE prescriptionID = ?";

    @Override
    public void save(Prescription prescription) {
        try (Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRESCRIPTION_SQL)) {
            preparedStatement.setInt(1, prescription.getAppointmentID());
            preparedStatement.setString(2, prescription.getDosage());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Prescription findById(int prescriptionID) {
        Prescription prescription = null;
        try (Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PRESCRIPTION_BY_ID)) {
            preparedStatement.setInt(1, prescriptionID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                prescription = new Prescription();
                prescription.setPrescriptionID(resultSet.getInt("prescriptionID"));
                prescription.setAppointmentID(resultSet.getInt("appointmentID"));
                prescription.setDosage(resultSet.getString("dosage"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prescription;
    }

    @Override
    public List<Prescription> findAll() {
        List<Prescription> prescriptions = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_PRESCRIPTIONS);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Prescription prescription = new Prescription();
                prescription.setPrescriptionID(resultSet.getInt("prescriptionID"));
                prescription.setAppointmentID(resultSet.getInt("appointmentID"));
                prescription.setDosage(resultSet.getString("dosage"));
                prescriptions.add(prescription);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prescriptions;
    }

    @Override
    public void update(Prescription prescription) {
        try (Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PRESCRIPTION_SQL)) {
            preparedStatement.setInt(1, prescription.getAppointmentID());
            preparedStatement.setString(2, prescription.getDosage());
            preparedStatement.setInt(3, prescription.getPrescriptionID());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int prescriptionID) {
        try (Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PRESCRIPTION_SQL)) {
            preparedStatement.setInt(1, prescriptionID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
