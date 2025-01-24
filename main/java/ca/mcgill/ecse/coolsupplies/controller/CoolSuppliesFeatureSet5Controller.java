package ca.mcgill.ecse.coolsupplies.controller;

import java.util.List;
import java.util.ArrayList;
import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;
import ca.mcgill.ecse.coolsupplies.model.GradeBundle;
import ca.mcgill.ecse.coolsupplies.model.BundleItem;
import ca.mcgill.ecse.coolsupplies.model.BundleItem.PurchaseLevel;
import ca.mcgill.ecse.coolsupplies.model.Item;
import ca.mcgill.ecse.coolsupplies.persistence.CoolSuppliesPersistence;

public class CoolSuppliesFeatureSet5Controller {

  public static CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();

  /**
   * Private Helper Method that returns the gradeBundle associated with the given name
   * 
   * @param bundleName the name of the gradeBundle to be returned
   * @return the gradeBundle associated with the name or null if none is found
   * 
   * @author Daniel Buruiana
   */
  private static GradeBundle getBundleFromName(String bundleName) {
    for (GradeBundle bundle : coolSupplies.getBundles()) {
      if (bundle.getName().equals(bundleName))
        return bundle;
    }
    return null;
  }

  /**
   * Private Helper Method that returns the item associated with the given name
   * 
   * @param itemName the name of the item to be returned
   * @return the item associated with the name or null if none is found
   * 
   * @author Daniel Buruiana
   */
  private static Item getItemFromName(String itemName) {
    for (Item item : coolSupplies.getItems()) {
      if (item.getName().equals(itemName))
        return item;
    }
    return null;
  }

  /**
   * Private Helper Method that returns the purchaseLevel associated with the given level
   * 
   * @param level the name of the level to be returned
   * @return the purchaseLevel associated with the name or null if none is found
   * 
   * @author Daniel Buruiana
   */
  private static PurchaseLevel getLevelFromName(String level) {
    for (PurchaseLevel loopLevel : PurchaseLevel.values()) {
      if (loopLevel.toString().equals(level))
        return loopLevel;
    }
    return null;
  }

  /**
   * Adds a new bundleItem to an existing gradeBundle
   * 
   * @param quantity the quantity of the bundleItem to be added
   * @param level the level of the bundleItem to be added
   * @param itemName the name of the item to be added as a bundleItem
   * @param bundleName the name of the gradeBundle in which to add the bundleItem
   * @return an empty string if bundleItem is successfully added, otherwise an error message
   * 
   * @author Daniel Buruiana
   */
  public static String addBundleItem(int quantity, String level, String itemName,
      String bundleName) {
    if (quantity < 1)
      return "The quantity must be greater than 0.";
    if (itemName == null || itemName.isEmpty())
      return "Item name must not be empty or null.";
    if (bundleName == null || bundleName.isEmpty())
      return "Bundle name must not be empty or null.";

    GradeBundle bundle = getBundleFromName(bundleName);
    if (bundle == null)
      return "The grade bundle does not exist.";

    Item item = getItemFromName(itemName);
    if (item == null)
      return "The item does not exist.";

    for (BundleItem bundleItem : bundle.getBundleItems()) {
      if (bundleItem.getItem().equals(item))
        return "The item already exists for the bundle.";
    }

    PurchaseLevel aLevel = getLevelFromName(level);
    if (aLevel == null)
      return "The level must be Mandatory, Recommended, or Optional.";

    try {
      bundle.addBundleItem(quantity, aLevel, coolSupplies, item);
      CoolSuppliesPersistence.save();
      return "";
    } catch (RuntimeException e) {
      return e.getMessage();
    }

  }

