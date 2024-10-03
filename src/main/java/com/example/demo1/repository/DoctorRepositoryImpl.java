package com.example.demo1.repository;

import com.example.demo1.model.Doctor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class DoctorRepositoryImpl implements DoctorRepository {

    private final JdbcTemplate jdbcTemplate;

    public DoctorRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(Doctor doctor) {
        String sql = "INSERT INTO Doctor (userID, specialization, departmentID, fees, rating) VALUES (?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, doctor.getUserID(), doctor.getSpecialization(),
                doctor.getDepartmentID(), doctor.getFees(), doctor.getRating());
    }

    @Override
    public int update(Doctor doctor) {
        String sql = "UPDATE Doctor SET userID=?, specialization=?, departmentID=?, fees=?, rating=? WHERE doctorID=?";
        return jdbcTemplate.update(sql, doctor.getUserID(), doctor.getSpecialization(),
                doctor.getDepartmentID(), doctor.getFees(), doctor.getRating(), doctor.getDoctorID());
    }

    @Override
    public Doctor findById(int doctorID) {
        String sql = "SELECT * FROM Doctor WHERE doctorID = ?";
        return jdbcTemplate.queryForObject(sql, this::mapRowToDoctor, doctorID);
    }

    @Override
    public List<Doctor> findAll() {
        String sql = "SELECT * FROM Doctor";
        return jdbcTemplate.query(sql, this::mapRowToDoctor);
    }

    @Override
    public int deleteById(int doctorID) {
        String sql = "DELETE FROM Doctor WHERE doctorID = ?";
        return jdbcTemplate.update(sql, doctorID);
    }

    private Doctor mapRowToDoctor(ResultSet rs, int rowNum) throws SQLException {
        Doctor doctor = new Doctor();
        doctor.setDoctorID(rs.getInt("doctorID"));
        doctor.setUserID(rs.getInt("userID"));
        doctor.setSpecialization(rs.getString("specialization"));
        doctor.setDepartmentID(rs.getInt("departmentID"));
        doctor.setFees(rs.getDouble("fees"));
        doctor.setRating(rs.getInt("rating"));
        return doctor;
    }
}
