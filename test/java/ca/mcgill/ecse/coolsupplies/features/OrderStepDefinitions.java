package ca.mcgill.ecse.coolsupplies.features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.sql.Date;

import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.controller.TOStudent;
import ca.mcgill.ecse.coolsupplies.model.*;
import ca.mcgill.ecse.coolsupplies.model.BundleItem.PurchaseLevel;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet8Controller;
import ca.mcgill.ecse.coolsupplies.controller.TOOrder;
import ca.mcgill.ecse.coolsupplies.controller.TOOrderItem;

/**
 * @author Bryan-Viet-Hoang-Vu
 * @author Matthew-Petruzziello
 */

public class OrderStepDefinitions {
  private CoolSupplies coolSupplies;
  private String actualErrorString;
  private List<TOOrder> presentedOrders;
  String name = "DefaultItem";
  int count = 0;

  private Order getOrderWithName(String orderNumber, CoolSupplies coolSupplies) {
    List<Order> orderList = coolSupplies.getOrders();
    for (Order order : orderList) {
      if (order.getNumber() == Integer.parseInt(orderNumber)) {
        return order;
      }
    }
    return null;
  }

  private boolean setState(Order order, String orderStatus, String authCode,
                           String penaltyAuthCode) {
    String nameToPut = name + count;
    if (orderStatus == null) {
      orderStatus = "Started";
    }
    switch (orderStatus) {
      case "Started":
        break;

      case "Paid":
        if (order.getOrderItems().isEmpty()) {
          Item defaultItem = new Item(nameToPut, 10, coolSupplies);
          coolSupplies.addOrderItem(1, order, defaultItem);
          count = count + 1;
        }
        order.paymentForOrder("0000");
        break;

      case "Penalized":
        order.schoolYearStarts();
        break;

      case "Prepared":
        if (order.getOrderItems().isEmpty()) {
          Item defaultItem = new Item(nameToPut, 10, coolSupplies);
          coolSupplies.addOrderItem(1, order, defaultItem);
          count = count + 1;
        }
        order.paymentForOrder("0000");
        order.schoolYearStarts();
        break;

      case "PickedUp":
        if (order.getOrderItems().isEmpty()) {
          Item defaultItem = new Item(nameToPut, 10, coolSupplies);
          coolSupplies.addOrderItem(1, order, defaultItem);
          count = count + 1;
        }
        order.paymentForOrder("0000");
        order.schoolYearStarts();
        order.pickedUpByStudent();
        break;

      default:
        return false;
    }
    order.setAuthorizationCode(authCode);
    order.setPenaltyAuthorizationCode(penaltyAuthCode);
    return true;
  }

  @Before
  public void setup() {
    coolSupplies = CoolSuppliesApplication.getCoolSupplies();
    presentedOrders = new ArrayList<TOOrder>();
  }

  @Given("the following parent entities exist in the system")
  public void the_following_parent_entities_exist_in_the_system(
          io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> parentEntities = dataTable.asMaps();
    for (Map<String, String> parent : parentEntities) {
      new Parent(parent.get("email"), parent.get("password"), parent.get("name"),
              Integer.parseInt(parent.get("phoneNumber")), coolSupplies);
    }
  }

  @Given("the following grade entities exist in the system")
  public void the_following_grade_entities_exist_in_the_system(
          io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> gradeEntities = dataTable.asMaps();
    for (Map<String, String> grade : gradeEntities) {
      new Grade(grade.get("level"), coolSupplies);
    }
  }

