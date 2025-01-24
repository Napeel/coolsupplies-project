package ca.mcgill.ecse.coolsupplies.features;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet6Controller;
import ca.mcgill.ecse.coolsupplies.controller.TOStudent;
import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;
import ca.mcgill.ecse.coolsupplies.model.Parent;
import ca.mcgill.ecse.coolsupplies.model.Student;
import ca.mcgill.ecse.coolsupplies.model.User;
import ca.mcgill.ecse.coolsupplies.model.Grade;

/**
 * 
 * @author Bryan-Viet-Hoang-Vu
 * @author Matthew-Petruzziello
 */

public class GetStudentFromStudentsOfParentStepDefinition {
  private CoolSupplies coolSupplies;
  private List<TOStudent> presentedStudents;

  @Before
  public void setup() {
    coolSupplies = CoolSuppliesApplication.getCoolSupplies();
    presentedStudents = new ArrayList<TOStudent>();
  }

  @Given("the following parent entities exists in the system \\(p3)")
  public void the_following_parent_entities_exists_in_the_system_p3(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> parentEntities = dataTable.asMaps();
    for (Map<String, String> parent : parentEntities) {
      new Parent(parent.get("email"), parent.get("password"), parent.get("name"),
          Integer.parseInt(parent.get("phoneNumber")), coolSupplies);
    }
  }

  @Given("the following grade entities exists in the system \\(p3)")
  public void the_following_grade_entities_exists_in_the_system_p3(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> gradeEntities = dataTable.asMaps();
    for (Map<String, String> grade : gradeEntities) {
      new Grade(grade.get("level"), coolSupplies);
    }
  }

  @Given("the following student entities exists in the system \\(p3)")
  public void the_following_student_entities_exists_in_the_system_p3(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> studentEntities = dataTable.asMaps();
    for (Map<String, String> student : studentEntities) {
      String gradeLevel = student.get("gradeLevel");
      Grade studentGrade = Grade.getWithLevel(gradeLevel);

      if (studentGrade == null) {
        fail("Invalid grade level: " + gradeLevel);
      }

      new Student(student.get("name"), coolSupplies, studentGrade);
    }
  }

  @Given("the following student entities exist for a parent in the system \\(p3)")
  public void the_following_student_entities_exist_for_a_parent_in_the_system_p3(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> studentToParentEntities = dataTable.asMaps();

    for (Map<String, String> studentParent : studentToParentEntities) {

      Parent parentOfStudent = (Parent) User.getWithEmail(studentParent.get("parentEmail"));

      if (parentOfStudent == null) {
        fail("Parent with email " + studentParent.get("parentEmail") + " not found");
      }

      Student student = Student.getWithName(studentParent.get("name"));

      if (student != null) {
        student.setParent(parentOfStudent);
      } else {
        fail("Student with name " + studentParent.get("name") + " is null");
      }
    }
  }

  @When("the parent attempts to get a student with name {string} from a parent with email {string} \\(p3)")
  public void the_parent_attempts_to_get_a_student_with_name_from_a_parent_with_email_p3(
      String studentName, String parentEmail) {
    TOStudent student = CoolSuppliesFeatureSet6Controller.getStudentOfParent(studentName, parentEmail);
    if (student == null) {
      return;
    }
    presentedStudents.add(student);
  }

  @When("the parent attempts to get all students from a parent with email {string} \\(p3)")
  public void the_parent_attempts_to_get_all_students_from_a_parent_with_email_p3(
      String parentEmail) {
    List<TOStudent> allStudents = CoolSuppliesFeatureSet6Controller.getStudentsOfParent(parentEmail);
    presentedStudents.addAll(allStudents);
  }

  @Then("the number of student entities in the system shall be {string} \\(p3)")
  public void the_number_of_student_entities_in_the_system_shall_be_p3(String expectedNumStudents) {
    int expectedNumStudentsInt = Integer.parseInt(expectedNumStudents);
    int actualNumStudents = coolSupplies.getStudents().size();
    assertEquals(expectedNumStudentsInt, actualNumStudents);
  }

  @Then("the number of student entities for parent {string} in the system shall be {string} \\(p3)")
  public void the_number_of_student_entities_for_parent_in_the_system_shall_be_p3(
      String parentEmail, String expectedNumStudents) {
    int expectedNumStudentsInt = Integer.parseInt(expectedNumStudents);
    int count = 0;
    for (Student student : coolSupplies.getStudents()) {
      if (student.getParent() != null && student.getParent().getEmail().equals(parentEmail)) {
        count++;
      }
    }
    assertEquals(expectedNumStudentsInt, count);
  }

  @Then("the following student entities shall be presented \\(p3)")
  public void the_following_student_entities_shall_be_presented_p3(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> studentEntities = dataTable.asMaps();
    List<TOStudent> exampleStudentList = new ArrayList<TOStudent>();
    for (Map<String, String> student : studentEntities) {
      TOStudent newTOStudent = new TOStudent(student.get("name"), student.get("gradeLevel"));
      exampleStudentList.add(newTOStudent);
    }
    assertEquals(exampleStudentList.size(), presentedStudents.size());

    for (TOStudent expected : exampleStudentList) {
      boolean found = false;
      for (TOStudent presented : presentedStudents) {
        if (presented.getName().equals(expected.getName())
            && presented.getGradeLevel().equals(expected.getGradeLevel())) {
          found = true;
          break;
        }
      }
      assertTrue(found, "Expected student not found: " + expected.getName());
    }
  }

  @Then("no student entities shall be presented \\(p3)")
  public void no_student_entities_shall_be_presented_p3() {
    int actualNumStudents = presentedStudents.size();
    assertEquals(0, actualNumStudents);
  }
}