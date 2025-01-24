package ca.mcgill.ecse.coolsupplies.features;

import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import io.cucumber.java.After;
import io.cucumber.java.Before;

public class CommonStepDefinitions {
  /**
   * Method used to delete the current bikeSafePlus system instance before the next test. This is
   * effective for all scenerios in all feature files
   */
  @Before
  public void setup() {
    CoolSuppliesApplication.getCoolSupplies().delete();
  }

  @After
  public void tearDown() {
    CoolSuppliesApplication.getCoolSupplies().delete();
  }
}
