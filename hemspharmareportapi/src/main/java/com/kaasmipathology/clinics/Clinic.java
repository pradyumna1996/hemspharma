package com.kaasmipathology.clinics;

public class Clinic {

    int id;
    String clinicName;
    String clinicAddress;
    String clinicPhoneNumber;
    String clinicPanNumber;
    String clinicRegNo;


    public Clinic(){

    }


    public Clinic(int id, String clinicName, String clinicAddress, String clinicPhoneNumber, String clinicPanNumber, String clinicRegNo) {
        this.id = id;
        this.clinicName = clinicName;
        this.clinicAddress = clinicAddress;
        this.clinicPhoneNumber = clinicPhoneNumber;
        this.clinicPanNumber = clinicPanNumber;
        this.clinicRegNo = clinicRegNo;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public String getClinicAddress() {
        return clinicAddress;
    }

    public void setClinicAddress(String clinicAddress) {
        this.clinicAddress = clinicAddress;
    }

    public String getClinicPhoneNumber() {
        return clinicPhoneNumber;
    }

    public void setClinicPhoneNumber(String clinicPhoneNumber) {
        this.clinicPhoneNumber = clinicPhoneNumber;
    }

    public String getClinicPanNumber() {
        return clinicPanNumber;
    }

    public void setClinicPanNumber(String clinicPanNumber) {
        this.clinicPanNumber = clinicPanNumber;
    }

    public String getClinicRegNo() {
        return clinicRegNo;
    }

    public void setClinicRegNo(String clinicRegNo) {
        this.clinicRegNo = clinicRegNo;
    }
}
