package ca.mcgill.ecse.coolsupplies.features;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet6Controller;
import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;
import ca.mcgill.ecse.coolsupplies.model.Grade;
import ca.mcgill.ecse.coolsupplies.model.Parent;
import ca.mcgill.ecse.coolsupplies.model.Student;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AddStudentToStudentsOfParentStepDefinitions {

  private CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();
  static String lastError = "";

  /**
   * @author Kathelina Wei
   */
  @Given("the following parent entities exists in the system \\(p16)")
  public void the_following_parent_entities_exists_in_the_system_p16(
      io.cucumber.datatable.DataTable dataTable) {

    // Convert the data table into a list of maps where each map represents a row of data.
    List<Map<String, String>> rows = dataTable.asMaps();

    for (var row : rows) {
      String email = row.get("email");
      String password = row.get("password");
      String name = row.get("name");
      int phone = Integer.parseInt(row.get("phoneNumber"));

      coolSupplies.addParent(email, password, name, phone);
    }
  }

  /**
   * @author Kathelina Wei
   */
  @Given("the following grade entities exists in the system \\(p16)")
  public void the_following_grade_entities_exists_in_the_system_p16(
      io.cucumber.datatable.DataTable dataTable) {

    List<Map<String, String>> rows = dataTable.asMaps();

    for (var row : rows) {
      String gradeLevel = row.get("gradeLevel");

      coolSupplies.addGrade(gradeLevel);
    }
  }

  /**
   * @author Kathelina Wei
   */
  @Given("the following student entities exists in the system \\(p16)")
  public void the_following_student_entities_exists_in_the_system_p16(
      io.cucumber.datatable.DataTable dataTable) {

    List<Map<String, String>> rows = dataTable.asMaps();

    for (var row : rows) {

      String name = row.get("name");
      String gradeLevel = row.get("gradeLevel");
      Grade studentGrade;

      if (Grade.hasWithLevel(gradeLevel)) { // Checking if grade exists
        studentGrade = Grade.getWithLevel(gradeLevel);
      } else {
        studentGrade = new Grade(gradeLevel, coolSupplies);
        coolSupplies.addGrade(studentGrade);
      }

      coolSupplies.addStudent(name, studentGrade);
    }
  }

  /**
   * @author Nizar Azmi
   */
  @When("the parent attempts to add a student with name {string} to an existing parent {string} \\(p16)")
  public void the_parent_attempts_to_add_a_student_with_name_to_an_existing_parent_p16(
      String studentName, String parentEmail) {

    lastError = CoolSuppliesFeatureSet6Controller.addStudentToParent(studentName, parentEmail);
  }

  /**
   * @author Nizar Azmi
   */
  @Then("the number of student entities in the system shall be {string} \\(p16)")
  public void the_number_of_student_entities_in_the_system_shall_be_p16(String expectedCountStr) {

    int expectedCount = Integer.parseInt(expectedCountStr);
    int actualCount = coolSupplies.getStudents().size(); // Get the total count of
                                                         // coolSupplies.student
    assertEquals(expectedCount, actualCount);
  }

  /**
   * @author Jad El Hachem
   */
  @Then("the number of student entities for parent {string} in the system shall be {string} \\(p16)")
  public void the_number_of_student_entities_for_parent_in_the_system_shall_be_p16(
      String parentEmail, String expectedCount) {
    Parent parent = (Parent) Parent.getWithEmail(parentEmail);
    int Expected = Integer.parseInt(expectedCount);
    int Actual = parent.getStudents().size(); // Get the number of elements in parent.student
    assertEquals(Expected, Actual);
  }

  /**
   * @author Tingyi Chen
   */
  @Then("the student {string} shall not exist for parent {string} in the system \\(p16)")
  public void the_student_shall_not_exist_for_parent_in_the_system_p16(String studentName,
      String parentEmail) {
    Parent parent = (Parent) Parent.getWithEmail(parentEmail);
    Student student = Student.getWithName(studentName);
    boolean hasStudent = parent.getStudents().contains(student); // Shall not exist meaning this
                                                                 // should return false
    assertFalse(hasStudent);
  }

  /**
   * @author Alexander Fou
   */
  @Then("the error {string} shall be raised \\(p16)")
  public void the_error_shall_be_raised_p16(String errormsg) {
    assertEquals(errormsg, lastError);
  }

  /**
   * @author Tingyi Chen
   */
  @Then("the student {string} shall exist for parent {string} in the system \\(p16)")
  public void the_student_shall_exist_for_parent_in_the_system_p16(String studentName,
      String parentEmail) {
    Parent parent = (Parent) Parent.getWithEmail(parentEmail);
    Student student = Student.getWithName(studentName);
    boolean hasStudent = parent.getStudents().contains(student); // Shall exist meaning this should
                                                                 // return true
    assertTrue(hasStudent);
  }
}
