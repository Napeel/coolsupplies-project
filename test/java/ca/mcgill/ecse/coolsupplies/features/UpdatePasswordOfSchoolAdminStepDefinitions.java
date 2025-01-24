package ca.mcgill.ecse.coolsupplies.features;

import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet1Controller;
import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;
import ca.mcgill.ecse.coolsupplies.model.SchoolAdmin;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.util.List;
import java.util.Map;

public class UpdatePasswordOfSchoolAdminStepDefinitions {
  /**
   * required for checking error thrown.
   * set this in a try block in "the_school_admin_attempts_to_update_school_admin_in_the_system_with_password_p7"
   */
  private String thrownException;
  private CoolSupplies coolSupplies;

  @Given("the following school admin entities exists in the system \\(p7)")
  public void the_following_school_admin_entities_exists_in_the_system_p7(
          io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> maps = dataTable.asMaps(String.class, String.class);
    if (maps.isEmpty()) return;
    Map<String, String> map = maps.get(maps.size()-1);
    coolSupplies = CoolSuppliesApplication.getCoolSupplies();
    if (!coolSupplies.hasAdmin()) {
      new SchoolAdmin(map.get("email"), map.get("password"), coolSupplies);
    } else {
      coolSupplies.getAdmin().setEmail(map.get("email"));
      coolSupplies.getAdmin().setPassword(map.get("password"));
    }
  }

  @When("the school admin attempts to update school admin {string} in the system with password {string} \\(p7)")
  public void the_school_admin_attempts_to_update_school_admin_in_the_system_with_password_p7(
          String email, String password) {
    thrownException = null;
    if (coolSupplies.getAdmin().getEmail().equals(email))
      thrownException = CoolSuppliesFeatureSet1Controller.updateAdmin(password);
    else 
      thrownException = "The admin does not exist.";
  }

  @Then("the number of school admin entities in the system shall be {string} \\(p7)")
  public void the_number_of_school_admin_entities_in_the_system_shall_be_p7(
          String noOfAdmins) {
    if (coolSupplies.hasAdmin()) Assert.assertEquals(noOfAdmins, "1");
    else Assert.assertEquals(noOfAdmins, "0");
  }

  @Then("the school admin {string} with password {string} shall not exist in the system \\(p7)")
  public void the_school_admin_with_password_shall_not_exist_in_the_system_p7(
          String email, String password) {
    if (coolSupplies.hasAdmin())
      Assert.assertFalse(coolSupplies.getAdmin().getEmail().equals(email) &&
              coolSupplies.getAdmin().getPassword().equals(password));
  }

  @Then("the following school admin entities shall exist in the system \\(p7)")
  public void the_following_school_admin_entities_shall_exist_in_the_system_p7(
          io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
    Assert.assertTrue(coolSupplies.hasAdmin());
    for (var row : rows) {
      Assert.assertEquals(row.get("email"), coolSupplies.getAdmin().getEmail());
      Assert.assertEquals(row.get("password"), coolSupplies.getAdmin().getPassword());
    }
  }

  @Then("the error {string} shall be raised \\(p7)")
  public void the_error_shall_be_raised_p7(
          String expectedException) {
    Assert.assertEquals(expectedException, thrownException);
  }

  @Then("the school admin {string} with password {string} shall exist in the system \\(p7)")
  public void the_school_admin_with_password_shall_exist_in_the_system_p7(
          String email, String password) {
    Assert.assertTrue(coolSupplies.hasAdmin());
    Assert.assertEquals(email, coolSupplies.getAdmin().getEmail());
    Assert.assertEquals(password, coolSupplies.getAdmin().getPassword());
  }
}
