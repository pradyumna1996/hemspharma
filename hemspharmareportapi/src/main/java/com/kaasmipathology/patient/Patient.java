package com.kaasmipathology.patient;

public class Patient {
    private int id;
    private String patientName;
    private String patientAddress;
    private int patientAge;
    private String patientSex;
    private String patientPhone;

    // Constructors, getters, and setters
    public Patient() {}

    public Patient(int id, String patientName, String patientAddress ,int patientAge, String patientSex , String patientPhone ) {
        this.id = id;
        this.patientName = patientName;
        this.patientAddress = patientAddress;
        this.patientAge = patientAge;
        this.patientSex = patientSex;
        this.patientPhone = patientPhone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}



