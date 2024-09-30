package com.kaasmipathology.pathtests;

import com.kaasmipathology.patient.Patient;
import com.kaasmipathology.patient.PatientService;
import com.kaasmipathology.util.DBConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MainLabTestService {


    private static final Logger log = LoggerFactory.getLogger(MainLabTestService.class);

    //  1. Getting All Main Lab Test
    public List<MainLabTest> getAllLabTests() {
        log.debug("Getting Main Lab test List!");
        List<MainLabTest> mainLabTests = new ArrayList<>();
        String query = "SELECT * FROM labtests";

        try (Connection connection = DBConnection.getInstance().getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                MainLabTest mainLabTest = new MainLabTest(
                     rs.getInt("id"),
                    rs.getString("labTestName"),
                    rs.getString("labTestRemark")
                );
                mainLabTests.add(mainLabTest);
            }
        } catch (SQLException e) {
            log.error("Error fetching patients from database: {}", e.getMessage());
        }
        return mainLabTests; // Returning the list, even if it's empty
    }


    //2. Create a Main Lab Test
    public MainLabTest createMainLabTest(MainLabTest mainLabTest) {
        String query = "INSERT INTO labtests (labTestName,labTestRemark) VALUES (?, ?)";

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, mainLabTest.getLabTestName());
            pstmt.setString(2, mainLabTest.getLabTestRemark());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        mainLabTest.setId(generatedKeys.getInt(1)); // Set the generated id to the patient object
                    }
                }
                return mainLabTest;
            }
        } catch (SQLException e) {
            log.debug("Error creating lab test: " + e.getMessage());
        }
        return null;
    }


    public boolean updateMainLabTest(MainLabTest mainLabTest) {
        log.info("Updating main lab test: {}", mainLabTest);
        String query = "UPDATE labtests SET labTestName = ?, labTestRemark = ? WHERE id = ?";

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, mainLabTest.getLabTestName());
            pstmt.setString(2, mainLabTest.getLabTestRemark());
            pstmt.setInt(3,mainLabTest.getId());
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;  // Returns true if the update was successful
        } catch (SQLException e) {
            log.info(e.getMessage());  // Log the exception message
        }
        return false;
    }

    public boolean deleteMainLabTests(int id) {

        String query = "DELETE FROM labtests WHERE id = ?";


        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();  // Execute the DELETE statement
            return affectedRows > 0;  // Return true if one or more rows were affected
        } catch (SQLException e) {
            log.debug(e.getMessage());  // Log the exception message
        }
        return false;  // Return false if there was an error or no rows were affected
    }

    public MainLabTest getMainLabTestById(int id) {
        log.debug("Fetching lab test with ID: {}", id);
        String query = "SELECT * FROM labtests WHERE id = ?";

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new MainLabTest(
                        rs.getInt("id"),
                        rs.getString("labTestName"),
                        rs.getString("labTestRemark")
                );
            }
        } catch (SQLException e) {
            log.error("Error fetching lab test from database: {}", e.getMessage());
        }
        return null;  // Return null if no lab test is found with the given ID
    }


    }

