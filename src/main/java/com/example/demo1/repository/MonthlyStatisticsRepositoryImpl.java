package com.example.demo1.repository;

import com.example.demo1.model.MonthlyStatistics;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class MonthlyStatisticsRepositoryImpl implements MonthlyStatisticsRepository {

    private final JdbcTemplate jdbcTemplate;

    public MonthlyStatisticsRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(MonthlyStatistics statistics) {
        String sql = "INSERT INTO Monthly_Statistics (monthName, totalAppointments, totalIncome, totalUsersRegistered) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(sql, statistics.getMonthName(), statistics.getTotalAppointments(), statistics.getTotalIncome(), statistics.getTotalUsersRegistered());
    }

    @Override
    public int update(MonthlyStatistics statistics) {
        String sql = "UPDATE Monthly_Statistics SET monthName=?, totalAppointments=?, totalIncome=?, totalUsersRegistered=? WHERE statID=?";
        return jdbcTemplate.update(sql, statistics.getMonthName(), statistics.getTotalAppointments(), statistics.getTotalIncome(), statistics.getTotalUsersRegistered(), statistics.getStatID());
    }

    @Override
    public MonthlyStatistics findById(int statID) {
        String sql = "SELECT * FROM Monthly_Statistics WHERE statID = ?";
        return jdbcTemplate.queryForObject(sql, this::mapRowToMonthlyStatistics, statID);
    }

    @Override
    public List<MonthlyStatistics> findAll() {
        String sql = "SELECT * FROM Monthly_Statistics";
        return jdbcTemplate.query(sql, this::mapRowToMonthlyStatistics);
    }

    @Override
    public int deleteById(int statID) {
        String sql = "DELETE FROM Monthly_Statistics WHERE statID = ?";
        return jdbcTemplate.update(sql, statID);
    }

    private MonthlyStatistics mapRowToMonthlyStatistics(ResultSet rs, int rowNum) throws SQLException {
        MonthlyStatistics statistics = new MonthlyStatistics();
        statistics.setStatID(rs.getInt("statID"));
        statistics.setMonthName(rs.getString("monthName"));
        statistics.setTotalAppointments(rs.getInt("totalAppointments"));
        statistics.setTotalIncome(rs.getDouble("totalIncome"));
        statistics.setTotalUsersRegistered(rs.getInt("totalUsersRegistered"));
        return statistics;
    }
}
