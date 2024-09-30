package com.kaasmipathology.dashboard;

import com.kaasmipathology.doctor.DoctorService;
import com.kaasmipathology.labtestenrollment.LabTestEnrollmentService;
import com.kaasmipathology.pathtests.MainLabTestService;
import com.kaasmipathology.patient.PatientService;
import io.javalin.http.Context;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DashboardController {

    private final PatientService patientService;
    private final DoctorService doctorService;
    private final LabTestEnrollmentService labTestEnrollmentService;
    private final MainLabTestService mainLabTestService;


    public DashboardController(PatientService patientService, DoctorService doctorService, LabTestEnrollmentService labTestEnrollmentService, MainLabTestService mainLabTestService) {
        this.patientService = patientService;
        this.doctorService = doctorService;
        this.labTestEnrollmentService = labTestEnrollmentService;
        this.mainLabTestService = mainLabTestService;
    }

    public void countAllMetrics(Context ctx) {
        try {
            // Count all the entities
            int patientCount = patientService.getAllPatients().size();
            int doctorCount = doctorService.getAllDoctors().size();
            int mainLabTestCount = mainLabTestService.getAllLabTests().size();
            int enrollmentCount = labTestEnrollmentService.getAllEnrollments().size();

            // Create a map to store the counts
            Map<String, Integer> metrics = new HashMap<>();
            metrics.put("patientCount", patientCount);
            metrics.put("doctorCount", doctorCount);
            metrics.put("mainLabTestCount", mainLabTestCount);
            metrics.put("enrollmentCount", enrollmentCount);

            // Return the metrics as a JSON response
            ctx.json(metrics);
        } catch (SQLException e) {
            e.printStackTrace();
            ctx.status(500).json("Error fetching metrics");
        }
    }
}
