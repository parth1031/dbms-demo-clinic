package com.example.demo1.repository;

import com.example.demo1.model.Description;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class DescriptionRepositoryImpl implements DescriptionRepository {

    private final JdbcTemplate jdbcTemplate;

    public DescriptionRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(Description description) {
        String sql = "INSERT INTO Description (appointmentID, problem) VALUES (?, ?)";
        return jdbcTemplate.update(sql, description.getAppointmentID(), description.getProblem());
    }

    @Override
    public int update(Description description) {
        String sql = "UPDATE Description SET problem=? WHERE appointmentID=?";
        return jdbcTemplate.update(sql, description.getProblem(), description.getAppointmentID());
    }

    @Override
    public Description findById(int appointmentID) {
        String sql = "SELECT * FROM Description WHERE appointmentID = ?";
        return jdbcTemplate.queryForObject(sql, this::mapRowToDescription, appointmentID);
    }

    @Override
    public List<Description> findAll() {
        String sql = "SELECT * FROM Description";
        return jdbcTemplate.query(sql, this::mapRowToDescription);
    }

    @Override
    public int deleteById(int appointmentID) {
        String sql = "DELETE FROM Description WHERE appointmentID = ?";
        return jdbcTemplate.update(sql, appointmentID);
    }

    private Description mapRowToDescription(ResultSet rs, int rowNum) throws SQLException {
        Description description = new Description();
        description.setAppointmentID(rs.getInt("appointmentID"));
        description.setProblem(rs.getString("problem"));
        return description;
    }
}