  @Given("the following student entities exist in the system")
  public void the_following_student_entities_exist_in_the_system(
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

  @Given("the following student entities exist for a parent in the system")
  public void the_following_student_entities_exist_for_a_parent_in_the_system(
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

  @Given("the following item entities exist in the system")
  public void the_following_item_entities_exist_in_the_system(
          io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> itemEntities = dataTable.asMaps();
    for (Map<String, String> item : itemEntities) {
      coolSupplies.addItem(item.get("name"), Integer.parseInt(item.get("price")));
    }
  }

  @Given("the following grade bundle entities exist in the system")
  public void the_following_grade_bundle_entities_exist_in_the_system(
          io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> gradeBundleEntities = dataTable.asMaps();
    for (Map<String, String> gradeBundle : gradeBundleEntities) {
      String name = gradeBundle.get("name");
      int discount = Integer.parseInt(gradeBundle.get("discount"));
      Grade grade = Grade.getWithLevel(gradeBundle.get("gradeLevel"));
      coolSupplies.addBundle(name, discount, grade);
    }
  }

  @Given("the following bundle item entities exist in the system")
  public void the_following_bundle_item_entities_exist_in_the_system(
          io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> bundleItemEntities = dataTable.asMaps();
    for (Map<String, String> bundleItem : bundleItemEntities) {
      int quantity = Integer.parseInt(bundleItem.get("quantity"));
      PurchaseLevel purchaseLevel = PurchaseLevel.valueOf(bundleItem.get("level"));
      GradeBundle gradeBundle =
              (GradeBundle) GradeBundle.getWithName(bundleItem.get("gradeBundleName"));
      Item item = (Item) Item.getWithName(bundleItem.get("itemName"));
      coolSupplies.addBundleItem(quantity, purchaseLevel, gradeBundle, item);
    }
  }

  @Given("the following order entities exist in the system")
  public void the_following_order_entities_exist_in_the_system(
          io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> orderEntities = dataTable.asMaps();

    for (Map<String, String> order : orderEntities) {
      int number = Integer.parseInt(order.get("number"));
      Date date = Date.valueOf(order.get("date"));
      PurchaseLevel purchaseLevel = PurchaseLevel.valueOf(order.get("level"));
      Parent parent = (Parent) Parent.getWithEmail(order.get("parentEmail"));
      Student student = Student.getWithName(order.get("studentName"));

      Order newOrder = new Order(number, date, purchaseLevel, parent, student, coolSupplies);
    }
  }

  @Given("the following order item entities exist in the system")
  public void the_following_order_item_entities_exist_in_the_system(
          io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> orderItemEntities = dataTable.asMaps();
    for (Map<String, String> orderItem : orderItemEntities) {
      int quantity = Integer.parseInt(orderItem.get("quantity"));
      Order order = Order.getWithNumber(Integer.parseInt(orderItem.get("orderNumber")));
      InventoryItem item = InventoryItem.getWithName(orderItem.get("itemName"));
      coolSupplies.addOrderItem(quantity, order, item);
    }
  }

  @Given("the order {string} is marked as {string}")
  public void the_order_is_marked_as(String orderNumber, String orderStatus) {
    Order order = Order.getWithNumber(Integer.parseInt(orderNumber));

    setState(order, orderStatus, "0000", "0000");

  }

  // Call the event and store any error message in actualErrorString and then @Then will handle the
  // error messages
  @When("the parent attempts to update an order with number {string} to purchase level {string} and student with name {string}")
  public void the_parent_attempts_to_update_an_order_with_number_to_purchase_level_and_student_with_name(
          String orderNumber, String purchaseLevel, String studentName) {
    actualErrorString = CoolSuppliesFeatureSet8Controller.UpdateOrder(Integer.parseInt(orderNumber),
            purchaseLevel, studentName);
  }

  @When("the parent attempts to add an item {string} with quantity {string} to the order {string}")
  public void the_parent_attempts_to_add_an_item_with_quantity_to_the_order(String itemName,
                                                                            String quantity, String orderNumber) {
    actualErrorString = CoolSuppliesFeatureSet8Controller
            .AddItemOrder(Integer.parseInt(orderNumber), itemName, Integer.parseInt(quantity));
  }

  @When("the parent attempts to update an item {string} with quantity {string} in the order {string}")
  public void the_parent_attempts_to_update_an_item_with_quantity_in_the_order(String itemName,
                                                                               String quantity, String orderNumber) {
    actualErrorString = CoolSuppliesFeatureSet8Controller.UpdateItem(Integer.parseInt(orderNumber),
            itemName, Integer.parseInt(quantity));
  }

  @When("the parent attempts to delete an item {string} from the order {string}")
  public void the_parent_attempts_to_delete_an_item_from_the_order(String itemName,
                                                                   String orderNumber) {
    actualErrorString =
            CoolSuppliesFeatureSet8Controller.removeItem(Integer.parseInt(orderNumber), itemName);
  }

  @When("the parent attempts to get from the system the order with number {string}")
  public void the_parent_attempts_to_get_from_the_system_the_order_with_number(String orderNumber) {
    TOOrder order = CoolSuppliesFeatureSet8Controller.getOrder(Integer.parseInt(orderNumber));
    if (order != null) {
      presentedOrders.add(order);
    }
  }


  @When("the parent attempts to cancel the order {string}")
  public void the_parent_attempts_to_cancel_the_order(String orderNumber) {
    actualErrorString =
            CoolSuppliesFeatureSet8Controller.CancelOrder(Integer.parseInt(orderNumber));
  }

  @When("the parent attempts to pay for the order {string} with authorization code {string}")
  public void the_parent_attempts_to_pay_for_the_order_with_authorization_code(String orderNumber,
                                                                               String authorizationCode) {
    actualErrorString = CoolSuppliesFeatureSet8Controller
            .processOrderPayment(Integer.parseInt(orderNumber), authorizationCode);
  }

  @When("the admin attempts to start a school year for the order {string}")
  public void the_admin_attempts_to_start_a_school_year_for_the_order(String orderNumber) {
    actualErrorString =
            CoolSuppliesFeatureSet8Controller.startSchoolYear(Integer.parseInt(orderNumber));
  }

  @When("the parent attempts to pay penalty for the order {string} with penalty authorization code {string} and authorization code {string}")
  public void the_parent_attempts_to_pay_penalty_for_the_order_with_penalty_authorization_code_and_authorization_code(
          String orderNumber, String penaltyAuthCode, String authCode) {
    actualErrorString = CoolSuppliesFeatureSet8Controller
            .processPenaltyPayment(Integer.parseInt(orderNumber), penaltyAuthCode, authCode);
  }

  @When("the student attempts to pickup the order {string}")
  public void the_student_attempts_to_pickup_the_order(String orderNumber) {
    actualErrorString =
            CoolSuppliesFeatureSet8Controller.PickedUpByStudent(Integer.parseInt(orderNumber));
  }

  @When("the school admin attempts to get from the system all orders")
  public void the_school_admin_attempts_to_get_from_the_system_all_orders() {
    presentedOrders = CoolSuppliesFeatureSet8Controller.getAllOrders();
  }

  @Then("the order {string} shall contain penalty authorization code {string}")
  public void the_order_shall_contain_penalty_authorization_code(String orderNumber,
                                                                 String penaltyAuthorizationCode) {
    Order order = getOrderWithName(orderNumber, coolSupplies);
    assertEquals(penaltyAuthorizationCode, order.getPenaltyAuthorizationCode());
  }

  @Then("the order {string} shall not contain penalty authorization code {string}")
  public void the_order_shall_not_contain_penalty_authorization_code(String orderNumber,
                                                                     String penaltyAuthorizationCode) {
    Order order = getOrderWithName(orderNumber, coolSupplies);
    assertNotEquals(penaltyAuthorizationCode, order.getPenaltyAuthorizationCode());
  }


  @Then("the order {string} shall not contain authorization code {string}")
  public void the_order_shall_not_contain_authorization_code(String orderNumber,
                                                             String authorizationCode) {
    Order order = getOrderWithName(orderNumber, coolSupplies);
    assertNotEquals(authorizationCode, order.getAuthorizationCode());
  }

  @Then("the order {string} shall not exist in the system")
  public void the_order_shall_not_exist_in_the_system(String orderNumber) {
    Order order = getOrderWithName(orderNumber, coolSupplies);
    assertNull(order);
  }

  @Then("the order {string} shall contain authorization code {string}")
  public void the_order_shall_contain_authorization_code(String orderNumber,
                                                         String authorizationCode) {
    Order order = getOrderWithName(orderNumber, coolSupplies);
    assertEquals(authorizationCode, order.getAuthorizationCode());
  }

  @Then("the order {string} shall contain {string} item")
  public void the_order_shall_contain_item(String orderNumber, String quantity) {
    Order order = getOrderWithName(orderNumber, coolSupplies);
    int expectedQuantity = Integer.parseInt(quantity);
    assertEquals(expectedQuantity, order.getOrderItems().size());
  }

  @Then("the order {string} shall not contain {string}")
  public void the_order_shall_not_contain(String orderNumber, String itemName) {
    List<OrderItem> allOrderItems = coolSupplies.getOrderItems();
    boolean found = false;

    for (OrderItem orderItem : allOrderItems) {
      if (orderItem.getItem().getName().equals(itemName)
              && orderItem.getOrder().getNumber() == Integer.parseInt(orderNumber)) {
        found = true;
      }
    }

    assertFalse(found);
  }


  @Then("the number of order items in the system shall be {string}")
  public void the_number_of_order_items_in_the_system_shall_be(String quantity) {
    int actualSize = coolSupplies.getOrderItems().size();
    int expectedSize = Integer.parseInt(quantity);
    assertEquals(expectedSize, actualSize);
  }

  @Then("the order {string} shall contain {string} items")
  public void the_order_shall_contain_items(String orderNumber, String quantity) {
    Order order = getOrderWithName(orderNumber, coolSupplies);
    int actualSize = order.getOrderItems().size();
    int expectedSize = Integer.parseInt(quantity);
    assertEquals(expectedSize, actualSize);
  }

  @Then("the order {string} shall not contain {string} with quantity {string}")
  public void the_order_shall_not_contain_with_quantity(String orderNumber, String itemName,
                                                        String quantity) {
    Order order = getOrderWithName(orderNumber, coolSupplies);
    int expectedQuantity = Integer.parseInt(quantity);

    for (OrderItem orderItem : order.getOrderItems()) {
      if (orderItem.getItem().getName().equals(itemName)) {
        assertNotEquals(expectedQuantity, orderItem.getQuantity());
      }
    }
  }


  @Then("the order {string} shall contain {string} with quantity {string}")
  public void the_order_shall_contain_with_quantity(String orderNumber, String itemName,
                                                    String quantity) {
    Order order = getOrderWithName(orderNumber, coolSupplies);
    OrderItem foundOrderItem = null;

    for (OrderItem orderItem : order.getOrderItems()) {
      if (orderItem.getItem().getName().equals(itemName)) {
        foundOrderItem = orderItem;
      }
    }
    assertNotNull(foundOrderItem);

    int expectedQuantity = Integer.parseInt(quantity);
    int actualQuantity = foundOrderItem.getQuantity();

    assertEquals(expectedQuantity, actualQuantity);
  }


  @Then("the order {string} shall be marked as {string}")
  public void the_order_shall_be_marked_as(String orderNumber, String expectedStatus) {
    Order order = getOrderWithName(orderNumber, coolSupplies);
    String actualStatus = order.getStatusFullName();
    assertEquals(expectedStatus, actualStatus);
  }


  @Then("the number of orders in the system shall be {string}")
  public void the_number_of_orders_in_the_system_shall_be(String quantity) {
    int expectedSize = Integer.parseInt(quantity);
    int actualSize = coolSupplies.getOrders().size();
    assertEquals(expectedSize, actualSize);
  }

  @Then("the order {string} shall contain level {string} and student {string}")
  public void the_order_shall_contain_level_and_student(String orderNumber,
                                                        String expectedPurchaseLevel, String expectedStudentName) {
    Order order = getOrderWithName(orderNumber, coolSupplies);

    String actualPurchaseLevel = order.getLevel().toString();
    assertEquals(expectedPurchaseLevel, actualPurchaseLevel);

    String actualStudentName = order.getStudent().getName();
    assertEquals(expectedStudentName, actualStudentName);
  }

  @Then("the error {string} shall be raised")
  public void the_error_shall_be_raised(String expectedErrorString) {
    assertEquals(expectedErrorString, actualErrorString);
  }

  @Then("the following order entities shall be presented")
  public void the_following_order_entities_shall_be_presented(
          io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> orderEntities = dataTable.asMaps();

    // verifying the number of orders matches
    assertEquals(orderEntities.size(), presentedOrders.size());

    // creating the expected orders from the data table
    for (Map<String, String> order : orderEntities) {
      boolean found = false;
      for (TOOrder presented : presentedOrders) {

        if (order.get("authorizationCode") == null){
          assertNull(presented.getAuthorizationCode());
        }

        if (order.get("penaltyAuthorizationCode") == null){
          assertNull(presented.getPenaltyAuthorizationCode());
        }

        if (presented.getParentEmail().equals(order.get("parentEmail"))
                && presented.getStudentName().equals(order.get("studentName"))
                && presented.getNumber() == Integer.parseInt(order.get("number"))
                && presented.getDate().equals(Date.valueOf(order.get("date")))
                && presented.getLevel().equals(order.get("level"))
                && presented.getTotalPrice() == Double.parseDouble(order.get("totalPrice"))) {
          found = true;
          break;
        }
      }
      assertTrue(found, "Order number " + order.get("number") + " not found.");
    }
  }

  @Then("the following order items shall be presented for the order with number {string}")
  public void the_following_order_items_shall_be_presented_for_the_order_with_number(
          String orderNumber, io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> orderItemEntities = dataTable.asMaps();

    TOOrder foundTOOrder = null;
    for (TOOrder TOOrderToCheck : presentedOrders) {
      if (TOOrderToCheck.getNumber() == Integer.parseInt(orderNumber)) {
        foundTOOrder = TOOrderToCheck;
      }
    }

    if (foundTOOrder == null) {
      fail("Could not find order");
    }

    // creating the expected order item from the data table
    for (Map<String, String> orderItem : orderItemEntities) {
      boolean found = false;
      String gradeBundleName = orderItem.get("gradeBundleName");
      if (gradeBundleName == null) {
        gradeBundleName = "";
      }
      String discountString = orderItem.get("discount");
      if (discountString == null || discountString == "") {
        discountString = "0";
      }

      for (TOOrderItem presented : foundTOOrder.getTOOrderItems()) {
        if (presented.getQuantity() == Integer.parseInt(orderItem.get("quantity"))
                && presented.getItemName().equals(orderItem.get("itemName"))
                && presented.getGradeBundleName().equals(gradeBundleName)
                && presented.getPrice() == Double.parseDouble(orderItem.get("price"))
                && presented.getDiscountDeducted() == Double.parseDouble(discountString)) {
          found = true;
          break;
        }
      }
      assertTrue(found, "Order item " + orderItem.get("itemName") + " not found.");
    }
  }

  @Then("no order entities shall be presented")
  public void no_order_entities_shall_be_presented() {
    assertEquals(0, presentedOrders.size());
  }

}
