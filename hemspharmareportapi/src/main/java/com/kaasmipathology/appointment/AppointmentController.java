package com.kaasmipathology.appointment;

import com.kaasmipathology.doctor.Doctor;
import com.kaasmipathology.patient.Patient;
import io.javalin.Javalin;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class AppointmentController {

    private final AppointmentService appointmentService = new AppointmentService();

    public void registerRoutes(Javalin app) {


        app.get("/api/appointments", ctx -> {
            List<Appointment> appointments = appointmentService.getAllAppointments();
            ctx.json(appointments);
        });

        app.post("/api/appointments", ctx -> {
            AppointmentRequest req = ctx.bodyAsClass(AppointmentRequest.class);
            Doctor doctor = new Doctor(
                    req.getDoctorId(),
                    req.getDoctorName(),
                    req.getDoctorAddress(),
                    req.getDoctorSpecialization()
            );


            Patient patient = new Patient(
                    req.getPatientId(),
                    req.getPatientName(),
                    req.getPatientAddress(),
                    req.getPatientAge(),
                    req.getPatientSex(),
                    req.getPatientPhone()
            );

            LocalDateTime appointmentDateTime = req.getAppointmentDateTime();

            try {
                Appointment appointment = appointmentService.createAppointment(doctor, patient, appointmentDateTime);
                if (appointment != null) {
                    ctx.status(201).json(appointment);
                } else {
                    ctx.status(400).result("Failed to create appointment");
                }
            } catch (SQLException e) {
                ctx.status(500).result("Error creating appointment: " + e.getMessage());
            }
        });

        app.delete("/api/appointments/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            try {
                appointmentService.deleteAppointment(id);
                ctx.status(204);
            } catch (SQLException e) {
                ctx.status(500).result("Error deleting appointment: " + e.getMessage());
            }
        });


        // User enrolls for appointment
        app.post("/api/user/appointments", ctx -> {
            // Parse the request body into the AppointmentRequest class
            AppointmentRequest req = ctx.bodyAsClass(AppointmentRequest.class);

            // Create Doctor and Patient objects from the request
            Doctor doctor = new Doctor(
                    req.getDoctorId(),
                    req.getDoctorName(),
                    req.getDoctorAddress(),
                    req.getDoctorSpecialization()
            );

            Patient patient = new Patient(
                    req.getPatientId(),
                    req.getPatientName(),
                    req.getPatientAddress(),
                    req.getPatientAge(),
                    req.getPatientSex(),
                    req.getPatientPhone()
            );

            // Get appointment datetime from the request
            LocalDateTime appointmentDateTime = req.getAppointmentDateTime();

            try {
                // Call the service to create the appointment, setting isAppointed to false by default
                Appointment appointment = appointmentService.createAppointment(doctor, patient, appointmentDateTime);

                // If the appointment is successfully created, return it with status 201
                if (appointment != null) {
                    ctx.status(201).json(appointment);
                } else {
                    ctx.status(400).result("Failed to create appointment");
                }
            } catch (SQLException e) {
                // Handle any SQL-related errors
                ctx.status(500).result("Error creating appointment: " + e.getMessage());
            }
        });


    }


    public static class AppointmentRequest {
        private int doctorId;
        private String doctorName;
        private String doctorAddress;
        private String doctorSpecialization;
        private int patientId;
        private String patientName;
        private String patientAddress;
        private int patientAge;
        private String patientSex;
        private String patientPhone;
        private LocalDateTime appointmentDateTime;

        // Getters and Setters
        public int getDoctorId() {
            return doctorId;
        }

        public void setDoctorId(int doctorId) {
            this.doctorId = doctorId;
        }

        public String getDoctorName() {
            return doctorName;
        }

        public void setDoctorName(String doctorName) {
            this.doctorName = doctorName;
        }

        public String getDoctorAddress() {
            return doctorAddress;
        }

        public void setDoctorAddress(String doctorAddress) {
            this.doctorAddress = doctorAddress;
        }

        public String getDoctorSpecialization() {
            return doctorSpecialization;
        }

        public void setDoctorSpecialization(String doctorSpecialization) {
            this.doctorSpecialization = doctorSpecialization;
        }

        public int getPatientId() {
            return patientId;
        }

        public void setPatientId(int patientId) {
            this.patientId = patientId;
        }

        public String getPatientName() {
            return patientName;
        }

        public void setPatientName(String patientName) {
            this.patientName = patientName;
        }

        public String getPatientAddress() {
            return patientAddress;
        }

        public void setPatientAddress(String patientAddress) {
            this.patientAddress = patientAddress;
        }

        public int getPatientAge() {
            return patientAge;
        }

        public void setPatientAge(int patientAge) {
            this.patientAge = patientAge;
        }

        public String getPatientSex() {
            return patientSex;
        }

        public void setPatientSex(String patientSex) {
            this.patientSex = patientSex;
        }

        public String getPatientPhone() {
            return patientPhone;
        }

        public void setPatientPhone(String patientPhone) {
            this.patientPhone = patientPhone;
        }

        public LocalDateTime getAppointmentDateTime() {
            return appointmentDateTime;
        }

        public void setAppointmentDateTime(LocalDateTime appointmentDateTime) {
            this.appointmentDateTime = appointmentDateTime;
        }
    }
}
