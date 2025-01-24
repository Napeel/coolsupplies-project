package ca.mcgill.ecse.coolsupplies.features;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet7Controller;
import ca.mcgill.ecse.coolsupplies.controller.TOGrade;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Implementation of the Gherkin Step definition for the GetGrade feature in Coolsupplies by mapping the Gherkin step to Java code of the controller and the model layers
 * This class defines the getgrade step definitions that the school admin can perform concerning actions such as getting or retrieving grades
 *
 * @author David Zhou, David Vo, David Wang, Shayan Yamanidouzi Sorkhabi, Hamza Khalfi, Jun Ho Oh, Jack McDonald
 */

public class GetGradeStepDefinitions {


  List<TOGrade> resultGrades;
  /**
   * This step definition assigns thev list of grades in the application to resultGrades by using .getGrades()
   * the single grade resultGrade will be also set to null after retrieving all grades into the list
   *
   */

  @When("the school admin attempts to get from the system all the grades \\(p2)")
  public void the_school_admin_attempts_to_get_from_the_system_all_the_grades_p2() {
    // Write code here that turns the phrase above into concrete actions
    resultGrades = CoolSuppliesFeatureSet7Controller.getGrades();
  }


  /**
   * This step defintion will retrieves the grade with the level requested (inputted)
   * to do so, we create an empty list of grades where we add the grade with desired level
   * and we assign the grade with the level in question to resultGrade
   */


  @When("the school admin attempts to get from the system the grade with level {string} \\(p2)")
  public void the_school_admin_attempts_to_get_from_the_system_the_grade_with_level_p2(
      String gradeLevel) {
    // Write code here that turns the phrase above into concrete actions

    TOGrade resultGrade = CoolSuppliesFeatureSet7Controller.getGrade(gradeLevel);
    resultGrades = new ArrayList<>();
    if(resultGrade!=null){
      resultGrades.add(resultGrade);
    }



  }

  /**
   * This step definition will compare the expected grades with the actual grades.
   * First, it verifies the size of both list of expected and actual grades
   * then it iterates through the exepectedGrades and searches for the grades with the same level in the actualGrades array
   *
   */

  @Then("the following grade entities shall be presented \\(p2)")
  public void the_following_grade_entities_shall_be_presented_p2(
      io.cucumber.datatable.DataTable dataTable) {

    List<Map<String, String>> expectedGrades = dataTable.asMaps();

    assertEquals(expectedGrades.size(), resultGrades.size(), "Number of grades doesn't match");

    for (int i = 0; i < expectedGrades.size(); i++) {
      String expectedLevel = expectedGrades.get(i).get("level");
      TOGrade actualLevel = findGrade(resultGrades, expectedLevel);
      assertNotNull(actualLevel, "Expected Grade not found: " + expectedLevel);



    }
  }

  /**
   * This step definition checks if the requested grade is non existant or if the requested list of grades is empty
   * The list resultGrades should be empty and resultGrade should be null for the assertion to be successful
   *
   */

  @Then("no grade entities shall be presented \\(p2)")
  public void no_grade_entities_shall_be_presented_p2() {
    // Write code here that turns the phrase above into concrete actions
    assertTrue(resultGrades.isEmpty(), "Expected no grades, but found some");
  }

  /**
   * This helper method will help find the target grade by iterating through the list of grades (regardless of the order in the list)
   *
   * @param grades a list of grades
   * @param target the desired grade that we wish to return from the list of grades
   *
   * @return the desired target grade if it is found in the list of grades, else it returns null
   */

  public TOGrade findGrade(List<TOGrade> grades, String target) {
    for (TOGrade grade : grades) {
      if (target.equals(grade.getLevel())) {
        return grade;
      }
    }
    return null;
  }
}
