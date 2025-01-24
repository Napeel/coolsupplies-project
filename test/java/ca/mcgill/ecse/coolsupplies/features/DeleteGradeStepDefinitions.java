package ca.mcgill.ecse.coolsupplies.features;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import java.util.Map;
import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet7Controller;
import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import ca.mcgill.ecse.coolsupplies.model.Grade;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Implementation of the Gherkin Step definition for the DeleteGrade feature in CoolSupplies by mapping the Gherkin step to Java code of the controller and the model layers
 * This class defines the deletegrade step definitions that the school admin can perform concerning deletion of grades such as
 * verifying existence or deleting grades
 *
 * @author David Zhou, David Vo, David Wang, Shayan Yamanidouzi Sorkhabi, Hamza Khalfi, Jun Ho Oh, Jack McDonald
 */


public class DeleteGradeStepDefinitions {
  private String lastError;
  private static CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();

  /**
   * the step definition adds teh assumed to be existant grades to an instance of the application
   *
   */

  @Given("the following grade entities exists in the system \\(p2)")
  public void the_following_grade_entities_exists_in_the_system_p2(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();
    for (var elem : rows) {
      String level = elem.get("level");
      // Assuming a method addGrade in the controller adds grades to the system.
      coolSupplies.addGrade(level);
    }
  }

  /**
   * this step definition will delete a grade from the application using the controller method. If an error occurs, it is stored into lastError
   */

  @When("the school admin attempts to delete from the system the grade with level {string} \\(p2)")
  public void the_school_admin_attempts_to_delete_from_the_system_the_grade_with_level_p2(
      String gradeLevel) {
    lastError = CoolSuppliesFeatureSet7Controller.deleteGrade(gradeLevel);
  }

  /**
   * This controler method will compare the number of grades in the appllication with the expected number.
   */

  @Then(value = "the number of grade entities in the system shall be {string} \\(p2)")
  public void the_number_of_grade_entities_in_the_system_shall_be_p2(String numberOfGrades) {

    int numGrades = Integer.parseInt(numberOfGrades);
    System.out.println(coolSupplies.getGrades().size());
    assertEquals(coolSupplies.getGrades().size(), numGrades);
  }

  /**
   * This step definition verifies whether the expected grades exist in the application.
   * It does so by iterating through the expected grades and comparing with the actual ones
   */

  @Then("the following grade entities shall exist in the system \\(p2)")
  public void the_following_grade_entities_shall_exist_in_the_system_p2(
      io.cucumber.datatable.DataTable dataTable) {

    List<Map<String, String>> expectedGrades = dataTable.asMaps();
    List<Grade> actualGrades = coolSupplies.getGrades();
    for (int i = 0; i < expectedGrades.size(); i++) {
      String expectedLevel = expectedGrades.get(i).get("level");
      Grade actualGrade = findGrade(actualGrades, expectedLevel);
      assertNotNull(actualGrade, "Expected grade with level '" + expectedLevel + "' was not found in the system.");
      assertEquals(expectedLevel, actualGrade.getLevel(), "Mismatch in grade level. Expected '" + expectedLevel +
          "', but found '" + actualGrade.getLevel() + "'.");
    }
  }

  /**
   * This step definition chack if the raised error message matches the expected error message
   *
   */

  @Then("the error {string} shall be raised \\(p2)")
  public void the_error_shall_be_raised_p2(String error) {
    // Write code here that turns the phrase above into concrete actions
    assertEquals(error, lastError);
  }

  /**
   * This helper method will help find the target grade by iterating through the list of grades (regardless of the order in the list)
   *
   * @param grades a list of grades
   * @param target the desired grade that we wish to return from the list of grades
   *
   * @return the desired target grade if it is found in the list of grades, else it returns null
   */

  public Grade findGrade(List<Grade> grades, String target) {
    for (Grade grade : grades) {
      if (target.equals(grade.getLevel())) {
        return grade;
      }
    }
    return null;
  }
}
