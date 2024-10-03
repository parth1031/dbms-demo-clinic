package com.example.demo1.repository;

import com.example.demo1.model.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class DepartmentRepositoryImpl implements DepartmentRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Department> rowMapper = new RowMapper<Department>() {
        @Override
        public Department mapRow(ResultSet rs, int rowNum) throws SQLException {
            Department department = new Department();
            department.setDepartmentID(rs.getInt("departmentID"));
            department.setDepartmentName(rs.getString("departmentName"));
            department.setDescription(rs.getString("description"));
            return department;
        }
    };

    @Override
    public void save(Department department) {
        String sql = "INSERT INTO departments (departmentName, description) VALUES (?, ?)";
        jdbcTemplate.update(sql, department.getDepartmentName(), department.getDescription());
    }

    @Override
    public Department findById(int departmentID) {
        String sql = "SELECT * FROM departments WHERE departmentID = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{departmentID}, rowMapper);
    }

    @Override
    public List<Department> findAll() {
        String sql = "SELECT * FROM departments";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public void update(Department department) {
        String sql = "UPDATE departments SET departmentName = ?, description = ? WHERE departmentID = ?";
        jdbcTemplate.update(sql, department.getDepartmentName(), department.getDescription(), department.getDepartmentID());
    }

    @Override
    public void delete(int departmentID) {
        String sql = "DELETE FROM departments WHERE departmentID = ?";
        jdbcTemplate.update(sql, departmentID);
    }
}
