package ca.mcgill.ecse.coolsupplies.features;

import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.controller.*;
import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;
import ca.mcgill.ecse.coolsupplies.model.Grade;
import ca.mcgill.ecse.coolsupplies.model.Student;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.BeforeAll;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class DeleteStudentStepDefinitions {

  private CoolSupplies coolSupplies=CoolSuppliesApplication.getCoolSupplies();
  private String error;
  private int errorCntr;

  /**
   * @author Doddy Yang Qiu
   */
  @Given("the following grade entities exists in the system \\(p5)")
  public void the_following_grade_entities_exists_in_the_system_p5(
      io.cucumber.datatable.DataTable dataTable) {

    List<Map<String, String>> rows = dataTable.asMaps(); // Get datatable as a list of Strings
    for (var row : rows) {
      String gradeLevel = row.get("level");
      coolSupplies.addGrade(gradeLevel);
    }

  }

  /**
   * @author Doddy Yang Qiu
   */
  @Given("the following student entities exists in the system \\(p5)")
  public void the_following_student_entities_exists_in_the_system_p5(
      io.cucumber.datatable.DataTable dataTable) {

    List<Map<String, String>> studentTable = dataTable.asMaps();
    for (var student : studentTable) {
      String name = student.get("name");
      String gradeLevel = student.get("gradeLevel");
      Grade studentGrade = Grade.getWithLevel(gradeLevel);
      coolSupplies.addStudent(name, studentGrade);
    }
  }

  /**
   * @author Brian Yang
   */
  @When("the school admin attempts to delete from the system the student with name {string} \\(p5)")
  public void the_school_admin_attempts_to_delete_from_the_system_the_student_with_name_p5(
      String studentName) {
    callController(CoolSuppliesFeatureSet2Controller.deleteStudent(studentName));
  }

  /**
   * @author Brian Yang
   */
  @Then("the number of student entities in the system shall be {string} \\(p5)")
  public void the_number_of_student_entities_in_the_system_shall_be_p5(String numOfStudent) {
    int number = Integer.parseInt(numOfStudent);
    assertEquals(number, coolSupplies.getStudents().size());
  }

  /**
   * @author Brian Yang
   */
  @Then("the following student entities shall exist in the system \\(p5)")
  public void the_following_student_entities_shall_exist_in_the_system_p5(
      io.cucumber.datatable.DataTable dataTable) {
    
    List<Map<String, String>> rows = dataTable.asMaps();
    for (var row : rows) {
      String name = row.get("name");
      String gradeLevel = row.get("gradeLevel");
      Student student = Student.getWithName(name);

      if (student != null && gradeLevel != null) {
        assertEquals(gradeLevel,student.getGrade().getLevel());
      }
    }
  }

  /**
   * @author Brian Yang
   */
  @Then("the error {string} shall be raised \\(p5)")
  public void the_error_shall_be_raised_p5(String errorMessage) {
    assertTrue(error.contains(errorMessage), "Expected error message '" 
    + errorMessage + "' not found in: " + error);
  }

  /** Calls controller and sets error and error counter **/
  private void callController(String result) {
    if (!result.isEmpty()) {
      error += result;
      errorCntr += 1;
    }
  }
}
