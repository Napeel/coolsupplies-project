package ca.mcgill.ecse.coolsupplies.features;


import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet5Controller;
import ca.mcgill.ecse.coolsupplies.controller.TOBundleItem;
import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;
import ca.mcgill.ecse.coolsupplies.model.Grade;
import ca.mcgill.ecse.coolsupplies.model.GradeBundle;
import ca.mcgill.ecse.coolsupplies.model.Item;
import ca.mcgill.ecse.coolsupplies.model.BundleItem.PurchaseLevel;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing class for succesfully and unsuccesfully getting the a bundle item from grade bundle
 * @authors Thomas Cotterau, Ariane Carrot, Andres Gonzalez, Logan Miller, Artiom Volodin
 * 
 */

public class GetBundleItemFromBundleItemsOfGradeBundleStepDefinitions {
  /**
   * Get CoolSupplies app as an attribute to easily modify
   */
  private static CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();

  /**
   * Bundles returned to the admin when trying to retreive all items of a bundle
   */
  private List<TOBundleItem> retrievedBundleItems = new ArrayList<>();


  /**
   * @author Andres Gonzalez:
   * 
   *         <pre>
   * Step definition for "the following grade entities exists in the system \\(p11)". This method adds grade instances to coolSupplies application with
   * the name specified by the dataTable.
   * <pre>
   * @param dataTable  The rows of the data table contain the names of the grade classes
   * 
   * Example:
   * <pre>
   *  Given the following grade entities exists in the system \\(p11)
   * 
   *         </pre>
   * 
   *         Adds grade instances to the coolSupplies application
   */

  @Given("the following grade entities exists in the system \\(p11)")
  public void the_following_grade_entities_exists_in_the_system_p11(
      io.cucumber.datatable.DataTable dataTable) {

    List<Map<String, String>> rows = dataTable.asMaps();

    for (var row : rows) {
      String level = row.get("level");
      coolSupplies.addGrade(level);
    }
  }

  /**
   * @author Andres Gonzalez:
   * 
   *         <pre>
   * Step definition for "the following item entities exists in the system \\(p11)". This method adds item instances to coolSupplies application with
   * the name  and price specified by the dataTable.
   * <pre>
   * @param dataTable  The columns of the data table contain the names of the grade classes (column(0): name, column(1): price)
   * 
   * Example:
   * <pre>
   *  Given the following item entities exists in the system \\(p11)
   * 
   *         </pre>
   * 
   *         Adds item instances to the coolSupplies application
   */

  @Given("the following item entities exists in the system \\(p11)")
  public void the_following_item_entities_exists_in_the_system_p11(
      io.cucumber.datatable.DataTable dataTable) {

    List<Map<String, String>> rows = dataTable.asMaps();

    for (var row : rows) {
      String name = row.get("name");
      int price = Integer.parseInt(row.get("price"));
      coolSupplies.addItem(name, price);
    }
  }

  /**
   * @author Andres Gonzalez:
   * 
   *         <pre>
   * Step definition for "the following grade bundle entities exists in the system \\(p11)". This method adds grade bundle instances to coolSupplies application with
   * the name, discount and gradeLevel
   * <pre>
   * @param dataTable  The columns of the data table contain the names of the grade classes (column(0): name, column(1): discount,
   * column(2): gradeLevel)
   * 
   * Example:
   * <pre>
   *  Given the following grade bundle entities exists in the system \\(p11)
   *         </pre>
   * 
   *         Adds grade bundle entities to the coolSupplies application
   */

  @Given("the following grade bundle entities exists in the system \\(p11)")
  public void the_following_grade_bundle_entities_exists_in_the_system_p11(
      io.cucumber.datatable.DataTable dataTable) {

    List<Map<String, String>> rows = dataTable.asMaps();

    for (var row : rows) {
      String name = row.get("name");
      int discount = Integer.parseInt(row.get("discount"));
      Grade grade = findGrade(row.get("gradeLevel"));
      coolSupplies.addBundle(name, discount, grade);
    }
  }

