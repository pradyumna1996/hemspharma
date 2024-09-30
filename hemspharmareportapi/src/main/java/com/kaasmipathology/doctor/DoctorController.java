package com.kaasmipathology.doctor;

import io.javalin.Javalin;
import io.javalin.http.Handler;

import java.util.List;

public class DoctorController {

    private final DoctorService doctorService = new DoctorService();

    public DoctorController(Javalin app) {
        app.get("/api/doctors", getAllDoctors);
        app.get("/api/doctors/{id}", getDoctorById);
        app.post("/api/doctors", createDoctor);
        app.put("/api/doctors/{id}", updateDoctor);
        app.delete("/api/doctors/{id}", deleteDoctor);
    }

    // 1. Get All Doctors
    public Handler getAllDoctors = ctx -> {
        List<Doctor> doctors = doctorService.getAllDoctors();

        if (doctors.isEmpty()) {
            ctx.status(404).result("No Doctors in Record!!");
        } else {
            ctx.json(doctors).status(200);
        }
    };

    //2. Create Doctor Handler
    public Handler createDoctor = ctx -> {
        Doctor doctor = ctx.bodyAsClass(Doctor.class);

        Doctor createdDoctor = doctorService.createDoctor(doctor);

        if (createdDoctor != null) {
            ctx.status(201).json(createdDoctor);  // Return the created patient with a 201 status
        } else {
            ctx.status(500).result("Doctor not created due to an error.");
        }
    };

    // 3. Get Doctor By id
    public Handler getDoctorById = ctx -> {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Doctor doctor = doctorService.getDoctorById(id);
        if (doctor != null) {
            ctx.json(doctor);
        } else {
            ctx.status(404).result("Doctor not found");
        }
    };

    //4. Update Doctor Handler
    public Handler updateDoctor = ctx -> {
        int id = Integer.parseInt(ctx.pathParam("id"));  // Retrieve the patient ID from the URL path
        Doctor doctor = ctx.bodyAsClass(Doctor.class);  // Convert the incoming JSON body to a Patient object
        doctor.setId(id);  // Set the patient ID to the one retrieved from the URL

        if (doctorService.updateDoctor(doctor)) {
            ctx.status(200).json(doctor);  // Return the updated patient details with a 200 status
        } else {
            ctx.status(404).result("Doctor not found or update failed.");  // Return a 404 status if the patient was not found
        }
    };

    //5. Delete handler
    public Handler deleteDoctor = ctx -> {
        int id = Integer.parseInt(ctx.pathParam("id"));  // Retrieve the patient ID from the URL path

        if (doctorService.deleteDoctor(id)) {
            ctx.status(204).result("Doctor Deleted !!");  // Return a 204 status with a success message
        } else {
            ctx.status(404).result("Doctor not found or could not be deleted.");  // Return a 404 status with an error message
        }
    };

}


