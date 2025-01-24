package ca.mcgill.ecse.coolsupplies.features;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import java.util.List;
import java.util.Map;
import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet2Controller;
import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;
import ca.mcgill.ecse.coolsupplies.model.Student;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * Step definition class for the "Update Student" feature. This class defines the Gherkin steps used
 * in the feature file and connects them with the underlying system logic to update students.
 * 
 * @author Alexander Kudinov
 */
public class UpdateStudentStepDefinitions {

  /**
   * Gherkin step definition for updating a student in the system. The school admin attempts to
   * update a student by specifying the current name, the new name, and the new grade level.
   * 
   * @param currentStudentName The current name of the student.
   * @param newStudentName The new name to update the student to.
   * @param newStudentGradeLevel The new grade level of the student.
   */
  CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();

  @When("the school admin attempts to update student {string} in the system with name {string} and grade level {string} \\(p4)")
  public void the_school_admin_attempts_to_update_student_in_the_system_with_name_and_grade_level_p4(
      String currentStudentName, String newStudentName, String newStudentGradeLevel) {
    String result = CoolSuppliesFeatureSet2Controller.updateStudent(currentStudentName,
        newStudentName, newStudentGradeLevel);
    TestUtility.setError(result);
  }

  /**
   * Gherkin step definition to ensure that a student with a specified name and grade level does not
   * exist in the system after an update operation.
   * 
   * @param studentName The name of the student.
   * @param studentGradeLevel The grade level of the student.
   */
  @Then("the student {string} with grade level {string} shall not exist in the system \\(p4)")
  public void the_student_with_grade_level_shall_not_exist_in_the_system_p4(String studentName,
      String studentGradeLevel) {
    List<Student> students = coolSupplies.getStudents();


    Student student = students.stream().filter(
        s -> s.getName().equals(studentName) && s.getGrade().getLevel().equals(studentGradeLevel))
        .findFirst().orElse(null);


    if (student != null) {
        fail("Student " + studentName + " with grade level " + studentGradeLevel + " should not exist in the system.");
    }
  }

  /**
   * Gherkin step definition to ensure that a specific list of student entities exists in the
   * system. The method checks if the expected students, including their names and grade levels, are
   * present in the system.
   * 
   * @param dataTable A DataTable containing the expected student names and grade levels.
   */
  @Then("the following student entities shall exist in the system \\(p4)")
  public void the_following_student_entities_shall_exist_in_the_system_p4(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> expectedStudents = dataTable.asMaps(String.class, String.class);


    List<Student> actualStudents = coolSupplies.getStudents();


    for (Map<String, String> expectedStudent : expectedStudents) {
      String expectedName = expectedStudent.get("name");
      String expectedGradeLevel = expectedStudent.get("gradeLevel");


      Student actualStudent = actualStudents.stream().filter(s -> s.getName().equals(expectedName)
          && s.getGrade().getLevel().equals(expectedGradeLevel)).findFirst().orElse(null);


      assertNotNull(actualStudent, "Expected student " + expectedName + " with grade level "
          + expectedGradeLevel + " was not found in the system.");
    }
  }
}
