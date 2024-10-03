package com.example.demo1.repository;

import com.example.demo1.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final DataSource dataSource;

    public UserRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public User findById(int userID) throws SQLException {
        String sql = "SELECT * FROM Users WHERE UserID = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapUser(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<User> findAll() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM Users";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                users.add(mapUser(rs));
            }
        }
        return users;
    }

    @Override
    public void save(User user) throws SQLException {
        String sql = "INSERT INTO Users (Username, Password, Role, ContactInfo, YearOfBirth, MonthOfBirth, DayOfBirth, BloodGroup, FirstName, LastName, Age, Email, Address, Gender) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            setUserParameters(stmt, user);
            stmt.executeUpdate();
        }
    }

    @Override
    public void update(User user) throws SQLException {
        String sql = "UPDATE Users SET Username = ?, Password = ?, Role = ?, ContactInfo = ?, YearOfBirth = ?, MonthOfBirth = ?, DayOfBirth = ?, BloodGroup = ?, FirstName = ?, LastName = ?, Age = ?, Email = ?, Address = ?, Gender = ? WHERE UserID = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            setUserParameters(stmt, user);
            stmt.setInt(14, user.getUserID());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(int userID) throws SQLException {
        String sql = "DELETE FROM Users WHERE UserID = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            stmt.executeUpdate();
        }
    }

    private User mapUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserID(rs.getInt("UserID"));
        user.setUsername(rs.getString("Username"));
        user.setPassword(rs.getString("Password"));
        user.setRole(rs.getString("Role"));
        user.setContactInfo(rs.getString("ContactInfo"));
        user.setYearOfBirth(rs.getInt("YearOfBirth"));
        user.setMonthOfBirth(rs.getInt("MonthOfBirth"));
        user.setDayOfBirth(rs.getInt("DayOfBirth"));
        user.setBloodGroup(rs.getString("BloodGroup"));
        user.setFirstName(rs.getString("FirstName"));
        user.setLastName(rs.getString("LastName"));
        user.setAge(rs.getInt("Age"));
        user.setEmail(rs.getString("Email"));
        user.setAddress(rs.getString("Address"));
        user.setGender(rs.getString("Gender"));
        return user;
    }

    private void setUserParameters(PreparedStatement stmt, User user) throws SQLException {
        stmt.setString(1, user.getUsername());
        stmt.setString(2, user.getPassword());
        stmt.setString(3, user.getRole());
        stmt.setString(4, user.getContactInfo());
        stmt.setInt(5, user.getYearOfBirth());
        stmt.setInt(6, user.getMonthOfBirth());
        stmt.setInt(7, user.getDayOfBirth());
        stmt.setString(8, user.getBloodGroup());
        stmt.setString(9, user.getFirstName());
        stmt.setString(10, user.getLastName());
        stmt.setInt(11, user.getAge());
        stmt.setString(12, user.getEmail());
        stmt.setString(13, user.getAddress());
        stmt.setString(14, user.getGender());
    }
}
