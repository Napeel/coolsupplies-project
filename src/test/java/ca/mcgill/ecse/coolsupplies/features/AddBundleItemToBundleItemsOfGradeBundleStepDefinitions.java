package ca.mcgill.ecse.coolsupplies.features;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet5Controller;
import ca.mcgill.ecse.coolsupplies.model.BundleItem;
import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;
import ca.mcgill.ecse.coolsupplies.model.Grade;
import ca.mcgill.ecse.coolsupplies.model.GradeBundle;
import ca.mcgill.ecse.coolsupplies.model.Item;
import ca.mcgill.ecse.coolsupplies.model.BundleItem.PurchaseLevel;

/**
 * Gherkin step definitions that are related to adding bundle items in grade bundle.
 * 
 * The following class contains the step definitions required for the gherkin scenarios
 * related to adding bundle items to a grade bundles.
 * Co-authored by many team members. All the methods in the class were co-authored by the
 * listed authors.
 * 
 * @author Arthur Huang
 * @author Maëlle Anguenot
 * @author Bora Denizasan
 * @author Hakim Bekkari
 */
public class AddBundleItemToBundleItemsOfGradeBundleStepDefinitions {

  public static String error = "";
  public static int errorCntr = 0;

  /**
   * Populates the CoolSupplies system with given grades from gherkin scenario datatable.
   * 
   * @param dataTable The datatable with the grade information of the grades we want to add. 
   *                  Each row is a grade with colomn "level" specifying the grade level.
   */
  @Given("the following grade entities exists in the system \\(p12)")
  public void the_following_grade_entities_exists_in_the_system_p12(
      io.cucumber.datatable.DataTable dataTable) {
    CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();
    List<Map<String, String>> gradeEntities = dataTable.asMaps();
    for (var grade : gradeEntities) {
      String level = grade.get("level");
      coolSupplies.addGrade(level);
    }
  }

  /**
   * Populates the CoolSupplies system with given items from the gherkin scenario datatable.
   * 
   * @param dataTable The datatable with the item information of the items we want to add. Each 
   *                  row is an inventory item with columns "name" specifying the item name, and 
   *                  "price" specifying the item price.
   */
  @Given("the following item entities exists in the system \\(p12)")
  public void the_following_item_entities_exists_in_the_system_p12(
      io.cucumber.datatable.DataTable dataTable) {
    CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();
    List<Map<String, String>> items = dataTable.asMaps();
    for (var item : items) {
      String name = item.get("name");
      int price = Integer.parseInt(item.get("price"));
      coolSupplies.addItem(name, price);
    }
  }

  /**
   * Populates the CoolSupplies system with the given grade bundles from the gherkin scenario datatable.
   * 
   * @param dataTable The datatable with the information of the grade bundles we want to add. Each row
   *                  is a grade bundle entity with columns "name" specifying the bundle name, "discount"
   *                  specifying the discount of the bundle, and "gradeLevel" specifying the grade level
   *                  of the bundle.
   */
  @Given("the following grade bundle entities exists in the system \\(p12)")
  public void the_following_grade_bundle_entities_exists_in_the_system_p12(
      io.cucumber.datatable.DataTable dataTable) {
        CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();
        List<Map<String, String>> bundles  = dataTable.asMaps();
        for (var bundle : bundles) {
          String name = bundle.get("name");
          if (GradeBundle.getWithName(name) != null) {
            fail("No grade bundle exists with name: " + name);
          }
          int discount = Integer.parseInt(bundle.get("discount"));
          String level = bundle.get("gradeLevel");
          Grade grade = Grade.getWithLevel(level);
          coolSupplies.addBundle(new GradeBundle(name, discount, coolSupplies, grade));
        }
  }

  /**
   * Populates the CoolSupplies system with the given bundle items from the gherkin scenario datatable.
   * 
   * @param dataTable The datatable with the information of the bundle items we want to add. Each row
   *                  is a bundle item with columns "quantity" specifying the bundle item quantity, 
   *                  "level" specifying the bundle item purchase level, "gradeBundleName" specifying
   *                  the of the grade bundle the bundle item belongs to, and "itemName" specifying the 
   *                  name of the bundle item.
   */
  @Given("the following bundle item entities exists in the system \\(p12)")
  public void the_following_bundle_item_entities_exists_in_the_system_p12(
      io.cucumber.datatable.DataTable dataTable) {
        CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();
        List<Map<String, String>> bundleItems = dataTable.asMaps();
        List<GradeBundle> bundles = coolSupplies.getBundles();
        List<Item> items = coolSupplies.getItems();
        for (var bundleItem : bundleItems) {
          int quantity = Integer.parseInt(bundleItem.get("quantity"));
          String level = bundleItem.get("level");
          String bundleName = bundleItem.get("gradeBundleName");
          String itemName = bundleItem.get("itemName");
          Item itemAdd = null;
          GradeBundle gradeBundle = null;
          for (GradeBundle bundle : bundles) {
            if (bundle.getName().equals(bundleName)) {
              gradeBundle = bundle;
              break;
            }
          }
          for (Item item : items) {
            if (item.getName().equals(itemName)) {
              itemAdd = item;
              break;
            }
          }
          coolSupplies.addBundleItem(quantity, PurchaseLevel.valueOf(level), gradeBundle, itemAdd);
        }
  }