  /**
   * @author Andres Gonzalez:
   * 
   *         <pre>
   * Step definition for "the following bundle item entities exists in the system \\(p11)". This method adds bundle item instances to coolSupplies application with
   * the name, discount, gradeLevel and itemName
   * <pre>
   * @param dataTable  The columns of the data table contain the names of the grade classes (column(0): name, column(1): discount,
   * column(2): gradeLevel, column(3): itemName)
   * 
   * Example:
   * <pre>
   *  Given the following bundle item entities exists in the system \\(p11)
   * 
   *         </pre>
   * 
   *         Adds bundle item entities to the coolSupplies application
   */

  @Given("the following bundle item entities exists in the system \\(p11)")
  public void the_following_bundle_item_entities_exists_in_the_system_p11(
      io.cucumber.datatable.DataTable dataTable) {

    List<Map<String, String>> rows = dataTable.asMaps();

    for (var row : rows) {
      int quantity = Integer.parseInt(row.get("quantity"));
      PurchaseLevel purchaseLevel = PurchaseLevel.valueOf(row.get("level"));
      GradeBundle gradeBundle = findGradeBundle(row.get("gradeBundleName"));
      Item item = findItem(row.get("itemName"));
      coolSupplies.addBundleItem(quantity, purchaseLevel, gradeBundle, item);
    }
  }

  /**
   * @author Thomas Cottereau
   * 
   *         Step definition for when the school admin attempts to retrieve a bundle item by its
   *         name from a specific grade bundle. This method finds the grade bundle by its name and
   *         retrieves the corresponding bundle item if it exists.
   * 
   * @param itemName The name of the bundle item the admin is attempting to retrieve.
   * @param bundleName The name of the grade bundle from which the admin is attempting to retrieve
   *        the item.
   * 
   *        Example:
   * 
   *        <pre>
   *   When the school admin attempts to get a bundle item with name "pencil" 
   *   from a grade bundle with name "Bundle 5" (p11)
   *        </pre>
   * 
   *        If the grade bundle exists, it will filter and retrieve the item by its name. If the
   *        grade bundle does not exist, the retrieved items list is cleared.
   */

  @When("the school admin attempts to get a bundle item with name {string} from a grade bundle with name {string} \\(p11)")
  public void the_school_admin_attempts_to_get_a_bundle_item_with_name_from_a_grade_bundle_with_name_p11(
      String itemName, String bundleName) {

    TOBundleItem adminItem = CoolSuppliesFeatureSet5Controller.getBundleItem(itemName, bundleName);

    if (adminItem != null) {
      retrievedBundleItems.add(adminItem);
    }
  }

  /**
   * @author Thomas Cottereau
   * 
   *         Step definition for when the school admin attempts to retrieve all bundle items from a
   *         specific grade bundle. This method finds the grade bundle by its name and retrieves all
   *         the bundle items if the grade bundle exists.
   * 
   * @param bundleName The name of the grade bundle from which the admin is attempting to retrieve
   *        all bundle items.
   * 
   *        Example:
   * 
   *        <pre>
   *   When the school admin attempts to get all bundle items from a grade bundle 
   *   with name "Bundle 5" (p11)
   *        </pre>
   * 
   *        If the grade bundle exists, it retrieves all the items associated with that bundle. If
   *        the grade bundle does not exist, the retrieved items list is cleared.
   */

  @When("the school admin attempts to get all bundle items from a grade bundle with name {string} \\(p11)")
  public void the_school_admin_attempts_to_get_all_bundle_items_from_a_grade_bundle_with_name_p11(
      String bundleName) {

    retrievedBundleItems = CoolSuppliesFeatureSet5Controller.getBundleItems(bundleName);
  }

