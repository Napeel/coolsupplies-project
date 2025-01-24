package ca.mcgill.ecse.coolsupplies.features;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet3Controller;
import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

// Step definitions for the DeleteItem feature in the CoolSupplies application
public class DeleteItemStepDefinitions {

  // Variable to store error messages when operations fail 
  private String errorMessage = "";
  private CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();
  
  /**
   * @author alara
   */

  // Sets up the initial state of the system by adding items to the system
  @Given("the following item entities exists in the system \\(p6)")
  public void the_following_item_entities_exists_in_the_system_p6(
      io.cucumber.datatable.DataTable dataTable) {
      
      // Converts the data table from the Gherkin scenario into a list of maps 
      // Each map contains item properties like "name" and "price"  
      List<Map<String, String>> items = dataTable.asMaps(String.class, String.class);
      
      // Iterates over the list of items and adds each item to the system
      for (Map<String, String> itemData : items) {
          String name = itemData.get("name");
          int price = Integer.parseInt(itemData.get("price"));

          // adds item to coolSupplies
          coolSupplies.addItem(name, price);
      }
  }

    /**
   * @author alara
   */

  // Simulates the action of the school admin attempting to delete an item by name
  @When("the school admin attempts to delete from the system the item with name {string} \\(p6)")
  public void the_school_admin_attempts_to_delete_from_the_system_the_item_with_name_p6(
      String string) {
      errorMessage = CoolSuppliesFeatureSet3Controller.deleteItem(string);
  }

  /**
   * @author alara
   */

  // This method verifies that the number of items in the system matches the expected count
  @Then("the number of item entities in the system shall be {string} \\(p6)")
  public void the_number_of_item_entities_in_the_system_shall_be_p6(String string) {
    // Directly check the size of the in-memory list
    int actualItemCount = coolSupplies.getItems().size();
    // Asserts that the actual count matches the expected count
    assertEquals(Integer.parseInt(string), actualItemCount,
        "The number of item entities does not match the expected value.");
  }


  /**
   * @author alara
   */

  // This method verifies that specific items exist in the system 
  @Then("the following item entities shall exist in the system \\(p6)")
  public void the_following_item_entities_shall_exist_in_the_system_p6(
      io.cucumber.datatable.DataTable dataTable) {
      
    // Converts the data table from the Gherkin scenario into a list of expected items
    List<Map<String, String>> expectedItems = dataTable.asMaps(String.class, String.class);

    // Iterates over the list of expected items and checks if each exists in the in-memory item list
    for (Map<String, String> expectedItemData : expectedItems) {
      String expectedName = expectedItemData.get("name");
      int expectedPrice = Integer.parseInt(expectedItemData.get("price"));

      // Checks if an item with matching name and price exists in the in-memory system
      boolean itemExists = coolSupplies.getItems().stream().anyMatch(
          item -> item.getName().equals(expectedName) && item.getPrice() == expectedPrice);

      // Asserts that the expected item is found in the in-memory system
      assertTrue(itemExists, "The item does not exist.");
    }
  }
  
  /**
   * @author alara
   */

  // Verifies if the correct error message is raised when an operation fails
  @Then("the error {string} shall be raised \\(p6)")
  public void the_error_shall_be_raised_p6(String string) {
      // Asserts that an error message was set
      assertNotNull(errorMessage, "No error was raised.");
      // Asserts that the error message matches the expected message
      assertEquals(string, errorMessage, "The error message does not match the expected value.");
    }
}