  /**
   * Updates the quantity and the level of an existing bundleItem in an existing gradeBundle
   * 
   * @param itemName the name of the item from the bundleItem to be updated
   * @param bundleName the name of the gradeBundle where to find the bundleItem
   * @param newQuantity the new quantity of the bundleItem
   * @param newLevel the new level of the bundleItem
   * @return an empty string if bundleItem is successfully updated, otherwise an error message
   * 
   * @author Daniel Buruiana
   */
  public static String updateBundleItem(String itemName, String bundleName, int newQuantity,
      String newLevel) {
    if (newQuantity < 1)
      return "The quantity must be greater than 0.";
    if (itemName == null || itemName.isEmpty())
      return "Item name must not be empty or null";
    if (bundleName == null || bundleName.isEmpty())
      return "Bundle name must not be empty or null";

    GradeBundle bundle = getBundleFromName(bundleName);
    if (bundle == null)
      return "The grade bundle does not exist.";

    Item item = getItemFromName(itemName);
    if (item == null)
      return "The item does not exist.";

    PurchaseLevel aLevel = getLevelFromName(newLevel);
    if (aLevel == null)
      return "The level must be Mandatory, Recommended, or Optional.";

    BundleItem bundleItem = null;
    for (BundleItem loopBundleItem : bundle.getBundleItems()) {
      if (loopBundleItem.getItem().equals(item)) {
        bundleItem = loopBundleItem;
        break;
      }
    }
    if (bundleItem == null)
      return "The bundle item does not exist for the grade bundle.";

    try {
      bundleItem.setQuantity(newQuantity);
      bundleItem.setLevel(aLevel);
      CoolSuppliesPersistence.save();
      return "";
    } catch (RuntimeException e) {
      return e.getMessage();
    }
  }

  /**
   * Deletes an existing bundleItem from an existing gradeBundle
   * 
   * @param itemName the name of the item from the bundleItem to be deleted
   * @param bundleName the name of the gradeBundle where to find the bundleItem
   * @return an empty string if bundleItem is successfully deleted, otherwise an error message
   * 
   * @author Daniel Buruiana
   */
  public static String deleteBundleItem(String itemName, String bundleName) {
    if (itemName == null || itemName.isEmpty())
      return "Item name must not be empty or null";
    if (bundleName == null || bundleName.isEmpty())
      return "Bundle name must not be empty or null";

    GradeBundle bundle = getBundleFromName(bundleName);
    if (bundle == null)
      return "The grade bundle does not exist.";

    Item item = getItemFromName(itemName);
    if (item == null)
      return "The bundle item does not exist.";

    BundleItem bundleItem = null;
    for (BundleItem loopBundleItem : bundle.getBundleItems()) {
      if (loopBundleItem.getItem().equals(item)) {
        bundleItem = loopBundleItem;
        break;
      }
    }
    if (bundleItem == null)
      return "The bundle item does not exist.";

    try {
      bundleItem.delete();
      CoolSuppliesPersistence.save();
      return "";
    } catch (RuntimeException e) {
      return e.getMessage();
    }
  }

  /**
   * Retrieves a specific bundleItem from a gradeBundle
   * 
   * @param itemName the name of the item from the bundleItem to be retrieved
   * @param bundleName the name of the gradeBundle where to find the bundleItem
   * @return a TOBundleItem of the requested bundleItem or null if it is not found
   * 
   * @author Daniel Buruiana
   */
  public static TOBundleItem getBundleItem(String itemName, String bundleName) {
    if (itemName == null || itemName.isEmpty())
      return null;
    if (bundleName == null || bundleName.isEmpty())
      return null;

    GradeBundle bundle = getBundleFromName(bundleName);
    if (bundle == null)
      return null;

    Item item = getItemFromName(itemName);
    if (item == null)
      return null;

    for (BundleItem bundleItem : bundle.getBundleItems()) {
      if (bundleItem.getItem().equals(item)) {
        int TOquantity = bundleItem.getQuantity();
        String TOlevel = bundleItem.getLevel().name();
        String TOitemName = item.getName();
        String TObundleName = bundle.getName();

        return new TOBundleItem(TOquantity, TOlevel, TOitemName, TObundleName);
      }
    }

    return null;
  }

  /**
   * Retrieves a list of all the bundleItems from a specific gradeBundle
   * 
   * @param bundleName the name of the gradeBundle from which all the bundleItems are to be
   *        retrieved
   * @return a list of TOBundleItems representing all the bundleItems in the gradeBundle
   * 
   * @author Daniel Buruiana
   */
  public static List<TOBundleItem> getBundleItems(String bundleName) {
    if (bundleName == null || bundleName.isEmpty())
      return null;

    List<TOBundleItem> bundleItems = new ArrayList<>();

    GradeBundle bundle = getBundleFromName(bundleName);
    if (bundle == null)
      return null;

    for (BundleItem bundleItem : bundle.getBundleItems()) {
      int TOquantity = bundleItem.getQuantity();
      String TOlevel = bundleItem.getLevel().name();
      String TOitemName = bundleItem.getItem().getName();
      String TObundleName = bundle.getName();
      bundleItems.add(new TOBundleItem(TOquantity, TOlevel, TOitemName, TObundleName));
    }

    return bundleItems;
  }

}
