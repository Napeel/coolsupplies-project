package ca.mcgill.ecse.coolsupplies.controller;

import java.util.List;
import java.util.ArrayList;
import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;
import ca.mcgill.ecse.coolsupplies.model.Grade;
import ca.mcgill.ecse.coolsupplies.model.GradeBundle;
import ca.mcgill.ecse.coolsupplies.persistence.CoolSuppliesPersistence;

public class CoolSuppliesFeatureSet4Controller {

  /**
   * Private Helper Method Finds a gradeBundle give then name of a bundle
   * 
   * @param coolSupplies The instance of the application
   * @param nameGradeBundleToFind Name of the bundle we want
   * @return gradleBundle with matching name or null if none match
   * @author Jeffrey Lim
   */
  private static GradeBundle findGradeBundle(CoolSupplies coolSupplies,
      String nameGradeBundleToFind) {
    for (GradeBundle gradeBundle : coolSupplies.getBundles()) {
      if (nameGradeBundleToFind.equals(gradeBundle.getName())) {
        return gradeBundle;
      }
    }
    return null;
  }

  /**
   * Adds bundle with the specified paramters to the current application
   * 
   * @param name New bundle's name
   * @param discount New bundle's discount amount
   * @param gradeLevel New bundle's grade level
   * @return string describing the success status of the operation
   * @author Jeffrey Lim
   */
  public static String addBundle(String name, int discount, String gradeLevel) {

    CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();

    // new GradeBundle(name,discount,coolSupplies,Grade.getWithLevel(gradeLevel));
    if (discount < 0 || discount > 100) {
      return "The discount must be greater than or equal to 0 and less than or equal to 100.";
    }
    if (name.isEmpty()) {
      return "The name must not be empty.";
    }
    for (GradeBundle GB : coolSupplies.getBundles()) {
      if (name.equals(GB.getName())) {
        return "The name must be unique.";
      }
    }
    if (Grade.getWithLevel(gradeLevel) == null) {
      return "The grade does not exist.";
    }
    if (Grade.getWithLevel(gradeLevel).hasBundle()) {
      return "A bundle already exists for the grade.";
    }
    new GradeBundle(name, discount, coolSupplies, Grade.getWithLevel(gradeLevel));
    CoolSuppliesPersistence.save();

    return "Succesfully added the bundle";
  }

  /**
   * Updates the bundle of the same name with given parameters in the current application
   * 
   * @param name Name of the bundle we want to change
   * @param newName New name of the bundle
   * @param newDiscount New discount of the bundle
   * @param newGradeLevel New gradelevel of the selected bundle
   * @return string describing the success status of the operation with the searched name
   * @author Jeffrey Lim
   */
  public static String updateBundle(String name, String newName, int newDiscount,
      String newGradeLevel) {

    CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();
    GradeBundle currentBundle = findGradeBundle(coolSupplies, name);

    if (newName.isEmpty()) {
      return "The name must not be empty.";
    }
    if (newDiscount < 0 || newDiscount > 100) {
      return "The discount must be greater than or equal to 0 and less than or equal to 100.";
    }

    if (Grade.getWithLevel(newGradeLevel) == null) {
      return "The grade does not exist.";
    }
    if (currentBundle == null) {
      return "The bundle does not exist.";
    }

    if(!name.equals(newName) && GradeBundle.hasWithName(newName)){
      return "The name must be unique.";
    }
    
    if(GradeBundle.hasWithName(newName)){
      return "A bundle already exists for the grade.";
    }
    
    if (currentBundle.setName(newName) && currentBundle.setDiscount(newDiscount)
        && currentBundle.setGrade(Grade.getWithLevel(newGradeLevel))) {
      CoolSuppliesPersistence.save();
      return "";
    }
    
    return "Failed to update bundle";
  }

  /**
   * Given a name delete bundle of the same name from the current application
   * 
   * @param name The name of the bundle we want to delete
   * @return Status of the operation
   * @author Jeffrey Lim
   */
  public static String deleteBundle(String name) {
    CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();
    GradeBundle currentBundle = findGradeBundle(coolSupplies, name);
    if (currentBundle == null) {
      return "The grade bundle does not exist.";
    }
    currentBundle.delete();
    CoolSuppliesPersistence.save();
    return "Grade Bundle deleted.";
  }

  /**
   * Gets the grade bundle using the name and gives it as a transfer object
   * 
   * @param name Name of the bundle we wat to get
   * @return gives the transfer object of the internal gradeBundle
   * @author Jeffrey Lim
   */
  public static TOGradeBundle getBundle(String name) {
    CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();
    GradeBundle gradeBundle = findGradeBundle(coolSupplies, name);
    if (gradeBundle == null) {
      return null;
    }
    int discount = gradeBundle.getDiscount();

    return new TOGradeBundle(gradeBundle.getName(), discount,
        gradeBundle.getGrade().getLevel());
  }

  // returns all bundles
  /**
   * Gets all the current grade bundles and gives it in the transfer object form.
   * 
   * @return Gives the transfer objects of the internal gradeBundles
   * @author Jeffrey Lim
   */
  public static List<TOGradeBundle> getBundles() {
    CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();
    List<TOGradeBundle> listGradeToBundle = new ArrayList<>();
    for (GradeBundle gradeBundle : coolSupplies.getBundles()) {
      TOGradeBundle currentToGradeBundle = getBundle(gradeBundle.getName());
      if (currentToGradeBundle != null) {
        listGradeToBundle.add(currentToGradeBundle);
      }
    }
    return listGradeToBundle;
  }


  /**
   * Check how many bundle items are in a given bundle
   * 
   * @param name the name of the bundle to check
   * @return the number of bundle items in the bundle
   * @author Jeffrey Lim
   */
  public static int checkBundleItemNumber(String name){
    CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();
    GradeBundle gradeBundle = findGradeBundle(coolSupplies, name);
    if (gradeBundle == null){
      return 0;
    }
    return gradeBundle.getBundleItems().size();
  }

}