  /**
   * @author Thomas Cottereau
   * 
   *         Step definition to verify that the number of bundle item entities in the system matches
   *         the expected value. This method compares the expected number of bundle items with the
   *         actual number of items in the system.
   * 
   * @param expectedNumber The expected number of bundle items in the system, provided as a string.
   * 
   *        Example:
   * 
   *        <pre>
   *   When the school admin attempts to get a bundle item with name "pencil" from a grade bundle with name "Bundle 5" (p11)
   *   Then the number of bundle item entities in the system shall be "4" (p11)
   *        </pre>
   * 
   *        The method asserts that the size of the bundleItems list equals the expected number.
   * 
   * @throws AssertionError if the actual number of bundle items does not match the expected number.
   */

  @Then("the number of bundle item entities in the system shall be {string} \\(p11)")
  public void the_number_of_bundle_item_entities_in_the_system_shall_be_p11(String expectedNumber) {
    int expectedSize = Integer.parseInt(expectedNumber);
    int actualSize = coolSupplies.getBundleItems().size();
    assertEquals(expectedSize, actualSize, "The expected size of bundle item entities was "
        + expectedSize + ", but is actually " + actualSize);
  }


  /**
   * @author Thomas Cottereau
   * 
   *         Step definition to verify that the expected bundle item entities are presented in the
   *         system. This method checks each expected bundle item from the provided data table and
   *         compares it with the actual items retrieved in the system.
   * 
   * @param dataTable The Cucumber data table containing the expected bundle item entities with
   *        properties such as quantity, level, gradeBundleName, and itemName.
   * 
   *        Example:
   * 
   *        <pre>
   *   When the school admin attempts to get all bundle items from a grade bundle with name "Bundle 5" (p11)
   *   Then the number of bundle item entities in the system shall be "4" (p11)
   *   Then the number of bundle item entities for grade bundle "Bundle 5" in the system shall be "3" (p11)
   *   Then the following bundle item entities shall be presented (p11)
   *      | quantity | level       | gradeBundleName | itemName |
   *      |        3 | Mandatory   | Bundle 5        | pencil   |
   *      |        2 | Optional    | Bundle 5        | eraser   |
   *      |        1 | Recommended | Bundle 5        | textbook |
   * 
   *        </pre>
   * 
   *        The method asserts that for each expected item, an item with matching quantity, level,
   *        gradeBundleName, and itemName is found in the list of retrieved bundle items.
   * 
   * @throws AssertionError if any of the expected items is not found among the retrieved bundle
   *         items.
   */


  @Then("the following bundle item entities shall be presented \\(p11)")
  public void the_following_bundle_item_entities_shall_be_presented_p11(
      io.cucumber.datatable.DataTable dataTable) {

    List<Map<String, String>> rows = dataTable.asMaps();

    for (var row : rows) {
      int expectedQuantity = Integer.parseInt(row.get("quantity"));
      String expectedPurchaseLevel = row.get("level");
      String expectedBundleName = row.get("gradeBundleName");
      String expectedItemName = row.get("itemName");

      Boolean itemFound = false;

      for (TOBundleItem bi : retrievedBundleItems) {
        if (expectedQuantity == bi.getQuantity() && expectedPurchaseLevel.equals(bi.getLevel())
            && expectedItemName.equals(bi.getItemName())
            && expectedBundleName.equals(bi.getGradeBundleName())) {
          itemFound = true;
        }
        if (itemFound) {
          break;
        }
      }

      assertTrue(itemFound, "An expected bundle item was not found");
    }
  }

  /**
   * @author Thomas Cottereau
   * 
   *         Step definition to verify the number of bundle item entities for a specific grade
   *         bundle in the system. This method checks if the number of bundle items in a given grade
   *         bundle matches the expected value.
   * 
   * @param bundleName The name of the grade bundle.
   * @param expectedNumber The expected number of bundle items in the grade bundle.
   * 
   *        Example:
   * 
   *        <pre>
   *   When the school admin attempts to get all bundle items from a grade bundle with name "Bundle 5" (p11)
   *   Then the number of bundle item entities in the system shall be "4" (p11)
   *   Then the number of bundle item entities for grade bundle "Bundle 5" in the system shall be "3" (p11)
   *        </pre>
   * 
   *        If the grade bundle exists, it checks the actual number of items in the bundle. If the
   *        grade bundle does not exist, it checks that the expected number is 0.
   * 
   * @throws AssertionError if the actual number of items does not match the expected number.
   */

