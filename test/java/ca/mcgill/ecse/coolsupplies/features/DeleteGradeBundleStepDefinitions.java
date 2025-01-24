package ca.mcgill.ecse.coolsupplies.features;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;

import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.model.GradeBundle;
import ca.mcgill.ecse.coolsupplies.model.Grade;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet4Controller;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class DeleteGradeBundleStepDefinitions {

  private String error = "";

  /**
   * @author Clementine Ruiz
   */
  @Given("the following grade entities exists in the system \\(p9)")
  public void the_following_grade_entities_exists_in_the_system_p9(io.cucumber.datatable.DataTable dataTable) {
    error = ""; // Set error to empty
    List<Map<String, String>> rows = dataTable.asMaps(); // Convert the DataTable from the Gherkin step into a list
                                                         // of maps for easier processing
    for (var row : rows) { // Loop through each row in the DataTable
      String level = row.get("level"); // For each row of the column "level" we extract the value in the row
      CoolSuppliesApplication.getCoolSupplies().addGrade(level); // Add a grade with the level defined above to
                                                                 // our application
    }
  }

  /**
   * @author Clara Stirling
   */
  @Given("the following grade bundle entities exists in the system \\(p9)")
  public void the_following_grade_bundle_entities_exists_in_the_system_p9(io.cucumber.datatable.DataTable dataTable) {
    error = ""; // Set error to empty

    // Convert the DataTable from the Gherkin step into a list of maps for easier
    // processing
    List<Map<String, String>> rows = dataTable.asMaps();

    for (var row : rows) { // Loop through each row in the DataTable
      Grade gradeLevel = Grade.getWithLevel(row.get("gradeLevel"));
      String name = row.get("name"); // extract value in said row from column name
      int discount = Integer.parseInt(row.get("discount")); // extract int in said row from column discount
      CoolSuppliesApplication.getCoolSupplies().addBundle(name, discount, gradeLevel); // Add a bundle with the
                                                                                       // details defined
                                                                                       // above to our application
    }
  }

  /**
   * @author Panayiotis Saropoulos
   */
  @When("the school admin attempts to delete from the system the gradeBundle with name {string} \\(p9)")
  public void the_school_admin_attempts_to_delete_from_the_system_the_grade_bundle_with_name_p9(String bundleName) {
    callController(CoolSuppliesFeatureSet4Controller.deleteBundle(bundleName)); // Call the controller method to
                                                                                // delete the grade bundle by name
  }

  /**
   * @author Maxime Courchesne
   */
  @Then("the number of grade bundle entities in the system shall be {string} \\(p9)")
  public void the_number_of_grade_bundle_entities_in_the_system_shall_be_p9(String expectedNumber) {

    // Convert the input to an int
    int expectedNumberOfBundles = Integer.parseInt(expectedNumber);

    // Get the number of bundles from the app
    int actualNumberOfBundles = CoolSuppliesApplication.getCoolSupplies().getBundles().size();
    // Assert whether the expectedNumberOfBundles is equal to the actualNumberOfBundles
    assertEquals(expectedNumberOfBundles, actualNumberOfBundles,
            "Expected " + expectedNumberOfBundles + " bundles, but found " + actualNumberOfBundles);
  }

  /**
   * @author Logan Ma
   */
  @Then("the following grade bundle entities shall exist in the system \\(p9)")
  public void the_following_grade_bundle_entities_shall_exist_in_the_system_p9(
          io.cucumber.datatable.DataTable dataTable) {
    // Convert the DataTable from the Gherkin step into a list of maps for easier processing;
    List<Map<String, String>> expectedGradeBundles = dataTable.asMaps(String.class, String.class);

    // Retrieve the current list of grade bundles from the system
    List<GradeBundle> actualGradeBundles = CoolSuppliesApplication.getCoolSupplies().getBundles();

    for (Map<String, String> expectedBundle : expectedGradeBundles) { // Loop through each bundle
      String name = expectedBundle.get("name"); // Extract name, discount, and grade level of each expected bundle
                                                // from the data table
      int discount = Integer.parseInt(expectedBundle.get("discount"));
      String gradeLevel = expectedBundle.get("gradeLevel");

      // Check whether a grade bundle with such properties exists in the system
      boolean exists = actualGradeBundles.stream().anyMatch(bundle -> bundle.getName().equals(name)
              && bundle.getDiscount() == discount && bundle.getGrade().getLevel().equals(gradeLevel));

      assertTrue(exists, "Expected grade bundle entity with name '" + name + "', discount '" + discount
              + "', and grade level '" + gradeLevel + "' does not exist in the system."); // Assert whether the
                                                                                          // expected bundle
                                                                                          // exists in the system
    }
  }

  /**
   * @author Ian Moore
   */
  @Then("the error {string} shall be raised \\(p9)")
  public void the_error_shall_be_raised_p9(String expectedError) {

    // assert whether the error variable is equal to given expectedError string
    assertEquals(expectedError, error, "Expected error: '" + expectedError + "', but got: '" + error + "'.");
  }

  /**
   * Calls controller and sets error and counter.
   */
  private void callController(String result) {
    if (!result.isEmpty()) {
      error = result;
    }
  }
}