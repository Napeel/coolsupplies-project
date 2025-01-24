package ca.mcgill.ecse.coolsupplies.features;

/* Project Imports */
import ca.mcgill.ecse.coolsupplies.application.*;
import ca.mcgill.ecse.coolsupplies.model.*;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet7Controller;

/* Cucumber Imports */
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/* JUnit Imports */
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

/* Helper Imports */
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class UpdateGradeStepDefinitions {
  private CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();

  /**
   * @author Kenny-Alexander Joseph
   * @param level the level that is going to be updated in the system
   * @param updatedLevel the new level that the old level is going to be updated to in the system
   * @return void
   * This gherkin step verifies that the first given level is updated with the second given level.
   */
  @When("the school admin attempts to update grade {string} in the system with level {string} \\(p13)")
  public void the_school_admin_attempts_to_update_grade_in_the_system_with_level_p13(String level,
      String updatedLevel) {
    callController(CoolSuppliesFeatureSet7Controller.updateGrade(level,updatedLevel));
  }

  /**
   * @author Trevor Piltch
   * @param level the level which should not exist in the system
   * @return void
   * This gherkin step verifies that the given level does not exist in the system.
   */
  @Then("the grade {string} shall not exist in the system \\(p13)")
  public void the_grade_shall_not_exist_in_the_system_p13(String level) {
    List<Grade> grades = coolSupplies.getGrades();
    List<String> levels = new ArrayList<>();

    for (int i = 0; i < grades.size(); i++) {
      levels.add(grades.get(i).getLevel());
    }

    assertFalse("Expected levels to not contain " + level, levels.contains(level));
  }

  /**
   * @author Trevor Piltch
   * @param grades the list of grades that should exist in the system
   * @return void
   * This gherkin step verifies that the list of grades exists in the system
   */
  @Then("the following grade entities shall exist in the system \\(p13)")
  public void the_following_grade_entities_shall_exist_in_the_system_p13(
      io.cucumber.datatable.DataTable grades) {

    List<Map<String, String>> entities = grades.asMaps();
    List<String> levels = new ArrayList<>();

    List<String> systemLevels = new ArrayList<>();

    for (var entity : entities) {
      String level = entity.get("level");
      levels.add(level);
    }

    for (var grade : coolSupplies.getGrades()) {
      systemLevels.add(grade.getLevel());
    }

    assertTrue("Expected levels: " + levels + " to exist in the system",
        systemLevels.containsAll(levels));
  }

  /* Helper Methods */

  /**
   * @author Trevor Piltch
   * @param result the error string coming from a controller call
   * @return void
   * Checks if the controller failed (i.e. returned a non-empty error string) and adds the string to the shared error object.
   */
  private void callController(String result) {
    if (!result.isEmpty()) {
      AddGradeStepDefinitions.error += result;
    }
  }
}

