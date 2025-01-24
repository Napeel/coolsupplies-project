package ca.mcgill.ecse.coolsupplies.features;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import ca.mcgill.ecse.coolsupplies.application.*;
import ca.mcgill.ecse.coolsupplies.controller.*;
import ca.mcgill.ecse.coolsupplies.model.*;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.*;

public class GetItemStepDefinitions {
  private List<TOItem> retrievedItems = new ArrayList<>();
  private CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();
  
    /**
   * @author Janelle
   * @author Alara
   */
  @When("the school admin attempts to get from the system all the items \\(p6)")
  public void the_school_admin_attempts_to_get_from_the_system_all_the_items_p6() {
    retrievedItems.addAll(CoolSuppliesFeatureSet3Controller.getItems());
  }
 /**
   * @author Alara
   */
  @When("the school admin attempts to get from the system the item with name {string} \\(p6)")
  public void the_school_admin_attempts_to_get_from_the_system_the_item_with_name_p6(
      String string) {
    // Write code her-e that turns the phrase above into concrete actions
    TOItem item = CoolSuppliesFeatureSet3Controller.getItem(string);
    if (item == null) {
      return;
    }

    retrievedItems.add(item);
  }

   /**
   * @author Alara
   */

  @Then("the following item entities shall be presented \\(p6)")
  public void the_following_item_entities_shall_be_presented_p6(
      io.cucumber.datatable.DataTable dataTable) {
    // Convert DataTable to a list of expected items
    List<Map<String, String>> expectedItems = dataTable.asMaps();
    // Validate that actual items match expected items
    assertEquals(retrievedItems.size(), expectedItems.size(), "Item count does not match");

       // Loop through each expected parent and verify it exists in the actual list
    for (Map<String, String> expectedItem : expectedItems) {
      boolean matchFound = false;

      for (TOItem actualItem : retrievedItems) {
        if (expectedItem.get("name").equals(actualItem.getName()) &&
                Integer.parseInt(expectedItem.get("price"))==(actualItem.getPrice())) {
          matchFound = true;
          break;
        }
      }

      assertTrue(matchFound, "Expected itme not found: " + expectedItem);
    }
    
  }

   /**
   * @author Janelle
   * @author Alara
   */

  @Then("no item entities shall be presented \\(p6)")
  public void no_item_entities_shall_be_presented_p6() {
    // Use CoolSuppliesFeatureSet3Controller.getItems() instead of 
    // retrievedItems so we can test the method again and make sure that 
    // everything is indeed deleted, instead of working with our own list
    assertTrue(retrievedItems.isEmpty(), "Expected no items, but retrieved: " + CoolSuppliesFeatureSet3Controller.getItems().size());
  }
}
