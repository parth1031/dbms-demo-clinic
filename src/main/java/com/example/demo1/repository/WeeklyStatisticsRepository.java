package com.example.demo1.repository;

import com.example.demo1.model.WeeklyStatistics;
import java.util.List;

public interface WeeklyStatisticsRepository {
    int save(WeeklyStatistics statistics);
    int update(WeeklyStatistics statistics);
    WeeklyStatistics findById(int statID);
    List<WeeklyStatistics> findAll();
    int deleteById(int statID);
}
