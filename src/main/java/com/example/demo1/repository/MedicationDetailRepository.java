package com.example.demo1.repository;

import com.example.demo1.model.MedicationDetail;

import java.util.List;

public interface MedicationDetailRepository {
    int save(MedicationDetail medicationDetail);
    int update(MedicationDetail medicationDetail);
    MedicationDetail findById(int prescriptionID);
    List<MedicationDetail> findAll();
    int deleteById(int prescriptionID);
}
