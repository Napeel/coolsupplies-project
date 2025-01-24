package ca.mcgill.ecse.coolsupplies.features;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import java.util.Map;
import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.controller.*;
import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;
import ca.mcgill.ecse.coolsupplies.model.Grade;
import ca.mcgill.ecse.coolsupplies.model.Student;

/**
 * Step definition class for the "Add Student" feature. This class defines the Gherkin steps used in
 * the feature file and connects them with the underlying system logic to add students.
 * 
 * @author Alexander Kudinov
 */
public class AddStudentStepDefinitions {

  CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();

  /**
   * Gherkin step definition to ensure that the provided grade entities exist in the system.
   * 
   * @param dataTable The DataTable containing grade information to be created.
   */
  @Given("the following grade entities exists in the system \\(p4)")
  public void the_following_grade_entities_exists_in_the_system_p4(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

    for (Map<String, String> row : rows) {
      String level = row.get("level");
      new Grade(level, coolSupplies);
    }
  }

  /**
   * Gherkin step definition to ensure that the provided student entities exist in the system.
   * 
   * @param dataTable The DataTable containing student names and grade levels to be created.
   */
  @Given("the following student entities exists in the system \\(p4)")
  public void the_following_student_entities_exists_in_the_system_p4(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

    for (Map<String, String> row : rows) {
      String studentName = row.get("name");
      String studentGradeLevel = row.get("gradeLevel");
      Grade grade = Grade.getWithLevel(studentGradeLevel);
      new Student(studentName, coolSupplies, grade);
    }
  }

  /**
   * Gherkin step definition for adding a new student with a specified name and grade level.
   * 
   * @param studentName The name of the student to be added.
   * @param studentGradeLevel The grade level of the student.
   */
  @When("the school admin attempts to add a new student in the system with name {string} and grade level {string} \\(p4)")
  public void the_school_admin_attempts_to_add_a_new_student_in_the_system_with_name_and_grade_level_p4(
      String studentName, String studentGradeLevel) {
    String result = CoolSuppliesFeatureSet2Controller.addStudent(studentName, studentGradeLevel);
    TestUtility.setError(result);
  }

  /**
   * Gherkin step definition for adding a new student with a specified name and grade level, using
   * the term "administrator" instead of "admin".
   * 
   * @param studentName The name of the student to be added.
   * @param studentGradeLevel The grade level of the student.
   */
  @When("the school administrator attempts to add a new student in the system with name {string} and grade level {string} \\(p4)")
  public void the_school_administrator_attempts_to_add_a_new_student_in_the_system_with_name_and_grade_level_p4(
      String studentName, String studentGradeLevel) {
    String result = CoolSuppliesFeatureSet2Controller.addStudent(studentName, studentGradeLevel);
    TestUtility.setError(result);
  }

  /**
   * Asserts that the number of student entities in the system matches the expected count.
   * 
   * @param expectedCount The expected number of students.
   * @param actualCount The actual number of students in the system.
   */
  public static void assertStudentCount(int expectedCount, int actualCount) {
    assertEquals(expectedCount, actualCount,
        "Expected number of students did not match the actual number.");
  }


  /**
   * Gherkin step definition to verify the total number of student entities in the system.
   * 
   * @param expectedStudentCount The expected number of students in the system.
   */
  @Then("the number of student entities in the system shall be {string} \\(p4)")
  public void the_number_of_student_entities_in_the_system_shall_be_p4(
      String expectedStudentCount) {
    List<Student> students = coolSupplies.getStudents();
    assertStudentCount(Integer.parseInt(expectedStudentCount), students.size());
  }

  /**
   * Gherkin step definition to verify that a specific error message was raised during the execution
   * of a previous step.
   * 
   * @param expectedErrorMessage The expected error message to be raised.
   */
  @Then("the error {string} shall be raised \\(p4)")
  public void the_error_shall_be_raised_p4(String expectedErrorMessage) {
    String actualError = TestUtility.getError();
    TestUtility.assertErrorRaised(expectedErrorMessage, actualError);
  }

  /**
   * Asserts that a student exists in the system with the expected name and grade level.
   * 
   * @param expectedName The expected name of the student.
   * @param expectedGradeLevel The expected grade level of the student.
   * @param actualName The actual name of the student found in the system.
   * @param actualGradeLevel The actual grade level of the student found in the system.
   */
  public static void assertStudentExists(String expectedName, String expectedGradeLevel,
      String actualName, String actualGradeLevel) {
    assertNotNull(actualName, "Expected student to exist, but they were not found.");
    assertEquals(expectedName, actualName, "Student name did not match.");
    assertEquals(expectedGradeLevel, actualGradeLevel, "Student grade level did not match.");
  }

  /**
   * Gherkin step definition to verify that a student with a specific name and grade level exists in
   * the system.
   * 
   * @param studentName The name of the student.
   * @param studentGradeLevel The grade level of the student.
   */
  @Then("the student {string} with grade level {string} shall exist in the system \\(p4)")
  public void the_student_with_grade_level_shall_exist_in_the_system_p4(String studentName,
      String studentGradeLevel) {
    List<Student> students = coolSupplies.getStudents();
    assertTrue(students.stream().anyMatch(
        s -> s.getName().equals(studentName) && s.getGrade().getLevel().equals(studentGradeLevel)));
  }
}
