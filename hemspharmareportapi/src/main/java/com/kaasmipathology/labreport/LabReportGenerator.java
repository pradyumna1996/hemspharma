package com.kaasmipathology.labreport;

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
import java.util.List;

public class LabReportGenerator {

    public File generateReport(LabReportGenerationDTO reportDTO) throws IOException {
        String projectDir = System.getProperty("user.dir");
        Path reportsDir = Paths.get(projectDir, "reports-lab");

        if (!Files.exists(reportsDir)) {
            Files.createDirectory(reportsDir);
        }

        String filePath = reportsDir.toString() + "/report_" + reportDTO.getPatientName().replace(" ", "_") + "_" + reportDTO.getLabTestName().replace(" ", "_") + ".pdf";
        PdfWriter writer = new PdfWriter(filePath);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        // Adding header and patient details
        document.add(new Paragraph("Kaasmi Pathology").setFontSize(20).setBold());
        document.add(new Paragraph("Dhangadhi -02, Purano Bhansar Road").setFontSize(12));
        document.add(new Paragraph("Tel: 9842378129").setFontSize(12));
        document.add(new Paragraph("Date: " + reportDTO.getEnrollmentDate()).setFontSize(12));
        document.add(new Paragraph("Patient Name: " + reportDTO.getPatientName()).setFontSize(12));
        document.add(new Paragraph("Age/Sex: " + reportDTO.getPatientAge() + "/" + reportDTO.getPatientSex()).setFontSize(12));
        document.add(new Paragraph("Address: " + reportDTO.getPatientAddress()).setFontSize(12));
        document.add(new Paragraph("Test Requested: " + reportDTO.getLabTestName()).setFontSize(12));

        // Create table for test components
        Table table = new Table(new float[]{1, 1, 1, 1});
        table.setWidth(UnitValue.createPercentValue(100));
        table.addHeaderCell("Test Name");
        table.addHeaderCell("Result");
        table.addHeaderCell("Unit");
        table.addHeaderCell("Reference Range");

        // Add components to the table
        List<LabReportGenerationDTO.ComponentDTO> components = reportDTO.getComponents();
        for (LabReportGenerationDTO.ComponentDTO component : components) {
            table.addCell(component.getComponentName());
            table.addCell(component.getComponentValue());
            table.addCell(component.getComponentUnit());
            table.addCell(component.getComponentRefRange());
        }

        document.add(table);
        document.close();

        // Return the file
        return new File(filePath);
    }
}
