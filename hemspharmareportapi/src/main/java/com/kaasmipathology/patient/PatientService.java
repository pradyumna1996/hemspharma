package com.kaasmipathology.patient;

import com.kaasmipathology.util.DBConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientService {

    private static final Logger log = LoggerFactory.getLogger(PatientService.class);

    //  1. Getting All Patients
    public List<Patient> getAllPatients() {
        log.debug("Getting Patient List!");
        List<Patient> patients = new ArrayList<>();
        String query = "SELECT * FROM patients";

        try (Connection connection = DBConnection.getInstance().getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Patient patient = new Patient(
                        rs.getInt("id"),
                        rs.getString("patientName"),
                        rs.getString("patientAddress"),
                        rs.getInt("patientAge"),
                        rs.getString("patientSex"),
                        rs.getString("patientPhone")
                );
                patients.add(patient);
            }
        } catch (SQLException e) {
            log.error("Error fetching patients from database: {}", e.getMessage());
        }
        return patients; // Returning the list, even if it's empty
    }

    //2. Create a patient
    public Patient createPatient(Patient patient) {
        String query = "INSERT INTO patients (patientName, patientAddress, patientAge, patientSex, patientPhone) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, patient.getPatientName());
            pstmt.setString(2, patient.getPatientAddress());
            pstmt.setInt(3, patient.getPatientAge());
            pstmt.setString(4, patient.getPatientSex());
            pstmt.setString(5, patient.getPatientPhone());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        patient.setId(generatedKeys.getInt(1)); // Set the generated id to the patient object
                    }
                }
                return patient;
            }
        } catch (SQLException e) {
            log.debug("Error creating patient: " + e.getMessage());
        }
        return null;
    }

    // 3. Get By Id
    public Patient getPatientById(int id) {
        Patient patient = null;
        String query = "SELECT * FROM patients WHERE id = ?";

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                patient = new Patient(
                        rs.getInt("id"),
                        rs.getString("patientName"),
                        rs.getString("patientAddress"),
                        rs.getInt("patientAge"),
                        rs.getString("patientSex"),
                        rs.getString("patientPhone")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patient;
    }

    // 4. Update Patient by Id
    public boolean updatePatient(Patient patient) {
        String query = "UPDATE patients SET patientName = ?, patientAddress = ?, patientAge = ?, patientSex = ?, patientPhone = ? WHERE id = ?";

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, patient.getPatientName());
            pstmt.setString(2, patient.getPatientAddress());
            pstmt.setInt(3, patient.getPatientAge());
            pstmt.setString(4, patient.getPatientSex());
            pstmt.setString(5, patient.getPatientPhone());
            pstmt.setInt(6, patient.getId());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;  // Returns true if the update was successful
        } catch (SQLException e) {
            log.debug(e.getMessage());  // Log the exception message
        }
        return false;  // Return false if there was an error or no rows were affected
    }

    //5. Delete Patient
    public boolean deletePatient(int id) {
        String query = "DELETE FROM patients WHERE id = ?";

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();  // Execute the DELETE statement
            return affectedRows > 0;  // Return true if one or more rows were affected
        } catch (SQLException e) {
            log.debug(e.getMessage());  // Log the exception message
        }
        return false;  // Return false if there was an error or no rows were affected
    }

}