  /**
   * Adds a bundle item to an existing grade bundle.
   * 
   * @param quantityString The quantity of the bundle item being added.
   * @param level The purchase level of the bundle item being added.
   * @param itemName The name of the bundle item being added.
   * @param gradeBundle The name of the grade bundle the bundle item is being added to.
   */
  @When("the school admin attempts to add a new bundle item with quantity {string}, level {string}, and item name {string} to an existing grade bundle {string} \\(p12)")
  public void the_school_admin_attempts_to_add_a_new_bundle_item_with_quantity_level_and_item_name_to_an_existing_grade_bundle_p12(
      String quantityString, String level, String itemName, String gradeBundle) {
        int quantity = Integer.parseInt(quantityString);
        error = "";
        errorCntr = 0;
        callController(CoolSuppliesFeatureSet5Controller.addBundleItem(quantity, level, itemName, gradeBundle));
  }

  /**
   * Adds a bundle item to a grade bundle that may or may not exist.
   * 
   * @param quantityString The quantity of the bundle item being added.
   * @param level The purchase level of the bundle item being added.
   * @param itemName The name of the bundle item being added.
   * @param gradeBundle The name of the grade bundle the bundle item is being added to.
   */
  @When("the school admin attempts to add a new bundle item with quantity {string}, level {string}, and itemName {string} to a gradeBundle {string} \\(p12)")
  public void the_school_admin_attempts_to_add_a_new_bundle_item_with_quantity_level_and_item_name_to_a_grade_bundle_p12(
    String quantityString, String level, String itemName, String gradeBundle) {
        int quantity = Integer.parseInt(quantityString);  
        error = "";
        errorCntr = 0;
        callController(CoolSuppliesFeatureSet5Controller.addBundleItem(quantity, level, itemName, gradeBundle));
  }

  /**
   * Verifies that that number of bundle items in the CoolSupplies system is equal to a
   * given amount
   * 
   * @param amount The number of bundle items the system should contain.
   */
  @Then("the number of bundle item entities in the system shall be {string} \\(p12)")
  public void the_number_of_bundle_item_entities_in_the_system_shall_be_p12(String amount) {
    int quantity = Integer.parseInt(amount);
    CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();
    assertEquals(quantity, coolSupplies.numberOfBundleItems());
  }

  /**
   * Verifies that the number of bundle items in a given bundle is equal to a given amount.
   * 
   * @param gradeBundleName The name of the grade bundle being verified.
   * @param itemAmount The number of bundle items that should be in the specified grade bundle.
   */
  @Then("the number of bundle item entities for grade bundle {string} in the system shall be {string} \\(p12)")
  public void the_number_of_bundle_item_entities_for_grade_bundle_in_the_system_shall_be_p12(
      String gradeBundleName, String itemAmount) {
        int quantity = Integer.parseInt(itemAmount);
        CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();
        List<GradeBundle> bundles = coolSupplies.getBundles();
        int numberOfItems = 0;
        for (GradeBundle bundle : bundles) {
          if (bundle.getName().equals(gradeBundleName)) {
            numberOfItems = bundle.getBundleItems().size();
            break;
          }
        }
        assertEquals(quantity, numberOfItems);
  }

  /**
   * Verifies that a bundle item with given quantity, level, and name exists in a given grade bundle.
   * 
   * @param quantity The quantity of the bundle item that should exist.
   * @param level The purchase level of the bundle item.
   * @param itemName The name of the bundle item.
   * @param gradeBundleName The name of the grade bundle we are looking for the bundle item in.
   */
  @Then("the bundle item with quantity {string}, level {string}, and item name {string} shall exist for grade bundle {string} in the system \\(p12)")
  public void the_bundle_item_with_quantity_level_and_item_name_shall_exist_for_grade_bundle_in_the_system_p12(
      String quantity, String level, String itemName, String gradeBundleName) {
        int IntQuantity = Integer.parseInt(quantity);
        CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();
        List<GradeBundle> bundles = coolSupplies.getBundles();
        GradeBundle targetBundle = null;
        for (GradeBundle bundle : bundles) {
          if (bundle.getName().equals(gradeBundleName)) {
            targetBundle = bundle;
            break;
          }
        }
        if (targetBundle == null) {
          fail("Bundle: " + gradeBundleName + " does not exist.");
        }
        List<BundleItem> bundleItems = targetBundle.getBundleItems();
        for (BundleItem item : bundleItems) {
          if (item.getQuantity() == IntQuantity && item.getLevel().equals(PurchaseLevel.valueOf(level)) && item.getItem().getName().equals(itemName)) {
            return;
          }
        }
        fail("Item: " + itemName + " with quantity: " + quantity + " and purchase level: " + 
        level + " does not exist in grade bundle: " + gradeBundleName + ".");
  }

  /**
   * Verifies that an error being raised is the correct one.
   * 
   * @param expectedError The expected error message.
   */
  @Then("the error {string} shall be raised \\(p12)")
  public void the_error_shall_be_raised_p12(String expectedError) {
    assertEquals(expectedError, error);
  }

  /**
   * Captures and stores the error message being outputted by the controller methods.
   * 
   * @param result The error message outputted by the controller.
   */
  public static void callController(String result) {
    if (!result.isEmpty()) {
      error += result;
      errorCntr += 1;
    }
  }
}
