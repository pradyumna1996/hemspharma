package com.kaasmipathology.pathtests;

public class ComponentLabTest {
    private int id;
    private int labTestId;  // Foreign key to MainLabTest
    private String componentName;
    private String componentUnit;
    private String componentRefRange;

    public ComponentLabTest() {}

    public ComponentLabTest(int id, int labTestId, String componentName, String componentUnit, String componentRefRange) {
        this.id = id;
        this.labTestId = labTestId;  // Assign the foreign key
        this.componentName = componentName;
        this.componentUnit = componentUnit;
        this.componentRefRange = componentRefRange;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLabTestId() {
        return labTestId;
    }

    public void setLabTestId(int labTestId) {
        this.labTestId = labTestId;
    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
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
