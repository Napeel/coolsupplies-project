package ca.mcgill.ecse.coolsupplies.features;

import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet4Controller;
import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;
import ca.mcgill.ecse.coolsupplies.model.GradeBundle;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

import java.util.List;

public class UpdateGradeBundleStepDefinitions {

  /**
   * @author adele
   */
  @When("the school admin attempts to update grade bundle {string} in the system with name {string}, discount {string}, and grade level {string} \\(p10)")
  public void the_school_admin_attempts_to_update_grade_bundle_in_the_system_with_name_discount_and_grade_level_p10( String name, String newName, String newDiscount,
  String newGradeLevel) {

      int convDiscount = Integer.parseInt(newDiscount);
      callController(CoolSuppliesFeatureSet4Controller.updateBundle(name, newName, convDiscount, newGradeLevel));

  }

  /**
   * @author Max Wu-Blouin
   */
  @Then("the grade bundle {string} with discount {string} and grade level {string} shall not exist in the system \\(p10)")
  public void the_grade_bundle_with_discount_and_grade_level_shall_not_exist_in_the_system_p10(
      String updatedName, String updatedDiscount, String updatedGradeLevel) {
    CoolSupplies aCoolSupplies = CoolSuppliesApplication.getCoolSupplies();
    List<GradeBundle> listOfBundles = aCoolSupplies.getBundles();
    boolean bundleExists = false;

    for(int i = 0; i < listOfBundles.size(); i++) {
      GradeBundle currentBundle = listOfBundles.get(i);
      String currentBundleName = currentBundle.getName();
      String currentBundleDiscount = String.valueOf(currentBundle.getDiscount());
      String currentBundleGradeLevel = currentBundle.getGrade().getLevel();
      if (currentBundleName.equals(updatedName) && currentBundleDiscount.equals(updatedDiscount) && currentBundleGradeLevel.equals(updatedGradeLevel)) {
        bundleExists = true;
        break;
      }
    }

    Assertions.assertFalse(bundleExists, "The bundle \"" + updatedName + "\" with discount " + updatedDiscount
            + " and grade level \"" + updatedGradeLevel + "\" should not exist in the system.");
  }

  /**
   * @author Steven Thao
   */
  @Then("the following grade bundle entities shall exist in the system \\(p10)")
  public void the_following_grade_bundle_entities_shall_exist_in_the_system_p10(
      io.cucumber.datatable.DataTable dataTable) {

    // Set up testing state
    CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();
    List<GradeBundle> actualBundles = coolSupplies.getBundles();
    List<List<String>> expectedBundles = dataTable.asLists(String.class);

    // Loop over each expected bundle, skipping headers
    for (int i = 1; i < expectedBundles.size(); i++) {
      List<String> expectedBundle = expectedBundles.get(i);

      boolean exists = false;  // Account for failure if bundle does not exist

      // Search for expected bundle
      for (GradeBundle gb : actualBundles) {
        if (gb.getName().equals(expectedBundle.get(0))) {
          exists = true;

          // Compare values
          int actualDiscount = gb.getDiscount();
          int expectedDiscount = Integer.parseInt(expectedBundle.get(1));
          Assertions.assertEquals(expectedDiscount, actualDiscount);

          String actualLevel = gb.getGrade().getLevel();
          String expectedLevel = expectedBundle.get(2);
          Assertions.assertEquals(expectedLevel, actualLevel);

          break;
        }
      }

      // Fail case if bundle wasn't found
      if (!exists) {
        Assertions.fail("The grade bundle \"" + expectedBundle.get(0) + "\" does not exist in the system.");
      }
    }
  }

  /** Calls controller and sets error and counter.
   * @author adele
   * */
  private void callController(String result) {
      if (!result.isEmpty()) {
          BundleTestUtility.setError(result);
      }
  }
}
