package ca.mcgill.ecse.coolsupplies.features;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet6Controller;
import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.model.BundleItem.PurchaseLevel;
import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;
import ca.mcgill.ecse.coolsupplies.model.Parent;
import ca.mcgill.ecse.coolsupplies.model.Grade;
import ca.mcgill.ecse.coolsupplies.model.Student;
import ca.mcgill.ecse.coolsupplies.model.User;
import ca.mcgill.ecse.coolsupplies.model.Order;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AddOrderStepDefinitions {

  private CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();
  private String error = ""; // Stores error strings from controller methods

  /**
   * @author Mathis
   */
  @Given("the following parent entities exists in the system \\(p15)")
  public void the_following_parent_entities_exists_in_the_system_p15(
      io.cucumber.datatable.DataTable parentTable) {
    // Transform data into list of map
    List<Map<String, String>> rows = parentTable.asMaps();
    // Iterate over list
    for (Map<String, String> columns : rows) {
      // Create and add parent to the system
      coolSupplies.addParent(columns.get("email"), columns.get("password"), columns.get("name"),
          Integer.parseInt(columns.get("phoneNumber")));
    }
  }

  /**
   * @author Carolyn
   */
  @Given("the following grade entities exists in the system \\(p15)")
  public void the_following_grade_entities_exists_in_the_system_p15(
      io.cucumber.datatable.DataTable gradeTable) {
    // Transform data into list of map
    List<Map<String, String>> grades = gradeTable.asMaps();
    // Iterate over list
    for (var grade : grades) {
      // Create and add Grade to the system
      coolSupplies.addGrade(grade.get("level"));
    }

  }

  /**
   * @author Hamza
   */
  @Given("the following student entities exists in the system \\(p15)")
  public void the_following_student_entities_exists_in_the_system_p15(
      io.cucumber.datatable.DataTable studentTable) {
    // Transform data into list of map
    List<Map<String, String>> rows = studentTable.asMaps();
    // Iterate over list
    for (Map<String, String> student : rows) {
      // Create and add Student to the system
      coolSupplies.addStudent(student.get("name"), Grade.getWithLevel(student.get("gradeLevel")));
    }
  }

  /**
   * @author Marianne
   */
  @Given("the following student entities exist for a parent in the system \\(p15)")
  public void the_following_student_entities_exist_for_a_parent_in_the_system_p15(
      io.cucumber.datatable.DataTable studentParentTable) {
    // Iterate over the data stored in the DataTable
    for (Map<String, String> map : studentParentTable.asMaps()) {
      // Get parent
      Parent parent = (Parent) User.getWithEmail(map.get("parentEmail"));
      // Get student
      Student student = Student.getWithName(map.get("name"));
      // Add student to parent
      parent.addStudent(student);
    }
  }

  /**
   * @author Reina
   */
  @Given("the following order entities exists in the system \\(p15)")
  public void the_following_order_entities_exists_in_the_system_p15(
      io.cucumber.datatable.DataTable orderTable) {
    // Transform data into list of map
    List<Map<String, String>> rows = orderTable.asMaps(String.class, String.class);
    // Iterate over list
    for (Map<String, String> row : rows) {
      // Transform date string into date object
      Date date = Date.valueOf(row.get("date"));
      // Transform level string into PurchaseLevel object
      PurchaseLevel level = PurchaseLevel.valueOf(row.get("level"));
      // Get Parent
      Parent parent = (Parent) User.getWithEmail(row.get("parentEmail"));
      // Get Student
      Student student = Student.getWithName(row.get("studentName"));
      // Create and add order to system
      coolSupplies.addOrder(Integer.parseInt(row.get("number")), date, level, parent, student);
    }
  }

  /**
   * @author Yanick
   */
  @When("the parent attempts to add a new order in the system with number {string}, date {string}, level {string}, parent email {string}, and student name {string} \\(p15)")
  public void the_parent_attempts_to_add_a_new_order_in_the_system_with_number_date_level_parent_email_and_student_name_p15(
      String orderNumber, String orderDate, String orderLevel, String parentEmail,
      String studentName) {
    // Try to add a new order to the system
    callController(CoolSuppliesFeatureSet6Controller.startOrder(Integer.parseInt(orderNumber),
        Date.valueOf(orderDate), orderLevel, parentEmail, studentName));
  }

  /**
   * @author Hamza
   */
  @Then("the number of order entities in the system shall be {string} \\(p15)")
  public void the_number_of_order_entities_in_the_system_shall_be_p15(String expectedOrderCount) {

    int expected = Integer.parseInt(expectedOrderCount);
    int actual = coolSupplies.getOrders().size();
    // Check if number of order is the same as expected
    assertEquals(expected, actual);
  }

  /**
   * @author Marianne
   */
  @Then("the error {string} shall be raised \\(p15)")
  public void the_error_shall_be_raised_p15(String specifiedErrorMessage) {
    // Check if the specified error occurred
    assertTrue(error.contains(specifiedErrorMessage),
        String.format("Specified error message '%s' was not found. This is the error message '%s'",
            specifiedErrorMessage, error));
  }

  /**
   * @author Mathis
   */
  @Then("the order {string} with date {string}, level {string}, parent email {string}, and student name {string} shall exist in the system \\(p15)")
  public void the_order_with_date_level_parent_email_and_student_name_shall_exist_in_the_system_p15(
      String orderNumber, String orderDate, String orderLevel, String parentEmail,
      String studentName) {
    // Transform string to integer
    int parsedOrderNumber = Integer.parseInt(orderNumber);
    // Check if the order exists
    assertTrue(Order.hasWithNumber(parsedOrderNumber),
        String.format("Order with number '%d' was not found in system", parsedOrderNumber));
    // Get order
    Order order = Order.getWithNumber(parsedOrderNumber);
    // Check if order data are correct
    assertEquals(orderDate, order.getDate().toString(),
        String.format("The expected date for order number '%d' is '%s' but found '%s'",
            parsedOrderNumber, orderDate, order.getDate()));
    assertEquals(orderLevel, order.getLevel().toString(),
        String.format("The expected level for order number '%d' is '%s' but found '%s'",
            parsedOrderNumber, orderLevel, order.getLevel()));
    assertEquals(parentEmail, order.getParent().getEmail(),
        String.format("The expected parent email for order number '%d' is '%s' but found '%s'",
            parsedOrderNumber, parentEmail, order.getParent().getEmail()));
    assertEquals(studentName, order.getStudent().getName(),
        String.format("The expected student name for order number '%d' is '%s' but found '%s'",
            parsedOrderNumber, studentName, order.getStudent().getName()));
  }

  private void callController(String controllerResult) {
    // Add the result of the controller call to the error string
    if (!controllerResult.isEmpty()) {
      error += controllerResult;
    }
  }
}