package com.example.demo1.repository;

import com.example.demo1.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserRepository {
    User findById(int userID) throws SQLException;
    List<User> findAll() throws SQLException;
    void save(User user) throws SQLException;
    void update(User user) throws SQLException;
    void delete(int userID) throws SQLException;
}
