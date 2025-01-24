/**
 * ADdParentStepDefinitions is a class used to do Gherkin Step Definitions for the method addParent used in the Cool Supplies controller
 *
 * @author Kevin Nguyen
 */

package ca.mcgill.ecse.coolsupplies.features;

import java.util.List;

import org.junit.Assert;

import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet1Controller;
import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;
import ca.mcgill.ecse.coolsupplies.model.Parent;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


public class AddParentStepDefinitions {
  static String error = "";

  @Given("the following parent entities exists in the system \\(p1)")
  public void the_following_parent_entities_exists_in_the_system_p1(
    io.cucumber.datatable.DataTable dataTable) {
    CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();

    //convert datatable to list of lists
    List<List<String>> parentList = dataTable.asLists(String.class);
    for (int i = 1; i < parentList.size(); i++) {
      List<String> parent = parentList.get(i);
      //get parent details from datatable
      String email = parent.get(0);
      String password = parent.get(1);
      String name = parent.get(2);
      int phoneNumber = Integer.parseInt(parent.get(3));
      //add parents
      coolSupplies.addParent(new Parent(email, password, name, phoneNumber, coolSupplies));
    }
  }

  @When("the parent attempts to add a new parent in the system with email {string}, password {string}, name {string}, and phone number {string} \\(p1)")
  public void the_parent_attempts_to_add_a_new_parent_in_the_system_with_email_password_name_and_phone_number_p1(

    String email, String password, String name, String phoneNumber) {
    error = CoolSuppliesFeatureSet1Controller.addParent(email, password, name, Integer.parseInt(phoneNumber));
  }

  @Then("the number of parent entities in the system shall be {string} \\(p1)")
  public void the_number_of_parent_entities_in_the_system_shall_be_p1(String numberOfParent) {

    CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();
    Assert.assertEquals(Integer.parseInt(numberOfParent), coolSupplies.numberOfParents());
  }

  @Then("the error {string} shall be raised \\(p1)")
  public void the_error_shall_be_raised_p1(String correctError) {
    //System.out.println("the error is" + error);
    Assert.assertEquals(correctError, error);
  }

  @Then("the parent {string} with password {string}, name {string}, and phone number {string} shall exist in the system \\(p1)")
  public void the_parent_with_password_name_and_phone_number_shall_exist_in_the_system_p1(
    String email, String password, String name, String phoneNumber) {

    Parent systemParent = (Parent) Parent.getWithEmail(email);

    Assert.assertNotNull(systemParent);
    Assert.assertEquals(email, systemParent.getEmail());
    Assert.assertEquals(password, systemParent.getPassword());
    Assert.assertEquals(name, systemParent.getName());
    Assert.assertEquals(Integer.parseInt(phoneNumber), systemParent.getPhoneNumber());
    //Asserting that the parent does exist in the system
  }


}
