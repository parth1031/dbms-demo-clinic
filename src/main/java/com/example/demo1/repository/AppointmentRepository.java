package com.example.demo1.repository;

import com.example.demo1.model.Appointment;
import java.sql.Date;
import java.util.List;

public interface AppointmentRepository {
    void save(Appointment appointment);
    Appointment findById(int appointmentID);
    List<Appointment> findAll();
    void update(Appointment appointment);
    void delete(int appointmentID);
}
