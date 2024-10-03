package com.example.demo1.repository;

import com.example.demo1.model.MonthlyStatistics;
import java.util.List;

public interface MonthlyStatisticsRepository {
    int save(MonthlyStatistics statistics);
    int update(MonthlyStatistics statistics);
    MonthlyStatistics findById(int statID);
    List<MonthlyStatistics> findAll();
    int deleteById(int statID);
}
