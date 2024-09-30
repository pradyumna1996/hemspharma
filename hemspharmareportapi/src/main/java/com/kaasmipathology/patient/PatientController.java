package com.kaasmipathology.patient;

import io.javalin.Javalin;
import io.javalin.http.Handler;

import java.util.List;
import java.util.logging.Logger;

public class PatientController {

    private final PatientService patientService = new PatientService();

    public PatientController(Javalin app) {
        app.get("/api/patients", getAllPatients);
        app.get("/api/patients/{id}", getPatientById);
        app.post("/api/patients", createPatient);
        app.put("/api/patients/{id}", updatePatient);
        app.delete("/api/patients/{id}", deletePatient);

    }

    // 1. Get All Patients
    public Handler getAllPatients = ctx -> {
        List<Patient> patients = patientService.getAllPatients();

        if (patients.isEmpty()) {
            ctx.status(404).result("No Patients in Record!!");
        } else {
            ctx.json(patients).status(200);
        }
    };


    //2. Create Patient Handler
    public Handler createPatient = ctx -> {
        Patient patient = ctx.bodyAsClass(Patient.class);

        Patient createdPatient = patientService.createPatient(patient);

        if (createdPatient != null) {
            ctx.status(201).json(createdPatient);  // Return the created patient with a 201 status
        } else {
            ctx.status(500).result("Patient not created due to an error.");
        }
    };

    // 3. Get Patient By id
    public Handler getPatientById = ctx -> {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Patient patient = patientService.getPatientById(id);
        if (patient != null) {
            ctx.json(patient);
        } else {
            ctx.status(404).result("Patient not found");
        }
    };

    //4. Update Patient Handler
    public Handler updatePatient = ctx -> {
        int id = Integer.parseInt(ctx.pathParam("id"));  // Retrieve the patient ID from the URL path
        Patient patient = ctx.bodyAsClass(Patient.class);  // Convert the incoming JSON body to a Patient object
        patient.setId(id);  // Set the patient ID to the one retrieved from the URL

        if (patientService.updatePatient(patient)) {
            ctx.status(200).json(patient);  // Return the updated patient details with a 200 status
        } else {
            ctx.status(404).result("Patient not found or update failed.");  // Return a 404 status if the patient was not found
        }
    };


    //5. Delete handler
    public Handler deletePatient = ctx -> {
        int id = Integer.parseInt(ctx.pathParam("id"));  // Retrieve the patient ID from the URL path

        if (patientService.deletePatient(id)) {
            ctx.status(204).result("Patient Deleted !!");  // Return a 204 status with a success message
        } else {
            ctx.status(404).result("Patient not found or could not be deleted.");  // Return a 404 status with an error message
        }
    };



}

