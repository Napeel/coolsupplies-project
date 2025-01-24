package ca.mcgill.ecse.coolsupplies.features;

/* Project Imports */
import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet7Controller;
import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;
import ca.mcgill.ecse.coolsupplies.model.Grade;

/* Cucumber Imports */
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/* JUnit Imports */
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.Assert.assertTrue;

/* Helper Imports */
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AddGradeStepDefinitions {
  private CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();
  public static String error = "";

  /**
   * This test attemps to verify that the given grade entities exists in the system
   * @author Lune Letailleur
   * @param DataTable represents the grade entities we wish to exist in the system
   * @return void
   */
  @Given("the following grade entities exists in the system \\(p13)")
  public void the_following_grade_entities_exists_in_the_system_p13(
      io.cucumber.datatable.DataTable grades) {
    List<Map<String, String>> entities = grades.asMaps();

    for (var entity : entities) {
      String level = entity.get("level");
      coolSupplies.addGrade(level);
    }
  }

  /**
   * This test attempts to add a new grade in the system and intercepts any error that might occur
   * @author Clara Dupuis
   * @param level represents the name of the grade that we wish to add to the system
   * @return void
   */
  @When("the school admin attempts to add a new grade in the system with level {string} \\(p13)")
  public void the_school_admin_attempts_to_add_a_new_grade_in_the_system_with_level_p13(
      String level) {
    callController(CoolSuppliesFeatureSet7Controller.addGrade(level));
    
  }

  /**
   * Tests if the number of grades in the system is correct
   * @author Dimitri Christopoulos
   * @param expectedNumberOfGrades What the number of grades in the system is supposed to be
   * @return void
   * @throws AssertionError If the number of grades in the system does not match the expected value
   */
  @Then("the number of grade entities in the system shall be {string} \\(p13)")
  public void the_number_of_grade_entities_in_the_system_shall_be_p13(String expectedNumberOfGrades) {
    // Get number of grades
    List<Grade> grades = coolSupplies.getGrades();
    int numGrades = grades.size();

    // Test case
    assertEquals(Integer.parseInt(expectedNumberOfGrades), numGrades);
  }

  /**
   *verifies if the expected error message matches the error message in the log
   * @author Nil Akkurt
   * @param errorMessage
   * @return void 
   * @throws AssertionError if the error message is not found in the error log
   */
  @Then("the error {string} shall be raised \\(p13)")
  public void the_error_shall_be_raised_p13(String errorMessage) {
    assertTrue("Expected error to contain " + errorMessage + " but was: " + error,
        error.contains(errorMessage));
  }

  /**
   * This test verifies if a specific grade exists in the system
   * @author Edouard Dupont
   * @param level is a specific grade we want to check
   * @return void
   */
  @Then("the grade {string} shall exist in the system \\(p13)")
  public void the_grade_shall_exist_in_the_system_p13(String level) {
    List<Grade> grades = coolSupplies.getGrades();
    List<String>  levels = new ArrayList<>();

    for (int i = 0; i < grades.size(); i++){
      levels.add(grades.get(i).getLevel());
    }

    assertTrue("Expected levels to contain " + level, levels.contains(level));
  }

  /**
   * This test verifies if there is an error and if so, appends the error to the error string
   * @author Clara Dupuis
   * @param result The result string returned by the controller. It will be empty if there is no error
   * return void
   */
  private void callController(String result){
    if (!result.isEmpty()){
      error += result;
    }
  }
}
