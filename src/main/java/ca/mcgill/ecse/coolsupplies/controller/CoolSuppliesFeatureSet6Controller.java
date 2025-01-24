package ca.mcgill.ecse.coolsupplies.controller;

import java.sql.Date;
import java.util.List;
import java.util.ArrayList;
import ca.mcgill.ecse.coolsupplies.model.*;
import ca.mcgill.ecse.coolsupplies.model.BundleItem.PurchaseLevel;
import ca.mcgill.ecse.coolsupplies.application.*;
import ca.mcgill.ecse.coolsupplies.persistence.CoolSuppliesPersistence;

/**
 * Controller for managing features related to students and parents as well as adding orders in the
 * Cool Supplies application.
 */

public class CoolSuppliesFeatureSet6Controller {

  /**
   * Helper function to retrieve a student from the Cool Supplies application by name
   *
   * @param studentName the name of the student to retrieve
   * @return the Student object if found, null otherwise
   */

  private static Student getStudentFromController(String studentName) {
    CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();
    for (Student student : coolSupplies.getStudents()) {
      if (student.getName().equals(studentName)) {
        return student;
      }
    }
    return null;
  }

  /**
   * Helper function to retrieve a parent from the Cool Supplies application by email.
   *
   * @param parentEmail the email of the parent to retrieve
   * @return the Parent object if found, null otherwise
   */

  private static Parent getParentFromController(String parentEmail) {
    CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();
    for (Parent parent : coolSupplies.getParents()) {
      if (parent.getEmail().equals(parentEmail)) {
        return parent;
      }
    }
    return null;
  }

  /**
   * Helper function to validate if the provided object is null.
   *
   * @param o the object to validate
   * @return true if the object is not null, false otherwise
   */

  private static Boolean isNullBoolean(Object o) {
    return (o == null) ? true : false;
  }

  /**
   * Adds a student to a parent.
   *
   * @param studentName the name of the student to add
   * @param parentEmail the email of the parent to whom the student will be added
   * @return a message indicating the result of the operation
   */

  public static String addStudentToParent(String studentName, String parentEmail) {
    Student student = getStudentFromController(studentName);
    Parent parent = getParentFromController(parentEmail);

    if (isNullBoolean(student)) {
      return "The student does not exist.";
    }

    if (isNullBoolean(parent)) {
      return "The parent does not exist.";
    }

    if (!student.setParent(parent)) {
      return "Could not add student to parent";
    }
    CoolSuppliesPersistence.save();

    return "Successfully added student " + studentName + " to the following email: " + parentEmail;
  }

  /**
   * Removes a student from a parent.
   *
   * @param studentName the name of the student to remove
   * @param parentEmail the email of the parent from whom the student will be removed
   * @return a message indicating the result of the operation
   */

  public static String deleteStudentFromParent(String studentName, String parentEmail) {
    Student student = getStudentFromController(studentName);
    Parent parent = getParentFromController(parentEmail);

    if (isNullBoolean(parent)) {
      return "The parent does not exist.";
    }
    if (isNullBoolean(student)) {
      return "The student does not exist.";
    }

    if (!parent.removeStudent(student)) {
      return "Could not remove student from parent.";
    }
    CoolSuppliesPersistence.save();

    return "Successfully removed student " + studentName + " from parent " + parentEmail;

  }

  /**
   * Retrieves the student associated with a parent.
   *
   * @param studentName the name of the student
   * @param parentEmail the email of the parent
   * @return a TOStudent object if the student belongs to the parent, null otherwise
   */

  public static TOStudent getStudentOfParent(String studentName, String parentEmail) {
    Student student = getStudentFromController(studentName);
    Parent parent = getParentFromController(parentEmail);
    if (isNullBoolean(parent) && isNullBoolean(student)) {
      return null;
    }
    if (!isNullBoolean(student.getParent()) && student.getParent().equals(parent)) {
      return new TOStudent(student.getName(), student.getGrade().getLevel());
    }

    return null;
  }

  /**
   * Retrieves all students associated with a given parent.
   *
   * @param parentEmail the email of the parent
   * @return a list of TOStudent objects representing the students of the parent
   */

  public static List<TOStudent> getStudentsOfParent(String parentEmail) {
    Parent parent = getParentFromController(parentEmail);
    if (isNullBoolean(parent)) {
      return new ArrayList<TOStudent>();
    }

    List<TOStudent> studentsOfParent = new ArrayList<TOStudent>();

    CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();
    for (Student student : coolSupplies.getStudents()) {
      if (parent.equals(student.getParent())) {
        studentsOfParent.add(new TOStudent(student.getName(), student.getGrade().getLevel()));
      }
    }

    return studentsOfParent;
  }

  /**
   * Starts an order for a student under a parent.
   *
   * @param number the order number
   * @param date the date of the order
   * @param level the purchase level of the order
   * @param parentEmail the email of the parent placing the order
   * @param studentName the name of the student for whom the order is placed
   * @return a message indicating the result of the order creation
   */

  public static String startOrder(int number, Date date, String level, String parentEmail,
      String studentName) {
    Student student = getStudentFromController(studentName);
    Parent parent = getParentFromController(parentEmail);
    PurchaseLevel purchaseLevel;
    CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();

    if (!isNullBoolean(Order.getWithNumber(number))) {
      return "The number must be unique.";
    }

    if (number == 0) {
      return "The number must be greater than 0.";
    }

    try {
      purchaseLevel = PurchaseLevel.valueOf(level);
    } catch (Exception e) {
      return "The level must be Mandatory, Recommended, or Optional.";
    }

    if (isNullBoolean(parent)) {
      return "The parent does not exist.";
    }

    if (isNullBoolean(student)) {
      return "The student does not exist.";
    }

    try {
      coolSupplies.addOrder(number, date, purchaseLevel, parent, student);
      CoolSuppliesPersistence.save();
    } catch (Exception e) {
      return "An error occurred while trying to start the order";
    }

    return "Order for student " + studentName + " has been created successfully with level: "
        + level;
  }

}
