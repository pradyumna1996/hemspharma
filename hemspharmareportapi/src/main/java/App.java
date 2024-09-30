import com.kaasmipathology.clinics.ClinicController;
import com.kaasmipathology.dashboard.DashboardController;
import com.kaasmipathology.doctor.DoctorService;
import com.kaasmipathology.labreport.LabReportController;
import com.kaasmipathology.labreport.LabReportGenerator;
import com.kaasmipathology.labreport.LabReportService;
import com.kaasmipathology.labtestenrollment.LabTestEnrollmentController;
import com.kaasmipathology.appointment.AppointmentController;
import com.kaasmipathology.doctor.DoctorController;
import com.kaasmipathology.labtestenrollment.LabTestEnrollmentService;
import com.kaasmipathology.pathtests.ComponentLabTestController;
import com.kaasmipathology.pathtests.MainLabTestController;
import com.kaasmipathology.pathtests.MainLabTestService;
import com.kaasmipathology.patient.PatientService;
import io.javalin.Javalin;

import com.kaasmipathology.patient.PatientController;
import io.javalin.json.JavalinJackson;
import io.javalin.plugin.bundled.CorsPluginConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;


public class App {
    public static void main(String[] args) {

        Javalin app = Javalin.create(config -> {

            config.bundledPlugins.enableCors(cors -> {
                cors.addRule(CorsPluginConfig.CorsRule::anyHost); // Allow requests from any host
            });

            // Configure JSON mapper to handle Java 8 date/time types
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            config.jsonMapper(new JavalinJackson());
        }).start(7000);

        new PatientController(app);
        new DoctorController(app);

        new MainLabTestController(app);
        new ComponentLabTestController(app);

        new LabTestEnrollmentController(app);
        new LabReportController();

        LabReportController labReportController = new LabReportController();

        AppointmentController appointmentController = new AppointmentController();
        appointmentController.registerRoutes(app);



        PatientService patientService = new PatientService();
        DoctorService doctorService = new DoctorService();
        LabTestEnrollmentService labTestEnrollmentService = new LabTestEnrollmentService();
        MainLabTestService mainLabTestService = new MainLabTestService();

        DashboardController dashboardController = new DashboardController(patientService, doctorService, labTestEnrollmentService, mainLabTestService);

        app.get("/api/dashboard/metrics", dashboardController::countAllMetrics);

        app.get("/api/get-enrollment-details/{enrollmentId}", labReportController::getLabReport);


        ClinicController clinicController = new ClinicController();
        clinicController.registerRoutes(app);



        // Define a route to handle report generation when the QR code is scanned
        app.get("/api/lab-report/generate-lab-report/{enrollmentId}", labReportController::generateLabReport);
    }



}


