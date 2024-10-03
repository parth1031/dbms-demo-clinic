package com.example.demo1.repository;

import com.example.demo1.model.Feedback;

import java.util.List;

public interface FeedbackRepository {
    int save(Feedback feedback);
    int update(Feedback feedback);
    Feedback findById(int feedbackID);
    List<Feedback> findAll();
    int deleteById(int feedbackID);
}
