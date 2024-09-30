package com.kaasmipathology.appointment;

import com.kaasmipathology.doctor.Doctor;
import com.kaasmipathology.patient.Patient;

import java.time.LocalDateTime;

public class Appointment {
    private int id;
    private Doctor doctor;
    private Patient patient;
    private LocalDateTime appointmentDateTime;
    private boolean isAppointed;

    public Appointment() {
    }

    public Appointment(int id, Doctor doctor, Patient patient, LocalDateTime appointmentDateTime) {
        this.id = id;
        this.doctor = doctor;
        this.patient = patient;
        this.appointmentDateTime = appointmentDateTime;
    }

    public Appointment(int id, Doctor doctor, Patient patient, LocalDateTime appointmentDateTime, boolean isAppointed) {

        this.id = id;
        this.doctor = doctor;
        this.patient = patient;
        this.appointmentDateTime = appointmentDateTime;
        this.isAppointed =isAppointed;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public LocalDateTime getAppointmentDateTime() {
        return appointmentDateTime;
    }

    public void setAppointmentDateTime(LocalDateTime appointmentDateTime) {
        this.appointmentDateTime = appointmentDateTime;
    }

    public void setAppointed(boolean appointed) {
        isAppointed = appointed;
    }

    public boolean isAppointed() {
        return isAppointed;
    }
}
