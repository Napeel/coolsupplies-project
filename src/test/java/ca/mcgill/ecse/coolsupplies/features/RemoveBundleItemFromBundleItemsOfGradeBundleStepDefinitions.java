package ca.mcgill.ecse.coolsupplies.features;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet5Controller;
import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.model.BundleItem;
import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;
import ca.mcgill.ecse.coolsupplies.model.GradeBundle;
import ca.mcgill.ecse.coolsupplies.model.Item;
import ca.mcgill.ecse.coolsupplies.model.BundleItem.PurchaseLevel;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.*;

/**
 * Testing class for succesfully and unsuccesfully removing an item from grade bundle
 * @authors Ansh Sani, Artiom Volodin, Andres Gonzalez, Logan Miller
 * 
 */

public class RemoveBundleItemFromBundleItemsOfGradeBundleStepDefinitions {

  /**
   * Get CoolSupplies app as an attribute to easily modify
   */
  private static CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();
  /**
   * Error array if the removal is not succesful
   */
  private ArrayList<String> errors = new ArrayList<>();

  /**
   * @author Andres Gonzalez:
   * 
   *         <pre>
   * Step definition for "the school admin attempts to remove a bundle item with name {string} from a grade bundle with name {string} \\(p11)". This test attempts
   * to remove the item {itemName} from the bundle {bundleName} via the admin point of view.
   * 
   * <pre>
   * @param itemName name of the item to remove
   * @param bundleName name of the bundle where the item will be removed
   * 
   * Example:
   * <pre>
   *  When the school admin attempts to remove a bundle item with name {string} from a grade bundle with name {string} \\(p11)
   *         </pre>
   * 
   *         If the names correspond to valid bundle item and bundle identities, the bundle item
   *         will be removed from the coolSupplies application.
   * 
   */

  @When("the school admin attempts to remove a bundle item with name {string} from a grade bundle with name {string} \\(p11)")
  public void the_school_admin_attempts_to_remove_a_bundle_item_with_name_from_a_grade_bundle_with_name_p11(
      String itemName, String bundleName) {
    callController(CoolSuppliesFeatureSet5Controller.deleteBundleItem(itemName, bundleName));
  }

  /**
   * @author Andres Gonzalez
   * 
   *         Step definition for "the following bundle item entities shall exist in the system
   *         \\(p11)". This test asserts if the bundle items presented by the dataTable correspond
   *         to valid bundle items in the system.
   * 
   * @param dataTable The Cucumber data table containing the expected bundle item entities with
   *        properties such as quantity, level, gradeBundleName, and itemName.
   * 
   *        Example:
   * 
   *        <pre>
   *        "the following bundle item entities shall exist in the system \\(p11)"
   *        </pre>
   * 
   *        The method asserts that bundle items listed are found in the system.
   * 
   * @throws AssertionError if the items in the table do not correspond to any item in the system.
   */


  @Then("the following bundle item entities shall exist in the system \\(p11)")
  public void the_following_bundle_item_entities_shall_exist_in_the_system_p11(
      io.cucumber.datatable.DataTable dataTable) {

    List<Map<String, String>> rows = dataTable.asMaps();
    List<BundleItem> bundleItemList = coolSupplies.getBundleItems();

    for (var row : rows) {
      int quantity = Integer.parseInt(row.get("quantity"));
      PurchaseLevel purchaseLevel = PurchaseLevel.valueOf(row.get("level"));
      GradeBundle gradeBundle = findGradeBundle(row.get("gradeBundleName"));
      assertNotNull(gradeBundle);
      Item item = findItemByName(row.get("itemName"));
      assertNotNull(item);

      boolean found = false;

      for (BundleItem bundleItem : bundleItemList) {
        if (bundleItem.getQuantity() == quantity && bundleItem.getLevel().equals(purchaseLevel)
            && bundleItem.getBundle().equals(gradeBundle) && bundleItem.getItem().equals(item)) {
          found = true;
        }
      }
      assertTrue(found, "An expected bundle item was not found in the system");
    }
  }

