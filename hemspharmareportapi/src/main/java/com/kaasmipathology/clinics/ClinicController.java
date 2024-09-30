package com.kaasmipathology.clinics;

import io.javalin.Javalin;
import io.javalin.http.Handler;

import java.sql.SQLException;
import java.util.List;

public class ClinicController {

    private  ClinicService clinicService = new ClinicService();



    public void registerRoutes(Javalin app) {
        app.post("/api/clinic", createClinic);
        app.put("/api/clinic/{id}", updateClinic);
        app.delete("/api/clinic/{id}", deleteClinic);
        app.get("/api/clinics", getAllClinics);  // Route to get all clinics
        app.get("/api/clinic/{id}", getClinicById);
    }

    // Get all clinics
    public Handler getAllClinics = ctx -> {
        try {
            List<Clinic> clinics = clinicService.getAllClinics();
            ctx.json(clinics); // Return the list of clinics as JSON
        } catch (SQLException e) {
            ctx.status(500).result("Error retrieving clinics");
        }
    };

    // Get a specific clinic by ID
    public Handler getClinicById = ctx -> {
        try {
            int id = Integer.parseInt(ctx.pathParam("id")); // Extract clinic ID from URL
            Clinic clinic = clinicService.getClinicById(id);
            if (clinic != null) {
                ctx.json(clinic); // Return the clinic as JSON
            } else {
                ctx.status(404).result("Clinic not found");
            }
        } catch (SQLException e) {
            ctx.status(500).result("Error retrieving clinic");
        } catch (NumberFormatException e) {
            ctx.status(400).result("Invalid clinic ID format");
        }
    };

    // Create a new clinic
    public Handler createClinic = ctx -> {
        Clinic clinic = ctx.bodyAsClass(Clinic.class); // Deserialize JSON to Clinic object
        Clinic createdClinic = clinicService.createClinic(clinic.getClinicName(), clinic.getClinicAddress(), clinic.getClinicPhoneNumber(),
                clinic.getClinicPanNumber(), clinic.getClinicRegNo());
        if (createdClinic != null) {
            ctx.status(201).json(createdClinic); // Send back the created clinic object
        } else {
            ctx.status(500).result("Failed to create clinic");
        }
    };

    // Update a clinic
    public Handler updateClinic = ctx -> {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Clinic clinic = ctx.bodyAsClass(Clinic.class); // Deserialize JSON to Clinic object
        Clinic updatedClinic = clinicService.updateClinic(id, clinic.getClinicName(), clinic.getClinicAddress(), clinic.getClinicPhoneNumber(),
                clinic.getClinicPanNumber(), clinic.getClinicRegNo());
        if (updatedClinic != null) {
            ctx.status(200).json(updatedClinic); // Send back the updated clinic object
        } else {
            ctx.status(404).result("Clinic not found or update failed");
        }
    };

    // Delete a clinic
    public Handler deleteClinic = ctx -> {
        int id = Integer.parseInt(ctx.pathParam("id"));
        boolean isDeleted = clinicService.deleteClinic(id);
        if (isDeleted) {
            ctx.status(204).result("Clinic deleted"); // No content for successful delete
        } else {
            ctx.status(404).result("Clinic not found or delete failed");
        }
    };
}

