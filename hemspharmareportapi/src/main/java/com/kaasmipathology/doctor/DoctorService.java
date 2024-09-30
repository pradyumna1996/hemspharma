package com.kaasmipathology.doctor;


import com.kaasmipathology.util.DBConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorService {

    private static final Logger log = LoggerFactory.getLogger(DoctorService.class);

    //  1. Getting All Doctors
    public List<Doctor> getAllDoctors() {
        log.debug("Getting Doctor List!");
        List<Doctor> doctors = new ArrayList<>();
        String query = "SELECT * FROM doctors";

        try (Connection connection = DBConnection.getInstance().getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Doctor doctor = new Doctor(
                        rs.getInt("id"),
                        rs.getString("doctorName"),
                        rs.getString("doctorAddress"),
                        rs.getString("doctorSpecialization")
                );
                doctors.add(doctor);
            }
        } catch (SQLException e) {
            log.error("Error fetching patients from database: {}", e.getMessage());
        }
        return doctors; // Returning the list, even if it's empty
    }

    //2. Create a doctor
    public Doctor createDoctor(Doctor doctor) {
        String query = "INSERT INTO doctors (doctorName, doctorAddress, doctorSpecialization) VALUES (?, ?, ?)";

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, doctor.getDoctorName());
            stmt.setString(2, doctor.getDoctorAddress());
            stmt.setString(3, doctor.getDoctorSpecialization());
            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        doctor.setId(generatedKeys.getInt(1)); // Set the generated id to the patient object
                    }
                }
                return doctor;
            }
        } catch (SQLException e) {
            log.debug("Error creating patient: " + e.getMessage());
        }
        return null;
    }

    // 3. Get By doctor by Id
    public Doctor getDoctorById(int id) {
        Doctor doctor = null;
        String query = "SELECT * FROM doctors WHERE id = ?";

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                doctor = new Doctor(
                        rs.getInt("id"),
                        rs.getString("doctorName"),
                        rs.getString("doctorAddress"),
                        rs.getString("doctorSpecialization")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return doctor;
    }

    // 4. Update Doctor by Id
    public boolean updateDoctor(Doctor doctor) {
        String query = "UPDATE doctors SET doctorName = ?, doctorAddress = ?, doctorSpecialization = ? WHERE id = ?";

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, doctor.getDoctorName());
            stmt.setString(2, doctor.getDoctorAddress());
            stmt.setString(3, doctor.getDoctorSpecialization());
            stmt.setInt(4, doctor.getId());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;  // Returns true if the update was successful
        } catch (SQLException e) {
            log.debug(e.getMessage());  // Log the exception message
        }
        return false;  // Return false if there was an error or no rows were affected
    }

    //5. Delete Doctor
    public boolean deleteDoctor(int id) {
        String query = "DELETE FROM doctors WHERE id = ?";

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


