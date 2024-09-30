package com.kaasmipathology.appointment;

import com.kaasmipathology.doctor.Doctor;
import com.kaasmipathology.patient.Patient;
import com.kaasmipathology.util.DBConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AppointmentService {

    public List<Appointment> getAllAppointments() throws SQLException {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT a.id AS appointment_id, a.appointment_datetime, " +
                "p.id AS patient_id, p.patientName AS patient_name, p.patientAddress AS patient_address, " +
                "p.patientAge AS patient_age, p.patientSex AS patient_sex, p.patientPhone AS patient_phone, " +
                "d.id AS doctor_id, d.doctorName AS doctor_name, d.doctorAddress AS doctor_address, " +
                "d.doctorSpecialization AS doctor_specialization " +
                "FROM appointments a " +
                "JOIN patients p ON a.patient_id = p.id " +
                "JOIN doctors d ON a.doctor_id = d.id";

        try (Connection conn = DBConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Doctor doctor = new Doctor(
                        rs.getInt("doctor_id"), // Use the alias here
                        rs.getString("doctor_name"), // Use the alias here
                        rs.getString("doctor_address"), // Use the alias here
                        rs.getString("doctor_specialization") // Use the alias here
                );

                Patient patient = new Patient(
                        rs.getInt("patient_id"), // Use the alias here
                        rs.getString("patient_name"), // Use the alias here
                        rs.getString("patient_address"), // Use the alias here
                        rs.getInt("patient_age"), // Use the alias here
                        rs.getString("patient_sex"), // Use the alias here
                        rs.getString("patient_phone") // Use the alias here
                );

                Appointment appointment = new Appointment(
                        rs.getInt("appointment_id"), // Use the alias here
                        doctor,
                        patient,
                        rs.getTimestamp("appointment_datetime").toLocalDateTime()
                );

                appointments.add(appointment);
            }
        }

        return appointments;
    }

    public Appointment createAppointment(Doctor doctor, Patient patient, LocalDateTime appointmentDateTime) throws SQLException {
        String sql = "INSERT INTO appointments (doctor_id, patient_id, appointment_datetime) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, doctor.getId());
            pstmt.setInt(2, patient.getId());
            pstmt.setTimestamp(3, Timestamp.valueOf(appointmentDateTime));

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int id = generatedKeys.getInt(1);
                        return new Appointment(id, doctor, patient, appointmentDateTime);
                    }
                }
            }

        }
        return null;
    }

    public void deleteAppointment(int id) throws SQLException {
        String sql = "DELETE FROM appointments WHERE id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    public Appointment createUserAppointment(Doctor doctor, Patient patient, LocalDateTime appointmentDateTime) throws SQLException {
        // Updated SQL query to include the 'isAppointed' column, which defaults to FALSE
        String sql = "INSERT INTO appointments (doctor_id, patient_id, appointment_datetime, isAppointed) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, doctor.getId());
            pstmt.setInt(2, patient.getId());
            pstmt.setTimestamp(3, Timestamp.valueOf(appointmentDateTime));
            pstmt.setBoolean(4, false);  // Set 'isAppointed' to false for user-initiated appointments

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int id = generatedKeys.getInt(1);
                        // Return the newly created Appointment object
                        return new Appointment(id, doctor, patient, appointmentDateTime, false);
                    }
                }
            }
        }
        return null;
    }

}
