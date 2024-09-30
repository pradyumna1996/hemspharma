package com.kaasmipathology.clinics;
import com.kaasmipathology.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClinicService {


    public ClinicService() {

    }

    public List<Clinic> getAllClinics() throws SQLException {
        List<Clinic> clinics = new ArrayList<>();
        String sql = "SELECT * FROM clinics";
        try (Connection connection =DBConnection.getInstance().getConnection();
                Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                clinics.add(new Clinic(
                        rs.getInt("id"),
                        rs.getString("clinicName"),
                        rs.getString("clinicAddress"),
                        rs.getString("clinicPhoneNumber"),
                        rs.getString("clinicPanNumber"),
                        rs.getString("clinicRegNo")
                ));
            }
        }
        return clinics;
    }

    // Get a specific clinic by ID
    public Clinic getClinicById(int id) throws SQLException {
        String sql = "SELECT * FROM clinics WHERE id = ?";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Clinic(
                            rs.getInt("id"),
                            rs.getString("clinicName"),
                            rs.getString("clinicAddress"),
                            rs.getString("clinicPhoneNumber"),
                            rs.getString("clinicPanNumber"),
                            rs.getString("clinicRegNo")
                    );
                }
            }
        }
        return null; // Return null if clinic not found
    }


    // Create a new clinic and return the created clinic object
    public Clinic createClinic(String clinicName, String clinicAddress, String clinicPhoneNumber, String clinicPanNumber, String clinicRegNo) throws SQLException {
        String sql = "INSERT INTO clinics (clinicName, clinicAddress, clinicPhoneNumber, clinicPanNumber, clinicRegNo) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DBConnection.getInstance().getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, clinicName);
            stmt.setString(2, clinicAddress);
            stmt.setString(3, clinicPhoneNumber);
            stmt.setString(4, clinicPanNumber);
            stmt.setString(5, clinicRegNo);
            int affectedRows = stmt.executeUpdate();

            // Check if the row was inserted and retrieve the generated ID
            if (affectedRows == 1) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int id = generatedKeys.getInt(1);
                        // Return the newly created clinic object
                        return new Clinic(id, clinicName, clinicAddress, clinicPhoneNumber, clinicPanNumber, clinicRegNo);
                    }
                }
            }
        }
        return null; // Return null if creation failed
    }

    // Update a clinic and return the updated clinic object
    public Clinic updateClinic(int id, String clinicName, String clinicAddress, String clinicPhoneNumber, String clinicPanNumber, String clinicRegNo) throws SQLException {
        String sql = "UPDATE clinics SET clinicName = ?, clinicAddress = ?, clinicPhoneNumber = ?, clinicPanNumber = ?, clinicRegNo = ? WHERE id = ?";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, clinicName);
            stmt.setString(2, clinicAddress);
            stmt.setString(3, clinicPhoneNumber);
            stmt.setString(4, clinicPanNumber);
            stmt.setString(5, clinicRegNo);
            stmt.setInt(6, id);

            int affectedRows = stmt.executeUpdate();

            // If update is successful, return the updated clinic object
            if (affectedRows == 1) {
                return new Clinic(id, clinicName, clinicAddress, clinicPhoneNumber, clinicPanNumber, clinicRegNo);
            }
        }
        return null; // Return null if update failed
    }

    // Delete a clinic and return a boolean indicating success
    public boolean deleteClinic(int id) throws SQLException {
        String sql = "DELETE FROM clinics WHERE id = ?";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows == 1; // Return true if deletion was successful
        }
    }
}
