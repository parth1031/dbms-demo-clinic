package com.example.demo1.repository;

import com.example.demo1.model.TimeSlot;
import com.example.demo1.repository.TimeSlotRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TimeSlotRepositoryImpl implements TimeSlotRepository {
    private final String jdbcURL = "jdbc:mysql://localhost:3306/your_database"; // Change to your database URL
    private final String jdbcUsername = "your_username"; // Change to your database username
    private final String jdbcPassword = "your_password"; // Change to your database password

    // SQL queries
    private static final String INSERT_TIMESLOT_SQL = "INSERT INTO time_slots (doctorID, timeslot, workday) VALUES (?, ?, ?)";
    private static final String SELECT_TIMESLOT_BY_ID = "SELECT doctorID, timeslot, workday FROM time_slots WHERE doctorID = ? AND timeslot = ?";
    private static final String SELECT_ALL_TIMESLOTS = "SELECT * FROM time_slots";
    private static final String UPDATE_TIMESLOT_SQL = "UPDATE time_slots SET workday = ? WHERE doctorID = ? AND timeslot = ?";
    private static final String DELETE_TIMESLOT_SQL = "DELETE FROM time_slots WHERE doctorID = ? AND timeslot = ?";

    @Override
    public void save(TimeSlot timeSlot) {
        try (Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TIMESLOT_SQL)) {
            preparedStatement.setInt(1, timeSlot.getDoctorID());
            preparedStatement.setTime(2, timeSlot.getTimeslot());
            preparedStatement.setString(3, timeSlot.getWorkday());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public TimeSlot findById(int doctorID, Time timeSlot) {
        TimeSlot ts = null;
        try (Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TIMESLOT_BY_ID)) {
            preparedStatement.setInt(1, doctorID);
            preparedStatement.setTime(2, timeSlot);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                ts = new TimeSlot();
                ts.setDoctorID(resultSet.getInt("doctorID"));
                ts.setTimeslot(resultSet.getTime("timeslot"));
                ts.setWorkday(resultSet.getString("workday"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ts;
    }

    @Override
    public List<TimeSlot> findAll() {
        List<TimeSlot> timeSlots = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_TIMESLOTS);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                TimeSlot ts = new TimeSlot();
                ts.setDoctorID(resultSet.getInt("doctorID"));
                ts.setTimeslot(resultSet.getTime("timeslot"));
                ts.setWorkday(resultSet.getString("workday"));
                timeSlots.add(ts);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return timeSlots;
    }

    @Override
    public void update(TimeSlot timeSlot) {
        try (Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_TIMESLOT_SQL)) {
            preparedStatement.setString(1, timeSlot.getWorkday());
            preparedStatement.setInt(2, timeSlot.getDoctorID());
            preparedStatement.setTime(3, timeSlot.getTimeslot());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int doctorID, Time timeSlot) {
        try (Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_TIMESLOT_SQL)) {
            preparedStatement.setInt(1, doctorID);
            preparedStatement.setTime(2, timeSlot);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
