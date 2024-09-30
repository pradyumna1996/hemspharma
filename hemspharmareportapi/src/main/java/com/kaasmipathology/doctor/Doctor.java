package com.kaasmipathology.doctor;


public class Doctor {
    private int id;
    private String doctorName;
    private String doctorAddress;
    private String doctorSpecialization;

    public Doctor(){

    }
    public Doctor(int id, String doctorName, String doctorAddress, String doctorSpecialization) {
        this.id = id;
        this.doctorName = doctorName;
        this.doctorAddress = doctorAddress;
        this.doctorSpecialization = doctorSpecialization;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}

