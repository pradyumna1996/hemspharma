package com.kaasmipathology.pathtests;

public class MainLabTest {

    private int id;
    private String labTestName;
    private String labTestRemark;


    public MainLabTest() {

    }

    public MainLabTest(int id, String labTestName, String labTestRemark) {
        this.id = id;
        this.labTestName = labTestName;
        this.labTestRemark = labTestRemark;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabTestName() {
        return labTestName;
    }

    public void setLabTestName(String labTestName) {
        this.labTestName = labTestName;
    }

    public String getLabTestRemark() {
        return labTestRemark;
    }

    public void setLabTestRemark(String labTestRemark) {
        this.labTestRemark = labTestRemark;
    }
}
