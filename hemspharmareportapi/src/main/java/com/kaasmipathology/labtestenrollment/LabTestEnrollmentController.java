package com.kaasmipathology.labtestenrollment;

import com.kaasmipathology.patient.Patient;
import io.javalin.Javalin;
import io.javalin.http.Handler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LabTestEnrollmentController {

    private static final Logger log = LoggerFactory.getLogger(LabTestEnrollmentController.class);
    private LabTestEnrollmentService labTestEnrollmentService;

    public LabTestEnrollmentController(Javalin app) {
        this.labTestEnrollmentService = new LabTestEnrollmentService();
        registerRoutes(app);
    }

    private void registerRoutes(Javalin app) {
        app.post("/api/enroll-patient", enrollPatientHandler);
        app.post("/api/submit-component-values", submitComponentValuesHandler);
        app.get("/api/get-all-test-enrollments", getEnrollmentsHandler);
    };


    public final Handler getEnrollmentsHandler = ctx -> {
        try {
            List<Map<String, Object>> enrollments = labTestEnrollmentService.getAllEnrollments();
            ctx.json(enrollments);
        } catch (SQLException e) {
            ctx.status(500).result("Internal Server Error: " + e.getMessage());
        }
    };

    private final Handler enrollPatientHandler = ctx -> {
        try {
            String patientIdStr = ctx.formParam("patientId");
            String labTestIdStr = ctx.formParam("labTestId");

            if (patientIdStr == null || labTestIdStr == null) {
                ctx.status(400).result("Missing required parameters: patientId and labTestId");
                return;
            }

            int patientId = Integer.parseInt(patientIdStr);
            int labTestId = Integer.parseInt(labTestIdStr);

            int enrollmentId = labTestEnrollmentService.enrollPatient(patientId, labTestId);

            if (enrollmentId > 0) {
                LabTestEnrollmentController.log.debug("Enrollment ID:  {} ", enrollmentId); // Log enrollment ID
                Map<String, Object> responseMap = new HashMap<>();
                responseMap.put("message", "Patient enrolled successfully");
                responseMap.put("enrollmentId", enrollmentId);
                ctx.json(responseMap);
            } else {
                ctx.status(500).result("Enrollment failed.");
            }

        } catch (NumberFormatException e) {
            ctx.status(400).result("Invalid parameter format: " + e.getMessage());
        } catch (Exception e) {
            ctx.status(500).result("Internal Server Error: " + e.getMessage());
        }
    };


    private final Handler submitComponentValuesHandler = ctx -> {
        try {
            String enrollmentIdStr = ctx.formParam("enrollmentId");
            log.debug("Saving the components value of enrollment Id : {}" , enrollmentIdStr);
            if (enrollmentIdStr == null) {
                ctx.status(400).result("Missing required parameter: enrollmentId");
                return;
            }

            int enrollmentId = Integer.parseInt(enrollmentIdStr);
            Map<String, List<String>> componentValues = ctx.formParamMap();
            componentValues.remove("enrollmentId");

            if (componentValues.isEmpty()) {
                ctx.status(400).result("No component values provided.");
                return;
            }

            labTestEnrollmentService.submitComponentValues(enrollmentId, componentValues);

            ctx.result("Component values submitted successfully.");
        } catch (NumberFormatException e) {
            ctx.status(400).result("Invalid parameter format: " + e.getMessage());
        } catch (Exception e) {
            ctx.status(500).result("Internal Server Error: " + e.getMessage());
        }
    };


}
