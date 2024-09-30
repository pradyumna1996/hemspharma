package com.kaasmipathology.labtestenrollment;

import com.kaasmipathology.util.DBConnection;
import com.kaasmipathology.util.DatabaseHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LabTestEnrollmentService {

    public int enrollPatient(int patientId, int labTestId) throws SQLException {
        String sql = "INSERT INTO patient_test_enrollment (patientId, labTestId) VALUES (?, ?)";
        return DatabaseHelper.executeInsert(sql, patientId, labTestId);
    }

    public void submitComponentValues(int enrollmentId, Map<String, List<String>> componentValues) throws SQLException {
        for (Map.Entry<String, List<String>> entry : componentValues.entrySet()) {
            int componentId = Integer.parseInt(entry.getKey());
            String value = entry.getValue().get(0); // Assuming one value per component

            String sql = "INSERT INTO component_test_value (enrollmentId, componentId, componentValue) VALUES (?, ?, ?)";
            DatabaseHelper.executeInsert(sql, enrollmentId, componentId, value);
        }
    }

    public List<Map<String, Object>> getAllEnrollments() throws SQLException {
            String sql = "SELECT pte.id , p.patientName AS patientName, lt.labTestName AS labTestName, "
                    + "pte.enrollmentDate "
                    + "FROM patient_test_enrollment pte "
                    + "JOIN patients p ON pte.patientId = p.id "
                    + "JOIN labtests lt ON pte.labTestId = lt.id";

            List<Map<String, Object>> results = new ArrayList<>();

            try (Connection connection = DBConnection.getInstance().getConnection();
                 PreparedStatement stmt = connection.prepareStatement(sql);
                 ResultSet resultSet = stmt.executeQuery()) {

                while (resultSet.next()) {
                    Map<String, Object> enrollment = new HashMap<>();
                    enrollment.put("id", resultSet.getInt("id"));
                    enrollment.put("patientName", resultSet.getString("patientName"));
                    enrollment.put("labTestName", resultSet.getString("labTestName"));
                    enrollment.put("enrollmentDate", resultSet.getDate("enrollmentDate"));
                    results.add(enrollment);
                }
            }
            return results;
        }


    }


