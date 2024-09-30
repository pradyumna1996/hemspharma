package com.kaasmipathology.pathtests;

import com.kaasmipathology.patient.Patient;
import com.kaasmipathology.patient.PatientService;
import io.javalin.Javalin;
import io.javalin.http.Handler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class MainLabTestController {


    private static final Logger log = LoggerFactory.getLogger(MainLabTestController.class);
    private final MainLabTestService labTestService = new MainLabTestService();

    public MainLabTestController(Javalin app) {
        app.get("/api/mainLabTests", getAllMainLabTests);
        app.post("/api/mainLabTests", saveMainLabTests);
        app.put("/api/updateLabTest/{id}", updateMainLabTests);
        app.delete("/api/deleteLabTest/{id}",deleteMainLabTests);
        app.get("/api/mainLabTests/{id}", getMainLabTestById );
    }

    public Handler getMainLabTestById =ctx -> {
        int id = Integer.parseInt(ctx.pathParam("id"));
        MainLabTest labTest = labTestService.getMainLabTestById(id);
        if (labTest != null) {
            ctx.json(labTest);
        } else {
            ctx.status(404).result("Lab Test not found");
        }


    };

    public Handler getAllMainLabTests = ctx -> {
        List<MainLabTest> mainLabTests= labTestService.getAllLabTests();
        if(mainLabTests != null) {
            ctx.status(200).json(mainLabTests);
        }else{
            ctx.status(404).result("No Lab Tests Found ! Please Add !!" );
        }

    };


    public Handler saveMainLabTests = ctx -> {

        MainLabTest mainLabTest = ctx.bodyAsClass(MainLabTest.class);

        MainLabTest createdLabTest = labTestService.createMainLabTest(mainLabTest);

        if (createdLabTest != null) {
            ctx.status(201).json(createdLabTest);  // Return the created patient with a 201 status
        } else {
            ctx.status(500).result("Lab Test not created due to an error.");
        }


    };

    //4. Update Lab Test Handler
    public Handler updateMainLabTests = ctx -> {
        int id = Integer.parseInt(ctx.pathParam("id"));  // Retrieve the patient ID from the URL path
        log.debug("Id to Update {} " , id);
        MainLabTest mainLabTest = ctx.bodyAsClass(MainLabTest.class);  // Convert the incoming JSON body to a Patient object
        mainLabTest.setId(id);  // Set the patient ID to the one retrieved from the URL

        if (labTestService.updateMainLabTest(mainLabTest)) {
            ctx.status(200).json(mainLabTest);  // Return the updated patient details with a 200 status
        } else {
            ctx.status(404).result("Lab Test not found or update failed.");  // Return a 404 status if the patient was not found
        }
    };

    public Handler deleteMainLabTests = ctx -> {
        int id = Integer.parseInt(ctx.pathParam("id"));

        if (labTestService.deleteMainLabTests(id)) {
            ctx.status(204).result("Deleted Lab Test !!");  // Return a 204 status with a success message
        } else {
            ctx.status(404).result("Patient not found or could not be deleted.");  // Return a 404 status with an error message
        }
    };


}