  @Then("the number of bundle item entities for grade bundle {string} in the system shall be {string} \\(p11)")
  public void the_number_of_bundle_item_entities_for_grade_bundle_in_the_system_shall_be_p11(
      String bundleName, String expectedNumber) {

    GradeBundle gradeBundle = findGradeBundle(bundleName);

    int expectedSize = Integer.parseInt(expectedNumber);
    int actualSize;
    if (gradeBundle != null) {
      actualSize = gradeBundle.getBundleItems().size();
    } else {
      actualSize = 0;
    }
    assertEquals(expectedSize, actualSize, "The expected number of items in the grade bundle was "
        + expectedSize + ", but is actually " + actualSize);
  }


  /**
   * @author Thomas Cottereau
   * 
   *         Step definition to verify that no bundle item entities are presented in the system.
   *         This method asserts that the list of retrieved bundle items is empty, meaning no items
   *         should be present.
   * 
   *         Example:
   * 
   *         <pre>
   *   When the school admin attempts to get a bundle item with name "pencil" from a grade bundle with name "Bundle 7" (p11)
   *   Then the number of bundle item entities in the system shall be "4" (p11)
   *   Then no bundle item entities shall be presented (p11)
   *         </pre>
   * 
   *         The method checks if the `retrievedBundleItems` list is empty and raises an assertion
   *         error if any items are present.
   * 
   * @throws AssertionError if the `retrievedBundleItems` list is not empty.
   */


  @Then("no bundle item entities shall be presented \\(p11)")
  public void no_bundle_item_entities_shall_be_presented_p11() {
    assertTrue(retrievedBundleItems == null || retrievedBundleItems.isEmpty(), "A bundle item was not expected, but at least one was retrieved");
  }

  /* Helper Find Methods */



  /**
   * @author Artiom Volodin
   *
   * Finds and returns the Grade object that corresponds to the provided grade level.
   * This method iterates through the list of Grade entities in the CoolSupplies system
   * and returns the first match based on the provided grade level.
   *
   * @param gradeLevel the level of the grade to search for ( e.g., 1, 2,3,etc)
   *
   *        Example:
   *
   *        <pre>
   *            If you search for grade 1, you should get an object with grade 1
   *            if you search for grade 20, you get null because no such grade exists
   *        </pre>
   *
   * @return the Grade object that matches the provided grade level or null if no match is found
   *
   */

  private Grade findGrade(String gradeLevel) {
    List<Grade> gradeList = coolSupplies.getGrades();
    for (Grade grade : gradeList) {
      if (grade.getLevel().equals(gradeLevel)) {
        return grade;
      }
    }
    return null;
  }

  /**
   * @author Artiom Volodin
   * Finds and returns the GradeBundle object that corresponds to the provided bundle name.
   * This method iterates through the list of GradeBundle entities in the CoolSupplies system
   * and returns the first match based on the provided bundle name.
   *
   * @param bundleName the name of the grade bundle to search for
   *
   *
   *        Example:
   *
   *        <pre>
   *            If you search for "Bundle 5", you should get the object that is Bundle 5.
   *            if you search for "Bundle school", you get null because no such bundles exist
   *            as bundles in the system are numbered not named with words.
   *        </pre>
   *
   * @return the GradeBundle object that matches the provided bundle name or null if no match is found
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
   *
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
   *            If you search for "pencil" you should get an object representing a pencil.
   *            If you search for "tires", you will get null because tires are not a school supply and therefore
   *            not on the list.
   *        </pre>
   *
   * @return the Item object that matches the provided item name or null if no match is found
   */


  private Item findItem(String itemName) {
    List<Item> ItemList = coolSupplies.getItems();
    for (Item item : ItemList) {
      if (item.getName().equals(itemName)) {
        return item;
      }
    }
    return null;
  }

}
