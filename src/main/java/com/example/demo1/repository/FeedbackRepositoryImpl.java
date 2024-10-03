package com.example.demo1.repository;

import com.example.demo1.model.Feedback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class FeedbackRepositoryImpl implements FeedbackRepository {

    private final JdbcTemplate jdbcTemplate;

    public FeedbackRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(Feedback feedback) {
        String sql = "INSERT INTO Feedback (appointmentID, rating, comments, feedbackDate) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(sql, feedback.getAppointmentID(), feedback.getRating(),
                feedback.getComments(), feedback.getFeedbackDate());
    }

    @Override
    public int update(Feedback feedback) {
        String sql = "UPDATE Feedback SET appointmentID=?, rating=?, comments=?, feedbackDate=? WHERE feedbackID=?";
        return jdbcTemplate.update(sql, feedback.getAppointmentID(), feedback.getRating(),
                feedback.getComments(), feedback.getFeedbackDate(), feedback.getFeedbackID());
    }

    @Override
    public Feedback findById(int feedbackID) {
        String sql = "SELECT * FROM Feedback WHERE feedbackID = ?";
        return jdbcTemplate.queryForObject(sql, this::mapRowToFeedback, feedbackID);
    }

    @Override
    public List<Feedback> findAll() {
        String sql = "SELECT * FROM Feedback";
        return jdbcTemplate.query(sql, this::mapRowToFeedback);
    }

    @Override
    public int deleteById(int feedbackID) {
        String sql = "DELETE FROM Feedback WHERE feedbackID = ?";
        return jdbcTemplate.update(sql, feedbackID);
    }

    private Feedback mapRowToFeedback(ResultSet rs, int rowNum) throws SQLException {
        Feedback feedback = new Feedback();
        feedback.setFeedbackID(rs.getInt("feedbackID"));
        feedback.setAppointmentID(rs.getInt("appointmentID"));
        feedback.setRating(rs.getInt("rating"));
        feedback.setComments(rs.getString("comments"));
        feedback.setFeedbackDate(rs.getDate("feedbackDate"));
        return feedback;
    }
}
