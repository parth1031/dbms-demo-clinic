package com.example.demo1.repository;

import com.example.demo1.model.WeeklyStatistics;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class WeeklyStatisticsRepositoryImpl implements WeeklyStatisticsRepository {

    private final JdbcTemplate jdbcTemplate;

    public WeeklyStatisticsRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(WeeklyStatistics statistics) {
        String sql = "INSERT INTO Weekly_Statistics (week, totalAppointments, totalIncome, totalUsersRegistered) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(sql, statistics.getWeek(), statistics.getTotalAppointments(), statistics.getTotalIncome(), statistics.getTotalUsersRegistered());
    }

    @Override
    public int update(WeeklyStatistics statistics) {
        String sql = "UPDATE Weekly_Statistics SET week=?, totalAppointments=?, totalIncome=?, totalUsersRegistered=? WHERE statID=?";
        return jdbcTemplate.update(sql, statistics.getWeek(), statistics.getTotalAppointments(), statistics.getTotalIncome(), statistics.getTotalUsersRegistered(), statistics.getStatID());
    }

    @Override
    public WeeklyStatistics findById(int statID) {
        String sql = "SELECT * FROM Weekly_Statistics WHERE statID = ?";
        return jdbcTemplate.queryForObject(sql, this::mapRowToWeeklyStatistics, statID);
    }

    @Override
    public List<WeeklyStatistics> findAll() {
        String sql = "SELECT * FROM Weekly_Statistics";
        return jdbcTemplate.query(sql, this::mapRowToWeeklyStatistics);
    }

    @Override
    public int deleteById(int statID) {
        String sql = "DELETE FROM Weekly_Statistics WHERE statID = ?";
        return jdbcTemplate.update(sql, statID);
    }

    private WeeklyStatistics mapRowToWeeklyStatistics(ResultSet rs, int rowNum) throws SQLException {
        WeeklyStatistics statistics = new WeeklyStatistics();
        statistics.setStatID(rs.getInt("statID"));
        statistics.setWeek(rs.getString("week"));
        statistics.setTotalAppointments(rs.getInt("totalAppointments"));
        statistics.setTotalIncome(rs.getDouble("totalIncome"));
        statistics.setTotalUsersRegistered(rs.getInt("totalUsersRegistered"));
        return statistics;
    }
}
