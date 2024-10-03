package com.example.demo1.repository;

import com.example.demo1.model.Prescription;

import java.util.List;

public interface PrescriptionRepository {
    void save(Prescription prescription);
    Prescription findById(int prescriptionID);
    List<Prescription> findAll();
    void update(Prescription prescription);
    void delete(int prescriptionID);
}