package com.example.demo1.repository;

import com.example.demo1.model.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class AppointmentRepositoryImpl implements AppointmentRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Appointment> rowMapper = new RowMapper<Appointment>() {
        @Override
        public Appointment mapRow(ResultSet rs, int rowNum) throws SQLException {
            Appointment appointment = new Appointment();
            appointment.setAppointmentID(rs.getInt("appointmentID"));
            appointment.setPatientID(rs.getInt("patientID"));
            appointment.setDoctorID(rs.getInt("doctorID"));
            appointment.setAppointmentDate(rs.getDate("appointmentDate"));
            appointment.setTime(rs.getTime("time"));
            appointment.setStatus(rs.getString("status"));
            return appointment;
        }
    };

    @Override
    public void save(Appointment appointment) {
        String sql = "INSERT INTO appointments (patientID, doctorID, appointmentDate, time, status) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, appointment.getPatientID(), appointment.getDoctorID(),
                appointment.getAppointmentDate(), appointment.getTime(),
                appointment.getStatus());
    }

    @Override
    public Appointment findById(int appointmentID) {
        String sql = "SELECT * FROM appointments WHERE appointmentID = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{appointmentID}, rowMapper);
    }

    @Override
    public List<Appointment> findAll() {
        String sql = "SELECT * FROM appointments";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public void update(Appointment appointment) {
        String sql = "UPDATE appointments SET patientID = ?, doctorID = ?, appointmentDate = ?, time = ?, status = ? WHERE appointmentID = ?";
        jdbcTemplate.update(sql, appointment.getPatientID(), appointment.getDoctorID(),
                appointment.getAppointmentDate(), appointment.getTime(),
                appointment.getStatus(), appointment.getAppointmentID());
    }

    @Override
    public void delete(int appointmentID) {
        String sql = "DELETE FROM appointments WHERE appointmentID = ?";
        jdbcTemplate.update(sql, appointmentID);
    }
}
