package ca.mcgill.ecse.coolsupplies.features;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Utility class for managing test state and assertions in the "Add Student" and "Update Student"
 * features. This class provides methods to store and retrieve errors, as well as assert conditions
 * related to student entities.
 * 
 * @author Alexander Kudinov
 */
public class TestUtility {

  // Static variable to hold the error or other state information
  private static String error;

  /**
   * Sets the error message to be captured during the test.
   * 
   * @param errorMsg The error message to set.
   */
  public static void setError(String errorMsg) {
    error = errorMsg;
  }

  /**
   * Retrieves the currently stored error message.
   * 
   * @return The current error message.
   */
  public static String getError() {
    return error;
  }

  /**
   * Clears the stored error message.
   */
  public static void clearError() {
    error = null;
  }

  // Assertion methods

  /**
   * Asserts that the expected error message was raised.
   * 
   * @param expectedError The expected error message.
   * @param actualError The actual error message raised.
   */
  public static void assertErrorRaised(String expectedError, String actualError) {
    assertEquals(expectedError, actualError,
        "Expected error message did not match the actual error.");
  }



  /**
   * Asserts that a student with a specific name and grade level does not exist in the system.
   * 
   * @param actualName The actual name of the student.
   * @param actualGradeLevel The actual grade level of the student.
   */
  public static void assertStudentDoesNotExist(String actualName, String actualGradeLevel) {
    assertTrue(actualName == null || actualGradeLevel == null, "Expected student " + actualName
        + " with grade level " + actualGradeLevel + " not to exist, but they were found.");
  }

}
