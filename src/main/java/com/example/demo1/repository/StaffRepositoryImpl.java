package com.example.demo1.repository;

import com.example.demo1.model.Staff;
import com.example.demo1.repository.StaffRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StaffRepositoryImpl implements StaffRepository {
    private final String jdbcURL = "jdbc:mysql://localhost:3306/your_database"; // Change to your database URL
    private final String jdbcUsername = "your_username"; // Change to your database username
    private final String jdbcPassword = "your_password"; // Change to your database password

    // SQL queries
    private static final String INSERT_STAFF_SQL = "INSERT INTO staff (userID, designation, salary) VALUES (?, ?, ?)";
    private static final String SELECT_STAFF_BY_ID = "SELECT staffID, userID, designation, salary FROM staff WHERE staffID = ?";
    private static final String SELECT_ALL_STAFF = "SELECT * FROM staff";
    private static final String UPDATE_STAFF_SQL = "UPDATE staff SET userID = ?, designation = ?, salary = ? WHERE staffID = ?";
    private static final String DELETE_STAFF_SQL = "DELETE FROM staff WHERE staffID = ?";

    @Override
    public void save(Staff staff) {
        try (Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_STAFF_SQL)) {
            preparedStatement.setInt(1, staff.getUserID());
            preparedStatement.setString(2, staff.getDesignation());
            preparedStatement.setDouble(3, staff.getSalary());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Staff findById(int staffID) {
        Staff staff = null;
        try (Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_STAFF_BY_ID)) {
            preparedStatement.setInt(1, staffID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                staff = new Staff();
                staff.setStaffID(resultSet.getInt("staffID"));
                staff.setUserID(resultSet.getInt("userID"));
                staff.setDesignation(resultSet.getString("designation"));
                staff.setSalary(resultSet.getDouble("salary"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return staff;
    }

    @Override
    public List<Staff> findAll() {
        List<Staff> staffList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_STAFF);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Staff staff = new Staff();
                staff.setStaffID(resultSet.getInt("staffID"));
                staff.setUserID(resultSet.getInt("userID"));
                staff.setDesignation(resultSet.getString("designation"));
                staff.setSalary(resultSet.getDouble("salary"));
                staffList.add(staff);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return staffList;
    }

    @Override
    public void update(Staff staff) {
        try (Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_STAFF_SQL)) {
            preparedStatement.setInt(1, staff.getUserID());
            preparedStatement.setString(2, staff.getDesignation());
            preparedStatement.setDouble(3, staff.getSalary());
            preparedStatement.setInt(4, staff.getStaffID());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int staffID) {
        try (Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_STAFF_SQL)) {
            preparedStatement.setInt(1, staffID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
