package ca.mcgill.ecse.coolsupplies.controller;

import ca.mcgill.ecse.coolsupplies.model.Parent;
import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;
import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.persistence.CoolSuppliesPersistence;
import java.util.List;
import java.util.ArrayList;

public class CoolSuppliesFeatureSet1Controller {

  /**
   * This is the implementation of the FeatureSet1 Controller
   * 
   * @author Yarno Grenier
   * 
   */

  public static CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();

  /**
   * 
   * Checks if the admin's password is formatted according to constraints and updates it
   * 
   * @param password the admin's new password
   * @return Success message if password is valid else error message
   */
  public static String updateAdmin(String password) {
    boolean containsSpecialChar = false;
    boolean containsLowerCase = false;
    boolean containsUpperCase = false;
    if (password == null || password.isEmpty()) {
      return "The password must not be empty.";
    }

    if (password.length() <= 3) {
      return "Password must be at least four characters long.";
    }

    for (char c : password.toCharArray()) {
      if (c == '!' || c == '$' || c == '#') {
        containsSpecialChar = true;
      } else if (Character.isLowerCase(c)) {
        containsLowerCase = true;
      } else if (Character.isUpperCase(c)) {
        containsUpperCase = true;
      }
    }
    if (!containsSpecialChar || !containsLowerCase || !containsUpperCase) {
      return "Password must contain a special character out of !#$, an upper case character, and a lower case character.";
    }

    coolSupplies.getAdmin().setPassword(password);
    CoolSuppliesPersistence.save();
    return "Successfully updated admin's password";
  }

  /**
   * 
   * Checks if email, password, name and phone number is formatted properly, then creates a parent
   * if yes
   * 
   * @param email the parent's email
   * @param password the parent's password
   * @param name the parent's name
   * @param phoneNumber the parent's phoneNumber
   * @return Success message if parameters are valid else error message
   */
  public static String addParent(String email, String password, String name, int phoneNumber) {
    for (TOParent p : getParents()) {
      if (p.getEmail().equals((email))) {
        return "The email must be unique.";
      }
    }
    if (email == null || email.isEmpty()) {
      return "The email must not be empty.";
    }
    if (email.contains(" ")) {
      return "The email must not contain spaces.";
    }

    if (email.indexOf("@") <= 0 || email.indexOf("@") != email.lastIndexOf("@")
        || email.indexOf("@") >= email.lastIndexOf(".") - 1
        || email.lastIndexOf(".") >= email.length() - 1) {
      return "The email must be well-formed.";
    }
    if (email.equals("admin@cool.ca")) {
      return "The email must not be admin@cool.ca.";
    }
    if (name == null || name.isEmpty()) {
      return "The name must not be empty.";
    }
    if (phoneNumber <= 999999 || phoneNumber >= 10000000) {
      return "The phone number must be seven digits.";
    }
    if (password == null || password.isEmpty()) {
      return "The password must not be empty.";
    }
    coolSupplies.addParent(email, password, name, phoneNumber);
    CoolSuppliesPersistence.save();
    return "Successfully added parent";
  }

  /**
   * 
   * Checks if email exists in database, as well as if new password, name and phone number are
   * formatted properly, then updates the parent if yes
   * 
   * @param email the parent's new email
   * @param newPassword the parent's new password
   * @param newName the parent's new name
   * @param newPhoneNumber the parent's new phoneNumber
   * @return Success message if parameters are valid else error message
   */
  public static String updateParent(String email, String newPassword, String newName,
      int newPhoneNumber) {

    if (email.contains(" ") || email.indexOf("@") <= 0
        || email.indexOf("@") != email.lastIndexOf("@")
        || email.indexOf("@") >= email.lastIndexOf(".") - 1
        || email.lastIndexOf(".") >= email.length() - 1 || email.equals("admin@cool.ca")) {
      return "Parent not found";
    }
    if (newName == null || newName.isEmpty()) {
      return "The name must not be empty.";
    }
    if (newPhoneNumber <= 999999 || newPhoneNumber >= 10000000) {
      return "The phone number must be seven digits.";
    }
    if (newPassword == null || newPassword.isEmpty()) {
      return "The password must not be empty.";
    }
    boolean parentFound = false;
    for (Parent p : coolSupplies.getParents()) {
      if (p.getEmail().equals(email)) {
        p.setName(newName);
        p.setPassword(newPassword);
        p.setPhoneNumber(newPhoneNumber);
        parentFound = true;
        CoolSuppliesPersistence.save();
        break;
      }
    }
    return parentFound ? "Successfully updated parent" : "The parent does not exist.";
  }

  /**
   * Checks if email exists in database, deletes the parent if yes
   * 
   * @param email the parent's email
   * @return Success message if parameter is valid else error message
   */
  public static String deleteParent(String email) {
    boolean parentDeleted = false;
    for (Parent p : coolSupplies.getParents()) {
      if (p.getEmail().equals(email)) {
        p.delete();
        CoolSuppliesPersistence.save();
        parentDeleted = true;
        break;
      }
    }
    return parentDeleted ? "Parent successfully deleted" : "The parent does not exist.";
  }

  /**
   * Checks if email exists in database, returns a transfer parent object if yes
   * 
   * @param email the parent's email
   * @return TOParent object if email found, null TOParent if not
   */
  public static TOParent getParent(String email) {
    Parent parent = null;
    for (Parent p : coolSupplies.getParents()) {
      if (p.getEmail().equals(email)) {
        parent = p;
        break;
      }
    }

    if (parent == null)
      return null;
    return new TOParent(parent.getEmail(), parent.getPassword(), parent.getName(),
        parent.getPhoneNumber());
  }

  /**
   * Creates an ArrayList of Parent Transfer Objects
   * 
   * @return TOParent object ArrayList
   */
  public static List<TOParent> getParents() {
    List<TOParent> parentList = new ArrayList<>();
    for (Parent p : coolSupplies.getParents()) {
      parentList.add(new TOParent(p.getEmail(), p.getPassword(), p.getName(), p.getPhoneNumber()));
    }
    return parentList;
  }
}
