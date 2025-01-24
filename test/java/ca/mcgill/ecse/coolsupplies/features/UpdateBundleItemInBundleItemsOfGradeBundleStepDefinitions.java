package ca.mcgill.ecse.coolsupplies.features;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.util.List;
import java.util.Map;
import org.checkerframework.common.value.qual.BoolVal;
import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet5Controller;
import ca.mcgill.ecse.coolsupplies.model.BundleItem;
import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;
import ca.mcgill.ecse.coolsupplies.model.GradeBundle;
import ca.mcgill.ecse.coolsupplies.model.Item;
import ca.mcgill.ecse.coolsupplies.model.BundleItem.PurchaseLevel;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * Gherking step definitons for updating bundle items in grade bundles.
 * 
 * The following class contains the step definitions required for the gherkin
 * scenarios that are related to updating a bundle item in grade bundles.
 * Co-authored by two team members. All the methods were co-authored by the 
 * listed authors.
 * 
 * @author Ruiyang Zhang
 * @author Céline Shao
 */
public class UpdateBundleItemInBundleItemsOfGradeBundleStepDefinitions {

  /**
   * Updates the bundle item in grade bundle.
   * 
   * @param bundleItem The name of the bundle item being updated.
   * @param gradeBundle The name of the grade bundle that contains the bundle item being updated.
   * @param quantity The new quantity of the the bundle item being updated.
   * @param level The new purchase level of the bundle item being updated.
   */
  @When("the school admin attempts to update a bundle item {string} of grade bundle {string} with quantity {string} and level {string} \\(p12)")
  public void the_school_admin_attempts_to_update_a_bundle_item_of_grade_bundle_with_quantity_and_level_p12(
      String bundleItem, String gradeBundle, String quantity, String level) {
      int intQuantity = Integer.parseInt(quantity);
      AddBundleItemToBundleItemsOfGradeBundleStepDefinitions.error = "";
      AddBundleItemToBundleItemsOfGradeBundleStepDefinitions.errorCntr = 0;
      AddBundleItemToBundleItemsOfGradeBundleStepDefinitions.callController(CoolSuppliesFeatureSet5Controller.updateBundleItem(bundleItem, gradeBundle, intQuantity, level));
  }

  /**
   * Verifies that a bundle item with given quantity, level, and name does not exist in a gradle bundle
   * with given name.
   * 
   * @param quantity Quantity of the bundle item that should not exist.
   * @param level Purchase level of the bundle item that should not exist.
   * @param itemName Name of the bundle item that should not exist.
   * @param gradeBundle Name of the grade bundle that we are checking for the bundle item in.
   */
  @Then("the bundle item with quantity {string}, level {string}, and item name {string} shall not exist for grade bundle {string} in the system \\(p12)")
  public void the_bundle_item_with_quantity_level_and_item_name_shall_not_exist_for_grade_bundle_in_the_system_p12(
      String quantity, String level, String itemName, String gradeBundle) {
    CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();
    int intQuantity = Integer.parseInt(quantity);

    for (GradeBundle bundle : coolSupplies.getBundles()) {
      if (bundle.getName().equals(gradeBundle)) {
        for (BundleItem item : bundle.getBundleItems()) {
          if (item.getLevel().name().equals(level) && item.getQuantity() == intQuantity 
          && item.getItem().getName().equals(itemName) ) {
            fail("Bundle item with quantity: " + quantity + ", level: " + level + ", item name: "
            + itemName + ", exists in grade bundle: " + gradeBundle + " when it shouldn't.");
          }
        }
      }
    }
  }

  /**
   * Verifies that a given bundle item exists in the CoolSupplies system.
   * 
   * @param dataTable The datatable from the gherkin scenarios for the bundle item we are verifying.
   *                  Each row represents a bundle item, with columns "quantity" that specifies quantity,
   *                  "level" that specifies purchase level, "gradeBundleName" that specifies the name of
   *                  the bundle, and "itemName" that specifies the name of the bundle item.
   */
  @Then("the following bundle item entities shall exist in the system \\(p12)")
  public void the_following_bundle_item_entities_shall_exist_in_the_system_p12(
      io.cucumber.datatable.DataTable dataTable) {

    CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();
    List<Map<String, String>> bundleItems = dataTable.asMaps();
    Boolean testSuccess = false;

    for (var bundleItem : bundleItems) {
      int quantity = Integer.parseInt(bundleItem.get("quantity"));
      String level = bundleItem.get("level");
      String bundleName = bundleItem.get("gradeBundleName");
      String itemName = bundleItem.get("itemName");
      List<GradeBundle> bundles = coolSupplies.getBundles();
      for (GradeBundle bundle : bundles) {
        if (bundle.getName().equals(bundleName)) {
          for (BundleItem item : bundle.getBundleItems()) {
            if (item.getLevel().equals(PurchaseLevel.valueOf(level)) && item.getQuantity() == quantity 
            && item.getItem().getName().equals(itemName) ) {
            testSuccess = true;
            break;
            }
          }
          if(testSuccess) {
            break;
          }
        }
      }
      if (!testSuccess) {
        fail("The expected bundle item: " + itemName + " with quantity: " + quantity +
        " and purchase level: " + level + " does not exist in grade bundle: " + bundleName);
      }
    }
  }
}
