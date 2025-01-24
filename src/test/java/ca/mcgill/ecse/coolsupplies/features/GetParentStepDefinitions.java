package ca.mcgill.ecse.coolsupplies.features;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet1Controller;
import ca.mcgill.ecse.coolsupplies.controller.TOParent;
import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;
import ca.mcgill.ecse.coolsupplies.model.Parent;
import ca.mcgill.ecse.coolsupplies.model.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Step definitions for the GetParent feature in the CoolSupplies application.
 *
 * @author Jiatian Liu
 * @author Snigdha Sen
 * @author Zhengxuan Zhao
 * @author Shengyi Zhong
 */
public class GetParentStepDefinitions {

  // Instance of the CoolSupplies application model
  private CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();

  // List to store the parent entities retrieved from the system
  private List<TOParent> actualParentList = new ArrayList<>();

  @When("the school admin attempts to get from the system the parent with email {string} \\(p8)")
  public void the_school_admin_attempts_to_get_from_the_system_the_parent_with_email_p8(
      String parentEmail) {
    // Attempt to retrieve the parent using the provided email
    TOParent parent = CoolSuppliesFeatureSet1Controller.getParent(parentEmail);

    // If the parent is not found, return without adding to the list
    if (parent == null) {
      return;
    }

    // Add the retrieved parent to the actual parent list
    actualParentList.add(parent);
  }

  @When("the school admin attempts to get from the system all the parents \\(p8)")
  public void the_school_admin_attempts_to_get_from_the_system_all_the_parents_p8() {
    // Retrieve all parents and add them to the actual parent list
    actualParentList.addAll(CoolSuppliesFeatureSet1Controller.getParents());
  }

  @Then("the following parent entities shall be presented \\(p8)")
  public void the_following_parent_entities_shall_be_presented_p8(
      io.cucumber.datatable.DataTable expectedParentDataTable) {
    // Convert the expected parent data from the DataTable into a list of maps
    List<Map<String, String>> expectedParentList = expectedParentDataTable.asMaps();

    // Assert that the size of the expected list matches the actual list
    assertEquals(expectedParentList.size(), actualParentList.size(),
            "Expected " + expectedParentList.size() + " parent(s), but found "
                    + actualParentList.size() + ".");

    // Loop through each expected parent and verify it exists in the actual list
    for (Map<String, String> expectedParent : expectedParentList) {
      boolean matchFound = false;

      for (TOParent actualParent : actualParentList) {
        if (expectedParent.get("email").equals(actualParent.getEmail()) &&
                expectedParent.get("password").equals(actualParent.getPassword()) &&
                expectedParent.get("name").equals(actualParent.getName()) &&
                Integer.parseInt(expectedParent.get("phoneNumber")) == actualParent.getPhoneNumber()) {
          matchFound = true;
          break;
        }
      }

      assertTrue(matchFound, "Expected parent not found: " + expectedParent);
    }
  }

  @Then("no parent entities shall be presented \\(p8)")
  public void no_parent_entities_shall_be_presented_p8() {
    // Assert that the actual parent list is empty
    assertTrue(actualParentList.isEmpty(), "Expected no parents, but found some.");
  }
}
