package com.kaasmipathology.labtestenrollment;

import java.sql.Date;

public class LabTestEnrollment {

    private int id;
    private int  patientId;
    private int labTestId;

    private Date enrollmentDate;

    public LabTestEnrollment(){

    }

    public LabTestEnrollment(int patientId, int labTestId, Date enrollmentDate) {
        this.patientId = patientId;
        this.labTestId = labTestId;
        this.enrollmentDate = enrollmentDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getLabTestId() {
        return labTestId;
    }

    public void setLabTestId(int labTestId) {
        this.labTestId = labTestId;
    }

    public Date getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(Date enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }
}
