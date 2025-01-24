
package ca.mcgill.ecse.coolsupplies.controller;

import java.util.List;
import java.util.ArrayList;
import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;
import ca.mcgill.ecse.coolsupplies.model.Student;
import ca.mcgill.ecse.coolsupplies.model.Grade;
import ca.mcgill.ecse.coolsupplies.persistence.CoolSuppliesPersistence;


public class CoolSuppliesFeatureSet2Controller {

  private static CoolSupplies coolsupplies = CoolSuppliesApplication.getCoolSupplies();

  /**
   * Private Helper Method returns a grade given the grade level.
   *
   * @param gradeLevel The grade level of the grade to be returned.
   * @return The grade associated with the gradelevel or null if that grade doesn't exist.
   * @author Sofia Galanopoulos
   */
  private static Grade getGradeFromLevel(String gradeLevel) {
    List<Grade> grades = coolsupplies.getGrades();

    for (Grade g : grades) {
      if (g.getLevel().equals(gradeLevel)) {
        return g;
      }
    }

    return null;
  }

  /**
   * Private Helper Method that returns the student associated with the given name.
   *
   * @param name The name of the student to be returned.
   * @return The student associated with the name or null if no such student exists.
   * @author Sofia Galanopoulos
   */
  private static Student getStudentFromName(String name) {
    List<Student> students = coolsupplies.getStudents();

    for (Student s : students) {
      if (s.getName().equals(name)) {
        return s;
      }
    }

    return null;
  }


  /**
   * Adds a new student to the CoolSupplies system
   * 
   * @param name The name of the student.
   * @param gradeLevel The grade level of the student.
   * @return An empty string if student is successfully added, otherwise an error message
   * @author Sofia Galanopoulos
   */
  public static String addStudent(String name, String gradeLevel) {

    // check name and grade level are not null
    if (name == null || name.isEmpty()) {
      return "The name must not be empty.";
    }
    if (gradeLevel == null || gradeLevel.isEmpty()) {
      return "The grade does not exist.";
    }

    // check grade level exists and get grade
    Grade grade = getGradeFromLevel(gradeLevel);

    if (grade == null) {
      return "The grade does not exist.";
    }

    // make sure name is unique
    List<Student> students = coolsupplies.getStudents();

    for (Student s : students) {
      if (s.getName().equals(name)) {
        return "The name must be unique.";
      }
    }

    try {
      coolsupplies.addStudent(name, grade);
      CoolSuppliesPersistence.save();
    } catch (RuntimeException e) {
      return e.getMessage();
    }

    return "";
  }

  /**
   * Updates the name and grade level of an existing student.
   * 
   * @param name The current name of the student.
   * @param newName The name to update the student with.
   * @param newGradeLevel The new grade level to update the student with.
   * @return An empty string if the update is successful, or an error message if something goes
   *         wrong.
   * @author Sofia Galanopoulos
   */
  public static String updateStudent(String name, String newName, String newGradeLevel) {

    // make sure none of the inputs are empty
    if (name == null || name.isEmpty()) {
      return "The name must not be empty.";
    }
    if (newName == null || newName.isEmpty()) {
      return "The name must not be empty.";
    }
    if (newGradeLevel == null || newGradeLevel.isEmpty()) {
      return "The grade does not exist.";
    }

    List<Student> students = coolsupplies.getStudents();
    Student student = null;

    // find student to update
    for (Student s : students) {
      if (s.getName().equals(name)) {
        student = s;
      }

      // make sure newName is unique
      if (s.getName().equals(newName)) {
        return "The name must be unique.";
      }
    }

    if (student == null) {
      return "The student does not exist.";
    }

    // get grade and make sure a proper grade level was inputted
    Grade grade = getGradeFromLevel(newGradeLevel);
    if (grade == null) {
      return "The grade does not exist.";
    }

    // update student name and grade
    student.setName(newName);
    student.setGrade(grade);
    CoolSuppliesPersistence.save();

    return "";

  }

  /**
   * Deletes a student from the CoolSupplies system.
   * 
   * @param name The name of the student to be deleted.
   * @return An empty string if the deletion is successful, or an error message if the student does
   *         not exist.
   * @author Sofia Galanopoulos
   */
  public static String deleteStudent(String name) {

    // make sure name is not null
    if (name == null || name.isEmpty()) {
      return "The name must not be empty.";
    }

    // get student and make sure they exist
    Student student = getStudentFromName(name);
    if (student == null) {
      return "The student does not exist.";
    }

    student.delete();
    CoolSuppliesPersistence.save();
    return "";
  }

  /**
   * Retrieves a specific student from the CoolSupplies system.
   * 
   * @param name The name of the student to retrieve.
   * @return A TOStudent object containing the student's name and grade level, or null if the
   *         student does not exist.
   * @author Sofia Galanopoulos
   */
  public static TOStudent getStudent(String name) {
    // make sure name is not null
    if (name == null || name.isEmpty()) {
      return null;
    }

    // get student and make sure they exist
    Student student = getStudentFromName(name);
    if (student == null) {
      return null;
    }

    // get grade level
    Grade grade = student.getGrade();
    String gradeLevel = grade.getLevel();

    // create TOStudent object
    TOStudent tostudent = new TOStudent(student.getName(), gradeLevel);
    return tostudent;
  }

  /**
   * Retrieves a list of all students in the CoolSupplies system.
   * 
   * @return A list of TOStudent objects, each representing a student with their name and grade
   *         level.
   * @author Sofia Galanopouos
   */

  // returns all students
  public static List<TOStudent> getStudents() {
    List<Student> students = coolsupplies.getStudents();

    List<TOStudent> TOstudents = new ArrayList<TOStudent>();
    for (Student s : students) {
      // get grade level
      Grade grade = s.getGrade();
      String g = grade.getLevel();

      // create TOStudent object
      TOStudent stu = new TOStudent(s.getName(), g);

      // add to list
      TOstudents.add(stu);
    }

    return TOstudents;

  }

}
