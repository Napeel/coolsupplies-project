package ca.mcgill.ecse.coolsupplies.features;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import java.util.Map;
import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet3Controller;
import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;
import ca.mcgill.ecse.coolsupplies.model.Item;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import java.util.Map;
import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet3Controller;
import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;
import ca.mcgill.ecse.coolsupplies.model.Item;




public class AddItemStepDefinitions {

  private static String error = "";
  private CoolSupplies coolsupplies = CoolSuppliesApplication.getCoolSupplies();;

  @Given("the following item entities exists in the system \\(p14)")
  public void the_following_item_entities_exists_in_the_system_p14(
      io.cucumber.datatable.DataTable dataTable) {

    List<Map<String, String>> rows = dataTable.asMaps();
    for (var row : rows) {
      String name = row.get("name");
      int price = Integer.parseInt(row.get("price"));
      coolsupplies.addItem(name, price);
    }

  }


  @When("the school admin attempts to add a new item in the system with name {string} and price {string} \\(p14)")
  public void the_school_admin_attempts_to_add_a_new_item_in_the_system_with_name_and_price_p14(
      String name, String price) {
    callController(CoolSuppliesFeatureSet3Controller.addItem(name, Integer.parseInt(price)));
  }

  @Then("the item {string} with price {string} shall exist in the system \\(p14)")
  public void the_item_with_price_shall_exist_in_the_system_p14(String name, String price) {
    Item item = (Item) Item.getWithName(name);
    assertNotNull(item);
    assertEquals(Integer.parseInt(price), item.getPrice());

  }

  @Then("the number of item entities in the system shall be {string} \\(p14)")
  public void the_number_of_item_entities_in_the_system_shall_be_p14(String numOfEntities) {
    assertEquals(Integer.parseInt(numOfEntities), coolsupplies.getItems().size());
  }

  @Then("the error {string} shall be raised \\(p14)")
  public void the_error_shall_be_raised_p14(String raisedError) {
    assertTrue(error.contains(raisedError), error + " does not contain " + raisedError);

  }

  public static void callController(String result) {
    if (!result.isEmpty()) {
      error += result;
    }
  }

}
