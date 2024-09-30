package com.kaasmipathology.labreport;

import com.kaasmipathology.util.DBConnection;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class LabReportService {



    public LabReportGenerationDTO getLabReport(int enrollmentId) throws SQLException {
        String query = "SELECT p.patientName, p.patientAge, p.patientSex, p.patientAddress, " +
                "lt.labTestName, clt.componentName, ctv.componentValue, clt.componentUnit, " +
                "clt.componentRefRange, pte.enrollmentDate " +
                "FROM patient_test_enrollment pte " +
                "JOIN patients p ON p.id = pte.patientId " +
                "JOIN component_test_value ctv ON ctv.enrollmentId = pte.id " +
                "JOIN component_lab_tests clt ON clt.id = ctv.componentId " +
                "JOIN labtests lt ON lt.id = pte.labTestId " +
                "WHERE pte.id = ?";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, enrollmentId);
            ResultSet rs = pstmt.executeQuery();

            String patientName = "";
            int patientAge = 0;
            String patientSex = "";
            String patientAddress = "";
            String labTestName = "";
            Date enrollmentDate = null;
            List<LabReportGenerationDTO.ComponentDTO> components = new ArrayList<>();

            while (rs.next()) {
                if (patientName.isEmpty()) {
                    patientName = rs.getString("patientName");
                    patientAge = rs.getInt("patientAge");
                    patientSex = rs.getString("patientSex");
                    patientAddress = rs.getString("patientAddress");
                    labTestName = rs.getString("labTestName");
                    enrollmentDate = rs.getDate("enrollmentDate");
                }

                String componentName = rs.getString("componentName");
                String componentValue = rs.getString("componentValue");
                String componentUnit = rs.getString("componentUnit");
                String componentRefRange = rs.getString("componentRefRange");

                LabReportGenerationDTO.ComponentDTO component = new LabReportGenerationDTO.ComponentDTO(
                        componentName, componentValue, componentUnit, componentRefRange
                );

                components.add(component);
            }

            return new LabReportGenerationDTO(patientName, patientAge, patientSex, patientAddress, labTestName, enrollmentDate, components);
        }
    }
}
