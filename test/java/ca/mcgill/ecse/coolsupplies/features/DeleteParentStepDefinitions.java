package ca.mcgill.ecse.coolsupplies.features;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet1Controller;
import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;
import ca.mcgill.ecse.coolsupplies.model.Parent;
import ca.mcgill.ecse.coolsupplies.model.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Step definitions for the DeleteParent feature in the CoolSupplies application.
 *
 * @author Mary Li
 * @author Artimice Mirchi
 * @author Jyothsna Seema
 */
public class DeleteParentStepDefinitions {

  // Instance of the CoolSupplies application model
  private CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();

  // Variable to hold any error messages encountered during operations
  private String error = "";

  @Given("the following parent entities exists in the system \\(p8)")
  public void the_following_parent_entities_exists_in_the_system_p8(
      io.cucumber.datatable.DataTable parentDataTable) {
    List<Map<String, String>> rows = parentDataTable.asMaps();
    for (var row : rows) {
      // Extract parent details from the data table
      String email = row.get("email");
      String password = row.get("password");
      String name = row.get("name");
      int phoneNumber = Integer.parseInt(row.get("phoneNumber"));

      // Add the parent to the CoolSupplies application
      coolSupplies.addParent(email, password, name, phoneNumber);
    }
  }

  @When("the school Administrator attempts to delete from the system the parent with email {string} \\(p8)")
  public void the_school_administrator_attempts_to_delete_from_the_system_the_parent_with_email_p8(
      String parentEmail) {
    // Call the controller to delete the parent and capture any error messages
    error = CoolSuppliesFeatureSet1Controller.deleteParent(parentEmail);
  }

  @Then("the number of parent entities in the system shall be {string} \\(p8)")
  public void the_number_of_parent_entities_in_the_system_shall_be_p8(String expectedParentCount) {
    // Get the current number of parents in the system
    int numberOfParents = coolSupplies.getParents().size();

    // Assert that the number of parents matches the expected count
    assertEquals(Integer.parseInt(expectedParentCount), numberOfParents,
            "Expected" + expectedParentCount + " parent(s), but found " + numberOfParents + ".");
  }

  @Then("the following parent entities shall exist in the system \\(p8)")
  public void the_following_parent_entities_shall_exist_in_the_system_p8(
      io.cucumber.datatable.DataTable expectedParentDataTable) {
    List<Map<String, String>> rows = expectedParentDataTable.asMaps();
    for (var row : rows) {
      // Extract expected parent details
      String email = row.get("email");
      String password = row.get("password");
      String name = row.get("name");
      int phoneNumber = Integer.parseInt(row.get("phoneNumber"));

      // Retrieve the parent by email
      Parent parent = (Parent) User.getWithEmail(email);

      // Assert that the parent exists and their details match the expected values
      assertNotNull(parent, "Parent with email " + email + " not found.");
      assertEquals(password, parent.getPassword(),
              "Incorrect password for parent with email " + email + ".");
      assertEquals(name, parent.getName(),
              "Incorrect name for parent with email " + email + ".");
      assertEquals(phoneNumber, parent.getPhoneNumber(),
              "Incorrect phone number for parent with email " + email + ".");
    }
  }

  @Then("the error {string} shall be raised \\(p8)")
  public void the_error_shall_be_raised_p8(String expectedError) {
    // Assert that the error matches the expected error message
    assertEquals(expectedError, error, "Expected error: '" + expectedError + "', but got: '" + error + "'.");
  }
}
