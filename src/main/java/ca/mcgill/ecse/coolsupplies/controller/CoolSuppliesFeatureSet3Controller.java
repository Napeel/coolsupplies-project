package ca.mcgill.ecse.coolsupplies.controller;

import java.util.List;
import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;
import ca.mcgill.ecse.coolsupplies.model.Item;
import ca.mcgill.ecse.coolsupplies.persistence.CoolSuppliesPersistence;
import java.util.ArrayList;

public class CoolSuppliesFeatureSet3Controller {

  /**
   * Public Helper Method to add an item to the inventory
   * 
   * @param name the name of the item to be added
   * @param price the price of the item to be added
   * @return null if the item was added successfully, else an error message
   * 
   * @author Matthew Petruzziello
   */
  public static String addItem(String name, int price) {
    if (name == null || name.trim().isEmpty()) {
      return "The name must not be empty.";
    }

    if (price < 0) {
      return "The price must be greater than or equal to 0.";
    }

    CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();

    for (Item item : coolSupplies.getItems()) {
      if (item.getName().equals(name)) {
        return "The name must be unique.";
      }
    }

    try {
      coolSupplies.addItem(name, price);
      CoolSuppliesPersistence.save();
      return "";
    } catch (RuntimeException e) {
      return e.getMessage();
    }

  }

  /**
   * Public Helper Method to update an item in the inventory
   * 
   * @param name the name of the item to be updated
   * @param newName the new name of the item to be updated
   * @param newPrice the new price of the item to be updated
   * @return null if the item was updated successfully, else an error message
   * 
   * @author Matthew Petruzziello
   */
  public static String updateItem(String name, String newName, int newPrice) {
    if (newName == null || newName.trim().isEmpty()) {
      return "The name must not be empty.";
    }

    if (newPrice < 0) {
      return "The price must be greater than or equal to 0.";
    }

    CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();
    Item itemToUpdate = null;

    for (Item item : coolSupplies.getItems()) {
      if (item.getName().equals(name)) {
        itemToUpdate = item;
        break;
      }
    }

    if (itemToUpdate == null) {
      return "The item does not exist.";
    }

    if (!name.equals(newName)) {
      for (Item item : coolSupplies.getItems()) {
        if (item.getName().equals(newName)) {
          return "The name must be unique.";
        }
      }
    }

    try {
      itemToUpdate.setName(newName);
      itemToUpdate.setPrice(newPrice);
      CoolSuppliesPersistence.save();
      return "";
    } catch (RuntimeException e) {
      return e.getMessage();
    }

  }

  /**
   * Public Helper Method to delete an item from the inventory
   * 
   * @param name the name of the item to be deleted
   * @return null if the item was deleted successfully, else an error message
   * 
   * @author Matthew Petruzziello
   */
  public static String deleteItem(String name) {
    if (name == null || name.trim().isEmpty()) {
      return "The name must not be empty.";
    }

    CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();
    Item itemToDelete = null;

    for (Item item : coolSupplies.getItems()) {
      if (item.getName().equals(name)) {
        itemToDelete = item;
        break;
      }
    }

    if (itemToDelete == null) {
      return "The item does not exist.";
    }

    try {
      itemToDelete.delete();
      CoolSuppliesPersistence.save();
      return null;
    } catch (RuntimeException e) {
      return e.getMessage();
    }

  }

  /**
   * Public Helper Method to get an item from the inventory
   * 
   * @param name the name of the item to be retrieved
   * @return a TOItem object with the name and price if the item exists, else null
   * 
   * @author Matthew Petruzziello
   */
  public static TOItem getItem(String name) {
    if (name == null || name.trim().isEmpty()) {
      return null;
    }

    CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();

    for (Item item : coolSupplies.getItems()) {
      if (item.getName().equals(name)) {
        return new TOItem(item.getName(), item.getPrice());
      }
    }

    return null;
  }

  /**
   * Public Helper Method to get all items from the inventory
   * 
   * @return a list of TOItem objects with the name and price of all items in the inventory
   * 
   * @author Matthew Petruzziello
   */
  public static List<TOItem> getItems() {
    List<TOItem> items = new ArrayList<>();
    CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();

    for (Item item : coolSupplies.getItems()) {
      items.add(new TOItem(item.getName(), item.getPrice()));
    }

    return items;
  }

}
