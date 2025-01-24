package ca.mcgill.ecse.coolsupplies.features;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import java.util.Map;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet3Controller;
import ca.mcgill.ecse.coolsupplies.model.Item;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet3Controller;
import ca.mcgill.ecse.coolsupplies.model.Item;
 
import java.util.List;
import java.util.Map;
 
public class UpdateItemStepDefinitions {


  @When("the school admin attempts to update item {string} in the system with name {string} and price {string} \\(p14)")
  public void the_school_admin_attempts_to_update_item_in_the_system_with_name_and_price_p14(
      String oldName, String newName, String newPrice) {
    AddItemStepDefinitions.callController(
        CoolSuppliesFeatureSet3Controller.updateItem(oldName, newName, Integer.parseInt(newPrice)));

  }

  @Then("the item {string} with price {string} shall not exist in the system \\(p14)")
  public void the_item_with_price_shall_not_exist_in_the_system_p14(String name, String price) {
    Item item = (Item) Item.getWithName(name);
    assertTrue(item == null || item.getPrice() != Integer.parseInt(price),
        "Item " + name + " with price " + price + " should not exist in the system");
  }
 
  @Then("the following item entities shall exist in the system \\(p14)")
  public void the_following_item_entities_shall_exist_in_the_system_p14(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();
    for (var row : rows) {
      String name = row.get("name");
      int price = Integer.parseInt(row.get("price"));

      Item item = (Item) Item.getWithName(name);
      assertNotNull(item);
      assertEquals(price, item.getPrice());
    }
  }

}
