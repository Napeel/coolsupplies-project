package ca.mcgill.ecse.coolsupplies.application;

import java.sql.Date;

import ca.mcgill.ecse.coolsupplies.javafx.fxml.CoolSuppliesFxmlView;
import ca.mcgill.ecse.coolsupplies.javafx.fxml.LogInPageController;
import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;
import ca.mcgill.ecse.coolsupplies.persistence.CoolSuppliesPersistence;
import java.time.LocalDate;
import javafx.application.Application;

public class CoolSuppliesApplication {

  private static CoolSupplies coolSupplies;
  public static final boolean DARK_MODE = false;
  private static Date currentDate;

  /**
   * Main entry point.
   *
   * @param args
   */
  public static void main(String[] args) {
    // start UI
    Application.launch(CoolSuppliesFxmlView.class, args);
  }

  public static CoolSupplies getCoolSupplies() {
    if (coolSupplies == null) {
      // load model
      coolSupplies = CoolSuppliesPersistence.load();
    }
    return coolSupplies;
  }

  public static Date getCurrentDate() {
    if (currentDate == null) {
      return Date.valueOf(LocalDate.now());
    }

    return currentDate;
  }

  public static void setCurrentDate(Date date) {
    currentDate = date;
  }

}
