package com.kaasmipathology.pathtests;

import com.kaasmipathology.util.DBConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComponentLabTestService {

    private static final Logger log = LoggerFactory.getLogger(ComponentLabTestService.class);

    // 1. Get all Component Lab Tests
    public List<ComponentLabTest> getAllComponentLabTests() {
        log.debug("Getting Component Lab Test List!");
        List<ComponentLabTest> componentLabTests = new ArrayList<>();
        String query = "SELECT * FROM component_lab_tests";

        try (Connection connection = DBConnection.getInstance().getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                ComponentLabTest componentLabTest = new ComponentLabTest(
                        rs.getInt("id"),
                        rs.getInt("labTestId"),
                        rs.getString("componentName"),
                        rs.getString("componentUnit"),
                        rs.getString("componentRefRange")
                );
                componentLabTests.add(componentLabTest);
            }
        } catch (SQLException e) {
            log.error("Error fetching component lab tests from database: {}", e.getMessage());
        }
        return componentLabTests; // Return the list, even if it's empty
    }

    // 2. Get Components by Lab Test ID

    public List<ComponentLabTest> getComponentsByLabTestId(int labTestId) {
        log.debug("Getting Component Lab Tests for Lab Test ID: {}", labTestId);
        List<ComponentLabTest> componentLabTests = new ArrayList<>();
        String query = "SELECT * FROM component_lab_tests WHERE labTestId = ?";

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setInt(1, labTestId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    ComponentLabTest componentLabTest = new ComponentLabTest(
                            rs.getInt("id"),
                            rs.getInt("labTestId"),
                            rs.getString("componentName"),
                            rs.getString("componentUnit"),
                            rs.getString("componentRefRange")
                    );
                    componentLabTests.add(componentLabTest);
                }
            }
        } catch (SQLException e) {
            log.error("Error fetching components by lab test ID from database: {}", e.getMessage());
        }
        return componentLabTests; // Return the list, even if it's empty
    }

    // Create Lab Test Component
    public ComponentLabTest createComponentLabTest2(ComponentLabTest componentLabTest) {
        log.info("Attempting to create component lab test with labTestId: {}", componentLabTest.getLabTestId());

        // Check if the labTestId exists before proceeding
        if (!labTestExists(componentLabTest.getLabTestId())) {
            log.error("labTestId {} does not exist, cannot create component lab test", componentLabTest.getLabTestId());
            throw new IllegalArgumentException("labTestId does not exist");
        }

        String query = "INSERT INTO component_lab_tests (labTestId, componentName, componentUnit, componentRefRange) VALUES (?, ?, ?, ?)";

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, componentLabTest.getLabTestId());  // Set the foreign key to MainLabTest
            pstmt.setString(2, componentLabTest.getComponentName());
            pstmt.setString(3, componentLabTest.getComponentUnit());
            pstmt.setString(4, componentLabTest.getComponentRefRange());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        componentLabTest.setId(generatedKeys.getInt(1)); // Set the generated ID
                    }
                }
                log.info("Successfully created component lab test with ID: {}", componentLabTest.getId());
                return componentLabTest; // Return the created component
            } else {
                log.warn("No rows affected, component lab test was not created.");
            }
        } catch (SQLException e) {
            log.error("Error creating component lab test: {}", e.getMessage());
        }
        return null; // Return null if creation failed
    }

    // Method to check if labTest exists
    private boolean labTestExists(int labTestId) {
        String query = "SELECT COUNT(*) FROM labtests WHERE id = ?";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, labTestId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Return true if count is greater than 0
            }
        } catch (SQLException e) {
            log.error("Error checking if labTest exists: {}", e.getMessage());
        }
        return false; // Return false if there was an error or count was 0
    }


    // 4. Update a Component Lab Test
    public boolean updateComponentLabTest(ComponentLabTest componentLabTest) {
        log.info("Updating component lab test: {}", componentLabTest);
        String query = "UPDATE component_lab_tests SET componentName = ?, componentUnit = ?, componentRefRange = ? WHERE id = ?";

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, componentLabTest.getComponentName());
            pstmt.setString(2, componentLabTest.getComponentUnit());
            pstmt.setString(3, componentLabTest.getComponentRefRange());
            pstmt.setInt(4, componentLabTest.getId());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;  // Return true if the update was successful
        } catch (SQLException e) {
            log.error("Error updating component lab test: {}", e.getMessage());
        }
        return false;
    }

    // 5. Delete a Component Lab Test by ID
    public boolean deleteComponentLabTest(int id) {
        String query = "DELETE FROM component_lab_tests WHERE id = ?";

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, id);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;  // Return true if one or more rows were affected
        } catch (SQLException e) {
            log.error("Error deleting component lab test: {}", e.getMessage());
        }
        return false;  // Return false if there was an error or no rows were affected
    }
}
