/**
 * UpdateParentStepDefinitions is a class used to do Gherkin Step Definitions for the method updateParent used in the Cool Supplies controller
 *
 * @author Simon Bluteau
 * @author Tyler Vuong
 * @author Kevin Nguyen (lots of edits)
 * @author Youdas Yessad (edits)
 */
package ca.mcgill.ecse.coolsupplies.features;

import java.util.List;

import org.junit.Assert;

import ca.mcgill.ecse.coolsupplies.model.Parent;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet1Controller;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class UpdateParentStepDefinitions {

  @When("the school Administrator attempts to update parent {string} in the system with password {string}, name {string}, and phone number {string} \\(p1)")
  public void the_school_administrator_attempts_to_update_parent_in_the_system_with_password_name_and_phone_number_p1(
    String email, String password, String name, String phoneNumber) {
    AddParentStepDefinitions.error = CoolSuppliesFeatureSet1Controller.updateParent(email, password, name, Integer.parseInt(phoneNumber));
  }

  @When("the school admin attempts to update parent {string} in the system with password {string}, name {string}, and phone number {string} \\(p1)")
  public void the_school_admin_attempts_to_update_parent_in_the_system_with_password_name_and_phone_number_p1(
    String email, String password, String name, String phoneNumber) {
    AddParentStepDefinitions.error = CoolSuppliesFeatureSet1Controller.updateParent(email, password, name, Integer.parseInt(phoneNumber));
  }

  @Then("the parent {string} with password {string}, name {string}, and phone number {string} shall not exist in the system \\(p1)")
  public void the_parent_with_password_name_and_phone_number_shall_not_exist_in_the_system_p1(
    String email, String password, String name, String phoneNumber) {

    Parent systemParent = (Parent) Parent.getWithEmail(email);
    if (systemParent != null) { // if the parent is not in the dictionary then the test pass
      boolean isParentExists = systemParent.getEmail().equals(email) &&
        (systemParent.getPassword().equals(password)) &&
        (systemParent.getName().equals(name)) &&
        (systemParent.getPhoneNumber() == Integer.parseInt(phoneNumber));

      Assert.assertFalse("Error: Return: <The parent exists in the system>" +
        " Expected: <shall not exist in the system>", isParentExists);
    }
  }

  @Then("the following parent entities shall exist in the system \\(p1)")
  public void the_following_parent_entities_shall_exist_in_the_system_p1(
    io.cucumber.datatable.DataTable dataTable) {
    List<List<String>> parentList = dataTable.asLists(String.class);

    for (int i = 1; i < parentList.size(); i++) {
      List<String> parent = parentList.get(i);

      Parent systemParent = (Parent) Parent.getWithEmail(parent.get(0));

      Assert.assertNotNull(systemParent);
      Assert.assertEquals(parent.get(0), systemParent.getEmail());
      Assert.assertEquals(parent.get(1), systemParent.getPassword());
      Assert.assertEquals(parent.get(2), systemParent.getName());
      Assert.assertEquals(Integer.parseInt(parent.get(3)), systemParent.getPhoneNumber());
    }

  }
}