  /**
 * @author Ansh Sahni:
 * <pre>
 * Step definition for "the error {string} shall be raised \\(p11)". This test verifies
 * that an error has been raised during the execution of the previous steps.
 * 
 * It checks two conditions:
 * 1. The size of the error ArrayList has been incremented, indicating that an error occurred during the
 *    execution of the scenario.
 * 2. The expected error message is contained within the actual error ArrayList. If the expected
 *    error message is not found, the test will fail.
 * </pre>
 *
 * @param expectedErrorMessage The expected error message that should be raised. This string 
 *                             is provided from the scenario and is used to verify the 
 *                             correctness of the error handling mechanism in the system.
 *
 * Example:
 * <pre>
 *  Then the error "Expected error message" shall be raised \\(p11)
 * </pre>
 *
 * @throws AssertionError if no errors were raised (i.e., if size of the error ArrayList is not greater than 0)
 *                        or if the expected error message is not found in the actual error ArrayList.
 */

  @Then("the error {string} shall be raised \\(p11)")
  public void the_error_shall_be_raised_p11(String expectedErrorMessage) {

    // Check if the amount of errors has increased
    assertTrue(errors.size() > 0, "Expected an error to be raised but none was found.");

    // Check if the expected error message is contained in the error ArrayList
    assertTrue(errors.contains(expectedErrorMessage),
        "Expected error message: '" + expectedErrorMessage + "' was not found.");
  }

  // Set of helper methods (add javadoc comments)

  /**
   * @author Artiom Volodin
   *
   * Finds and returns the GradeBundle object that corresponds to the provided bundle name.
   * This method iterates through the list of GradeBundle entities in the CoolSupplies system
   * and returns the first match based on the provided bundle name.
   *
   * @param bundleName the name of the grade bundle to search for
   *
   *        Example:
   *
   *        <pre>
   *            If you search for "Bundle 5", you will get an object representing Bundle 5.
   *            If you search for "Bundle school", you will get null because bundles in this system
   *            are numbered, not named with words.
   *        </pre>
   *
   * @return the GradeBundle object that matches the provided bundle name, or null if no match is found
   */

  private GradeBundle findGradeBundle(String bundleName) {
    List<GradeBundle> gradeBundleList = coolSupplies.getBundles();
    for (GradeBundle gradeBundle : gradeBundleList) {
      if (gradeBundle.getName().equals(bundleName)) {
        return gradeBundle;
      }
    }
    return null;
  }

  /**
   * @author Artiom Volodin
   *
   * Finds and returns the Item object that corresponds to the provided item name.
   * This method iterates through the list of Item entities in the CoolSupplies system
   * and returns the first match based on the provided item name.
   *
   * @param itemName the name of the item to search for
   *
   *        Example:
   *
   *        <pre>
   *            If you search for "pencil", you will get an object representing a pencil.
   *            If you search for "tires", you will get null because tires are not a school supply and therefore
   *            not on the list.
   *        </pre>
   *
   * @return the Item object that matches the provided item name or null if no match is found
   */


  private Item findItemByName(String itemName) {
    List<Item> ItemList = coolSupplies.getItems();
    for (Item item : ItemList) {
      if (item.getName().equals(itemName)) {
        return item;
      }
    }
    return null;
  }

  /**
   * @author Artiom Volodin
   *
   * Adds an error message to the existing error ArrayList if the result from the controller is not empty.
   *
   * @param result the result from the controller, which is checked for errors
   *
   *        Example:
   *
   *        <pre>
   *            If the result contains "Invalid quantity" the method adds this message to the error ArrayList.
   *        </pre>
   */

  private void callController(String result) {
    if (!result.isEmpty()) {
      errors.add(result);
    }
  }
}
