package com.kaasmipathology.labreport;

import java.sql.Date;
import java.util.List;

public class LabReportGenerationDTO {
    private String patientName;
    private int patientAge;
    private String patientSex;
    private String patientAddress;
    private String labTestName;
    private Date enrollmentDate;
    private List<ComponentDTO> components;

    // Inner class to hold individual component data
    public static class ComponentDTO {
        private String componentName;
        private String componentValue;
        private String componentUnit;
        private String componentRefRange;

        // Constructors, getters, and setters
        public ComponentDTO(String componentName, String componentValue, String componentUnit, String componentRefRange) {
            this.componentName = componentName;
            this.componentValue = componentValue;
            this.componentUnit = componentUnit;
            this.componentRefRange = componentRefRange;
        }

        public String getComponentName() {
            return componentName;
        }

        public void setComponentName(String componentName) {
            this.componentName = componentName;
        }

        public String getComponentValue() {
            return componentValue;
        }

        public void setComponentValue(String componentValue) {
            this.componentValue = componentValue;
        }

        public String getComponentUnit() {
            return componentUnit;
        }

        public void setComponentUnit(String componentUnit) {
            this.componentUnit = componentUnit;
        }

        public String getComponentRefRange() {
            return componentRefRange;
        }

        public void setComponentRefRange(String componentRefRange) {
            this.componentRefRange = componentRefRange;
        }
    }

    // Constructors, getters, and setters
    public LabReportGenerationDTO() {}

    public LabReportGenerationDTO(String patientName, int patientAge, String patientSex, String patientAddress, String labTestName, Date enrollmentDate, List<ComponentDTO> components) {
        this.patientName = patientName;
        this.patientAge = patientAge;
        this.patientSex = patientSex;
        this.patientAddress = patientAddress;
        this.labTestName = labTestName;
        this.enrollmentDate = enrollmentDate;
        this.components = components;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
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

    public String getPatientAddress() {
        return patientAddress;
    }

    public void setPatientAddress(String patientAddress) {
        this.patientAddress = patientAddress;
    }

    public String getLabTestName() {
        return labTestName;
    }

    public void setLabTestName(String labTestName) {
        this.labTestName = labTestName;
    }

    public Date getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(Date enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public List<ComponentDTO> getComponents() {
        return components;
    }

    public void setComponents(List<ComponentDTO> components) {
        this.components = components;
    }
}
