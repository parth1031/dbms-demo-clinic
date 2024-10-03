package com.example.demo1.repository;

import com.example.demo1.model.MedicationDetail;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class MedicationDetailRepositoryImpl implements MedicationDetailRepository {

    private final JdbcTemplate jdbcTemplate;

    public MedicationDetailRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(MedicationDetail medicationDetail) {
        String sql = "INSERT INTO Medication_Detail (prescriptionID, advice) VALUES (?, ?)";
        return jdbcTemplate.update(sql, medicationDetail.getPrescriptionID(), medicationDetail.getAdvice());
    }

    @Override
    public int update(MedicationDetail medicationDetail) {
        String sql = "UPDATE Medication_Detail SET advice=? WHERE prescriptionID=?";
        return jdbcTemplate.update(sql, medicationDetail.getAdvice(), medicationDetail.getPrescriptionID());
    }

    @Override
    public MedicationDetail findById(int prescriptionID) {
        String sql = "SELECT * FROM Medication_Detail WHERE prescriptionID = ?";
        return jdbcTemplate.queryForObject(sql, this::mapRowToMedicationDetail, prescriptionID);
    }

    @Override
    public List<MedicationDetail> findAll() {
        String sql = "SELECT * FROM Medication_Detail";
        return jdbcTemplate.query(sql, this::mapRowToMedicationDetail);
    }

    @Override
    public int deleteById(int prescriptionID) {
        String sql = "DELETE FROM Medication_Detail WHERE prescriptionID = ?";
        return jdbcTemplate.update(sql, prescriptionID);
    }

    private MedicationDetail mapRowToMedicationDetail(ResultSet rs, int rowNum) throws SQLException {
        MedicationDetail medicationDetail = new MedicationDetail();
        medicationDetail.setPrescriptionID(rs.getInt("prescriptionID"));
        medicationDetail.setAdvice(rs.getString("advice"));
        return medicationDetail;
    }
}
