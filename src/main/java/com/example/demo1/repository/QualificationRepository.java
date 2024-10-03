package com.example.demo1.repository;

import com.example.demo1.model.Qualification;

import java.util.List;

public interface QualificationRepository {
    void save(Qualification qualification);
    Qualification findById(int doctorID);
    List<Qualification> findAll();
    void update(Qualification qualification);
    void delete(int doctorID);
}
