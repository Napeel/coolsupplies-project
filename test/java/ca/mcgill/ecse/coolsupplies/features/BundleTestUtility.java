package ca.mcgill.ecse.coolsupplies.features;

/**
 * @author Maximilian Bergmair
 */
public class BundleTestUtility {

  private static String error;

  public static void setError(String errorMsg) {
    error = errorMsg;
  }

  public static String getError() {
    return error;
  }

  public static void clearError() {
    error = null;
  }

}