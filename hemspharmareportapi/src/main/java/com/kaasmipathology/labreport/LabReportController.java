package com.kaasmipathology.labreport;

import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;

public class LabReportController {

    private final LabReportService labReportService;
    private final LabReportGenerator labReportGenerator = new LabReportGenerator();
    public LabReportController() {
        this.labReportService = new LabReportService();
    }

        public void getLabReport(Context ctx) {
        int enrollmentId = Integer.parseInt(ctx.pathParam("enrollmentId"));

        try {
            LabReportGenerationDTO labReport = labReportService.getLabReport(enrollmentId);

            if (labReport == null) {
                throw new NotFoundResponse("Lab Report not found for Enrollment ID: " + enrollmentId);
            }

            ctx.json(labReport);
        } catch (Exception e) {
            ctx.status(500).json(e.getMessage());
        }
    }


    public void generateLabReport(Context ctx) {
        try {
            // Get the enrollmentId from the path parameter (after QR code scan)
            int enrollmentId = Integer.parseInt(ctx.pathParam("enrollmentId"));

            // Fetch the lab report data for the given enrollmentId
            LabReportGenerationDTO reportDTO = labReportService.getLabReport(enrollmentId);

            // Generate the PDF report file using LabReportGenerator
            File reportFile = labReportGenerator.generateReport(reportDTO);

            // Ensure the file exists and can be accessed
            if (reportFile.exists() && reportFile.isFile()) {
                // Set the content type to application/pdf
                ctx.contentType("application/pdf");

                // Set the response header for file download (force download)
                ctx.header("Content-Disposition", "attachment; filename=" + reportFile.getName());

                // Stream the file content as the response result
                ctx.result(new FileInputStream(reportFile));

            } else {
                // If file is not found or doesn't exist, return 404 status
                ctx.status(404).result("Report not found.");
            }

        } catch (SQLException e) {
            ctx.status(500).result("Database error: " + e.getMessage());
        } catch (IOException e) {
            ctx.status(500).result("Error generating or reading PDF: " + e.getMessage());
        } catch (NumberFormatException e) {
            ctx.status(400).result("Invalid enrollment ID format.");
        }
    }
}
