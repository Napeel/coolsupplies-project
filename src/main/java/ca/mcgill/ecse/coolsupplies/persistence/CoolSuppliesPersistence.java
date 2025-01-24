package ca.mcgill.ecse.coolsupplies.persistence;
import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;

public class CoolSuppliesPersistence {

  private static String filename = "app.data";
  private static JsonSerializer serializer = new JsonSerializer("ca.mcgill.ecse.coolsupplies");
  public static void save() {
    save(CoolSuppliesApplication.getCoolSupplies());
  }
  public static void save(CoolSupplies coolSupplies) {
    serializer.serialize(coolSupplies, filename);
  }

  public static CoolSupplies load() {
    var coolSupplies = (CoolSupplies) serializer.deserialize(filename);
    // model cannot be loaded - create empty root object
    if (coolSupplies == null) {
      coolSupplies = new CoolSupplies();
    } else {
      coolSupplies.reinitialize();
    }
    return coolSupplies;
  }

  public static void setFilename(String filename) {
	  CoolSuppliesPersistence.filename = filename;
  }

}
