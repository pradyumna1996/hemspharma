package com.kaasmipathology.pathtests;

import io.javalin.Javalin;
import io.javalin.http.Handler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ComponentLabTestController {

    private static final Logger log = LoggerFactory.getLogger(ComponentLabTestController.class);
    private final ComponentLabTestService componentLabTestService = new ComponentLabTestService();

    public ComponentLabTestController(Javalin app) {
        app.get("/api/testcomponents", getAllComponentLabTests);
        app.get("/api/testcomponents/{labTestId}", getComponentsByLabTestId);
        app.post("/api/testcomponents", saveComponentLabTest);
        app.put("/api/testcomponents/{id}", updateComponentLabTest);
        app.delete("/api/testcomponents/{id}", deleteComponentLabTest);
    }

    // 1. Get All Components
    public Handler getAllComponentLabTests = ctx -> {
        List<ComponentLabTest> components = componentLabTestService.getAllComponentLabTests();
        if (components != null && !components.isEmpty()) {
            ctx.status(200).json(components);
        } else {
            ctx.status(204).result("No components found.");
        }
    };

    // 2. Get Components by Lab Test ID
    public Handler getComponentsByLabTestId = ctx -> {
        int labTestId = Integer.parseInt(ctx.pathParam("labTestId"));
        List<ComponentLabTest> components = componentLabTestService.getComponentsByLabTestId(labTestId);
        if (components != null && !components.isEmpty()) {
            ctx.status(200).json(components);
        } else {
            ctx.status(204).result("No components found for this lab test.");
        }
    };

    // 3. Save Component
    public Handler saveComponentLabTest = ctx -> {
        String labTestIdParam = ctx.queryParam("labTestId");
        log.info("Query Lab Test ID {}", labTestIdParam);

        int labTestId = Integer.parseInt(labTestIdParam); // Convert to int

        // Parse the request body to a ComponentLabTest object
        ComponentLabTest componentLabTest = ctx.bodyAsClass(ComponentLabTest.class);
        componentLabTest.setLabTestId(labTestId); // Set the labTestId

        // Call the service to save the component lab test
        ComponentLabTest createdComponent = componentLabTestService.createComponentLabTest2(componentLabTest);

        // Check if creation was successful
        if (createdComponent != null) {
            ctx.status(201); // Created
            ctx.json(createdComponent); // Return the created component as JSON
        } else {
            ctx.status(500); // Internal Server Error
            ctx.result("Failed to create component lab test");
        }


    };

    // 4. Update Component
    public Handler updateComponentLabTest = ctx -> {
        int id = Integer.parseInt(ctx.pathParam("id"));
        ComponentLabTest componentLabTest = ctx.bodyAsClass(ComponentLabTest.class);
        componentLabTest.setId(id);

        if (componentLabTestService.updateComponentLabTest(componentLabTest)) {
            ctx.status(200).json(componentLabTest);
        } else {
            ctx.status(404).result("Component not found or update failed.");
        }
    };

    // 5. Delete Component
    public Handler deleteComponentLabTest = ctx -> {
        int id = Integer.parseInt(ctx.pathParam("id"));

        if (componentLabTestService.deleteComponentLabTest(id)) {
            ctx.status(204).result("Deleted component successfully.");
        } else {
            ctx.status(404).result("Component not found or could not be deleted.");
        }
    };

}
