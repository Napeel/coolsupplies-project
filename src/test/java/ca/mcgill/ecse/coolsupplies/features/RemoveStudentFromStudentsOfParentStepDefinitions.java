
package ca.mcgill.ecse.coolsupplies.features;

import java.util.List;
import java.util.Map;

import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet6Controller;
import ca.mcgill.ecse.coolsupplies.model.Parent;
import ca.mcgill.ecse.coolsupplies.model.Student;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.*;

public class RemoveStudentFromStudentsOfParentStepDefinitions {

  /**
   * @author Kathelina Wei
   */
  @Given("the following student entities exist for a parent in the system \\(p16)")
  public void the_following_student_entities_exist_for_a_parent_in_the_system_p16(
      io.cucumber.datatable.DataTable dataTable) {

    // Convert the data table into a list of maps where each map represents a row of data.
    List<Map<String, String>> rows = dataTable.asMaps();

    for (var row : rows) {
      String name = row.get("name");
      String parentEmail = row.get("parentEmail");
      Parent studentParent = null;
      Student student = null;

      if (Parent.hasWithEmail(parentEmail)) {
        studentParent = (Parent) Parent.getWithEmail(parentEmail);
      } else {
        fail("Parent not found");
      }

      if (Student.hasWithName(name)) {
        student = Student.getWithName(name);
      } else {
        fail("student not found");
      }

      studentParent.addStudent(student);

    }
  }

  /**
   * @author Alexander Fou
   */
  @When("the parent attempts to remove a student with name {string} from a parent with email {string} \\(p16)")
  public void the_parent_attempts_to_remove_a_student_with_name_from_a_parent_with_email_p16(
      String studentName, String parentEmail) {
    AddStudentToStudentsOfParentStepDefinitions.lastError =
        CoolSuppliesFeatureSet6Controller.deleteStudentFromParent(studentName, parentEmail);
  }

  /**
   * @author Jad El Hachem
   */
  @Then("the following student entities shall exist in the system \\(p16)")
  public void the_following_student_entities_shall_exist_in_the_system_p16(
      io.cucumber.datatable.DataTable dataTable) {

    List<Map<String, String>> rows = dataTable.asMaps();

    // Loop through each map
    for (var row : rows) {
      String parentEmail = row.get("parentEmail");
      String nameOfStudent = row.get("name");

      Student studentFromTable = Student.getWithName(nameOfStudent);
      Parent parentFromTable = (Parent) Parent.getWithEmail(parentEmail);

      assertTrue(parentFromTable.getStudents().contains(studentFromTable));
    }
  }

  /**
   * @author Hongyi Ye
   */
  @Then("the following student entities for a parent shall exist in the system \\(p16)")
  public void the_following_student_entities_for_a_parent_shall_exist_in_the_system_p16(
      io.cucumber.datatable.DataTable dataTable) {

    List<Map<String, String>> rows = dataTable.asMaps();

    for (var row : rows) {
      String sName = row.get("name"); // must exist
      String parentTableEmail = row.get("parentEmail");
      Student s = Student.getWithName(sName);
      if (s == null) {fail("The student doesn't exit.");}
      String email = s.getParent().getEmail();
      assertEquals(email, parentTableEmail);
    }

  }


}
