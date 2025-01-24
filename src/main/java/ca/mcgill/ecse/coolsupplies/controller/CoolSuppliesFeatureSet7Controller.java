package ca.mcgill.ecse.coolsupplies.controller;

import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;
import ca.mcgill.ecse.coolsupplies.model.Grade;
import ca.mcgill.ecse.coolsupplies.persistence.CoolSuppliesPersistence;
import java.util.ArrayList;

import java.util.List;

/**
 * Controller class for managing grades in the CoolSupplies application. This class contains methods
 * for adding, updating, deleting, retrieving grade, and retrieving grades.
 */
public class CoolSuppliesFeatureSet7Controller {

  private static CoolSupplies coolsupplies = CoolSuppliesApplication.getCoolSupplies();
  private static List<Grade> grades = coolsupplies.getGrades();


  /**
   * Adds a new grade to the system.
   *
   * @param level the grade level to be added.
   * @return a success message when a Grade is added successfully, or an error message otherwise.
   */
  public static String addGrade(String level) {
    if (level == null || level.isEmpty()) {
      return "The level must not be empty.";
    }
    if (Grade.hasWithLevel(level)) {
      return "The level must be unique.";
    }
    try {
      Grade newGrade = new Grade(level, coolsupplies);
      CoolSuppliesPersistence.save();
      return "Grade added successfully.";
    } catch (Exception e) {
      return "Error: Unable to add grade.";
    }
  }

  /**
   * Updates the name of an existing grade.
   *
   * @param level the current grade level to be updated.
   * @param newLevel the new grade level.
   * @return a success message if the grade is updated successfully, or an error message otherwise.
   */
  public static String updateGrade(String level, String newLevel) {
    if (level == null || level.isEmpty() || newLevel == null || newLevel.isEmpty()) {
      return "The level must not be empty.";
    }
    Grade existingGrade = Grade.getWithLevel(level);
    if (existingGrade == null) {
      return "The grade does not exist.";
    }
    if (Grade.hasWithLevel(newLevel)) {
      return "The level must be unique.";
    }
    existingGrade.setLevel(newLevel);
    CoolSuppliesPersistence.save();
    return "Grade updated successfully.";
  }

  /**
   * Deletes an existing grade from the application.
   *
   * @param level the grade level to be deleted.
   * @return a success message if the grade is deleted successfully, or an error message otherwise.
   */
  public static String deleteGrade(String level) {
    if (level == null || level.isEmpty()) {
      return "Invalid grade name: empty";
    }

    Grade existingGrade = Grade.getWithLevel(level);
    if (existingGrade == null) {
      return "The grade does not exist.";
    }

    if (!grades.contains(existingGrade)) {
      return "The grade does not exist.";
    }
    existingGrade.delete();
    CoolSuppliesPersistence.save();

    return "Grade deleted successfully.";
  }


  /**
   * returns a grade based on input level as a transfer object.
   *
   * @param level the grade level to be retrieved.
   * @return Grade as a transfer object or null if the grade is not found.
   */
  public static TOGrade getGrade(String level) {
    Grade grade = Grade.getWithLevel(level);
    if (grade == null) {
      return null;
    }
    return new TOGrade(grade.getLevel());
  }

  /**
   * returns all grades
   *
   * @return a list of all grades as a list of Grade transfer objects.
   */
  public static List<TOGrade> getGrades() {
    List<TOGrade> toGrades = new ArrayList<>();
    for (Grade grade : grades) {
      toGrades.add(new TOGrade(grade.getLevel()));
    }
    return toGrades;
  }

}
