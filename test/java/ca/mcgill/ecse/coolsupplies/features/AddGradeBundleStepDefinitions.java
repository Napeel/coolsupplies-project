package ca.mcgill.ecse.coolsupplies.features;

import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet4Controller;
import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;
import ca.mcgill.ecse.coolsupplies.model.Grade;
import ca.mcgill.ecse.coolsupplies.model.GradeBundle;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

import java.util.List;

public class AddGradeBundleStepDefinitions {

  private CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();

  /**
   * @author Maximilian Bergmair
   */
  @Given("the following grade entities exists in the system \\(p10)")
  public void the_following_grade_entities_exists_in_the_system_p10(
      io.cucumber.datatable.DataTable dataTable) {
    List<String> gradeLevels = dataTable.asList(String.class);
    for (int i = 1; i < gradeLevels.size(); i++) {
      new Grade(gradeLevels.get(i), coolSupplies);
    }
  }

  /**
   * @author Maximilian Bergmair
   */
  @Given("the following grade bundle entities exists in the system \\(p10)")
  public void the_following_grade_bundle_entities_exists_in_the_system_p10(
      io.cucumber.datatable.DataTable dataTable) {
    List<List<String>> gradeBundles = dataTable.asLists(String.class);

    // Skip index zero since it contains column headers
    for (int i = 1; i < gradeBundles.size(); i++) {
      List<String> curData = gradeBundles.get(i);
      String name = curData.get(0);
      int discount = Integer.parseInt(curData.get(1));
      Grade grade = Grade.getWithLevel(curData.get(2));
      new GradeBundle(name, discount, coolSupplies, grade);
    }
  }

  /**
   *
   * @param name
   * @param discount
   * @param gradeLevel
   * @author Tal Smith
   */
  @When("the school admin attempts to add a new grade bundle in the system with name {string}, discount {string}, and grade level {string} \\(p10)")
  public void the_school_admin_attempts_to_add_a_new_grade_bundle_in_the_system_with_name_discount_and_grade_level_p10(
      String name, String discount, String gradeLevel) {

      int discountValue = Integer.parseInt(discount);
      String result = CoolSuppliesFeatureSet4Controller.addBundle(name, discountValue, gradeLevel);
      BundleTestUtility.setError(result);
  }

  /**
   *
   * @param bundle
   * @param discount
   * @param gradeLevel
   * @author Tal Smith
   */
  @Then("the grade bundle {string} with discount {string} and grade level {string} shall exist in the system \\(p10)")
  public void the_grade_bundle_with_discount_and_grade_level_shall_exist_in_the_system_p10(
      String bundle, String discount, String gradeLevel) {

    int discountValue = Integer.parseInt(discount);
    boolean hasBundle = false;

    for (GradeBundle b : coolSupplies.getBundles() ){
      if (b.getName().equals(bundle) && b.getGrade().getLevel().equals(gradeLevel)
       && Integer.valueOf(b.getDiscount()).equals(discountValue)) {
        hasBundle = true;
        break;
      }
    }

    Assertions.assertTrue(hasBundle, "Bundle with name \"" + bundle + "\", discount of " + discount
            + ", and grade level \"" + gradeLevel + "\" should exist in the system");
  }

  /**
   * @author Jeremias Z
   * @param numOfGradeBundles string of the number of grade bundle entities in system
   */
  @Then("the number of grade bundle entities in the system shall be {string} \\(p10)")
  public void the_number_of_grade_bundle_entities_in_the_system_shall_be_p10(String numOfGradeBundles) {
    int actualGradeBundles = coolSupplies.getBundles().size();
    int expectedGradeBundles = Integer.parseInt(numOfGradeBundles);
    Assertions.assertEquals(expectedGradeBundles, actualGradeBundles);
  }

  /**
   * Verifies that the actual error message matches the expected one.
   * @param expectedError String of the expected error message that should be raised.
   * @author Matthew Eiley
   */
  @Then("the error {string} shall be raised \\(p10)")
  public void the_error_shall_be_raised_p10(String expectedError) {
    String error = BundleTestUtility.getError();
    Assertions.assertEquals(expectedError, error, "Expected to see error message: " + expectedError + "\nBut got the message: " + error);
  }
}
