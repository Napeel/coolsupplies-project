package ca.mcgill.ecse.coolsupplies.features;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ca.mcgill.ecse.coolsupplies.controller.TOGradeBundle;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet4Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GetGradeBundleStepDefinitions {
  private List<TOGradeBundle> bundleList;

  /**
   * @author Remy Laurendeau
   */
  @When("the school admin attempts to get from the system all the bundles \\(p9)")
  public void the_school_admin_attempts_to_get_from_the_system_all_the_bundles_p9() {
    bundleList = CoolSuppliesFeatureSet4Controller.getBundles();  // Call the controller to retrieve all grade
  }

  /**
   * @author Clara Stirling
   */
  @When("the school admin attempts to get from the system the grade bundle with name {string} \\(p9)")
  public void the_school_admin_attempts_to_get_from_the_system_the_grade_bundle_with_name_p9(String name) {
    TOGradeBundle bundle = CoolSuppliesFeatureSet4Controller.getBundle(name); // Attempt to retrieve the grade
                                                                              // bundle by the given name
                                                                              // from the CoolSupplies system
    bundleList = new ArrayList<>();
    if (bundle != null) {
      bundleList.add(bundle);
    }
  }

  /**
   * @author Ian Moore
   */
  @Then("the following grade bundle entities shall be presented \\(p9)")
  public void the_following_grade_bundle_entities_shall_be_presented_p9(io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> bundles = dataTable.asMaps(); // Convert the DataTable from the Gherkin step into a list
                                                            // of maps for easier processing

    assertEquals(bundles.size(), bundleList.size(), "Number of bundles does not match");

    for (var expectedBundle : bundles) { // Loop through rows

      String level = expectedBundle.get("gradeLevel");
      String name = expectedBundle.get("name");
      int discount = Integer.parseInt(expectedBundle.get("discount"));
      boolean equals = false; // Initialize the variable which checks if we have encountered a matching bundle
                              // to false

      for (TOGradeBundle actualBundle : bundleList) { // Loop through every bundle in our app
        // Check if the actual bundle's name, grade level, and discount match the
        // expected properties and set boolean value to equals
        equals = actualBundle.getName().equals(name) & actualBundle.getGradeLevel().equals(level)
                & (actualBundle.getDiscount() == discount);
        if (equals) { // Break loop if we have found a match
          break;
        }
      }

      assertTrue(equals, "One or more grades where not present"); // Assert that a matching grade bundle was
                                                                          // found.

    }

  }

  /**
   * @author Remy Laurendeau
   */
  @Then("no grade bundle entities shall be presented \\(p9)")
  public void no_grade_bundle_entities_shall_be_presented_p9() {
    // Assert that the `error` variable contains the Error message below
    // If false the message "At least one grade bundle entity is present" will be returned
    assertTrue(bundleList.isEmpty(), "At least one grade bundle entity is present");

  }

}
