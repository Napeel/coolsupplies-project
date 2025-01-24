package ca.mcgill.ecse.coolsupplies.javafx.fxml.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet4Controller;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet7Controller;
import ca.mcgill.ecse.coolsupplies.controller.TOGrade;
import ca.mcgill.ecse.coolsupplies.controller.TOGradeBundle;
import ca.mcgill.ecse.coolsupplies.javafx.fxml.CoolSuppliesFxmlView;
import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;
import java.util.List;
import java.util.stream.Collectors;

public class ManageBundlesController implements Refreshable{

  @FXML
  private ComboBox<String> bundleComboBox;
  @FXML
  private TextField bundleNameField;
  @FXML
  private ListView<String> bundlesListView;
  @FXML
  private Button addButton;
  @FXML
  private Button updateButton;
  @FXML
  private Button deleteButton;
  @FXML
  private Button clearButton;
  @FXML
  private TextField discountField;
  @FXML
  private ComboBox<String> gradeComboBox;
  @FXML
  private Button refreshButton;

  

  @FXML
  public void initialize() {
    refreshBundleComboBox();
    refreshBundles();
    refreshGradeLevels();

    updateButton.setDisable(true);
    deleteButton.setDisable(true);
    CoolSuppliesFxmlView.getInstance().registerRefreshEvent(gradeComboBox);
  }

  public void refresh() {
    refreshBundleComboBox();
    refreshBundles();
    refreshGradeLevels();
}

  private void refreshBundleComboBox() {
    List<TOGradeBundle> bundles = CoolSuppliesFeatureSet4Controller.getBundles();
    List<String> bundleNames =
        bundles.stream().map(TOGradeBundle::getName).collect(Collectors.toList());
    bundleComboBox.setItems(FXCollections.observableArrayList(bundleNames));
  }

  @FXML
  private void handleBundleSelection(ActionEvent event) {
    String selectedBundleName = bundleComboBox.getValue();
    if (selectedBundleName != null) {
      TOGradeBundle selectedBundle =
          CoolSuppliesFeatureSet4Controller.getBundle(selectedBundleName);
      if (selectedBundle != null) {
        bundleNameField.setText(selectedBundle.getName());
        gradeComboBox.setValue(selectedBundle.getGradeLevel());
        discountField.setText(Integer.toString(selectedBundle.getDiscount()));
        updateButton.setDisable(false);
        deleteButton.setDisable(false);
      }
    }
  }

  @FXML
  private void handleUpdateBundle(ActionEvent event) {
    String selectedBundleName = bundleComboBox.getValue();
    if (selectedBundleName == null) {
      showError("Please select a bundle to update");
      return;
    }
    if (!validateInputs())
      return;

    try {
      String newName = bundleNameField.getText().trim();
      String newGrade = gradeComboBox.getValue();
      String discountText = discountField.getText();

      int discount = 0;

      try {
        discount = Integer.parseInt(discountText);
        if (discount < 0) {
          showError("Please enter a positive number for the discount.");
        }
      } catch (NumberFormatException e) {
          showError("Invalid number format. Please enter a valid integer.");
      }

      String error = CoolSuppliesFeatureSet4Controller.updateBundle(selectedBundleName, newName, discount,
          String.valueOf(newGrade));

      if (error.isEmpty()) {
        handleClearFields(event);
        refreshBundleComboBox();
        refreshBundles();
        refreshGradeLevels();
        updateButton.setDisable(true);
        deleteButton.setDisable(true);
      } else {
        showError(error);
      }
    } catch (NumberFormatException e) {
      showError("Grade must be a valid number");
    }
  }

  @FXML
  private void handleDeleteBundle(ActionEvent event) {
    String selectedBundleName = bundleComboBox.getValue();
    if (selectedBundleName == null) {
      showError("Please select a bundle to delete");
      return;
    }

    if (confirmDelete(selectedBundleName)) {
      String error = CoolSuppliesFeatureSet4Controller.deleteBundle(selectedBundleName);
      if (error.equals("Grade Bundle deleted.")) {
        handleClearFields(event);
        refreshBundleComboBox();
        refreshBundles();
        updateButton.setDisable(true);
        deleteButton.setDisable(true);
      } else {
        showError(error);
      }
    }
  }

  private boolean confirmDelete(String bundleName) {
    Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
    confirm.setTitle("Confirm Delete");
    confirm.setHeaderText("Delete Bundle");
    confirm.setContentText("Are you sure you want to delete " + bundleName + "?");
    return confirm.showAndWait().get() == ButtonType.OK;
  }

  @FXML
  private void handleClearFields(ActionEvent event) {
    bundleNameField.clear();
    gradeComboBox.setValue(null);
    discountField.clear();
    bundleComboBox.setValue(null);
    updateButton.setDisable(true);
    deleteButton.setDisable(true);
  }

  private boolean validateInputs() {
    if (bundleNameField.getText().trim().isEmpty()) {
      showError("Bundle name cannot be empty");
      return false;
    }
    if (gradeComboBox.getValue().trim().isEmpty()) {
      showError("Grade cannot be empty");
      return false;
    }
    return true;
  }

  private void refreshBundles() {
    List<TOGradeBundle> bundles = CoolSuppliesFeatureSet4Controller.getBundles();
    ObservableList<String> bundleDetails = FXCollections.observableArrayList();

    for (TOGradeBundle bundle : bundles) {
      int discount = bundle.getDiscount();
      if (CoolSuppliesFeatureSet4Controller.checkBundleItemNumber(bundle.getName()) < 2){
        discount = 0;
      }
      StringBuilder bundleInfo = new StringBuilder().append("Bundle: ").append(bundle.getName())
          .append("\n").append("Grade Level: ").append(bundle.getGradeLevel())
          .append("\n").append("Discount: ").append(discount)
          .append("\n").append("----------------------------------------");
      bundleDetails.add(bundleInfo.toString());
    }

    bundlesListView.setItems(bundleDetails);
  }

  private void showError(String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error");
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }

  @FXML
  private void handleAddBundle(ActionEvent event) {
    if (!validateInputs()) {
      return;
    }

    String discountText = discountField.getText().trim();
    int discount = 0;

    try {
      discount = Integer.parseInt(discountText);
      if (discount < 0) {
        showError("Please enter a positive number for the discount.");
      }
    } catch (NumberFormatException e) {
        showError("Invalid number format. Please enter a valid integer.");
    }

    try {
      String name = bundleNameField.getText().trim();
      String grade = gradeComboBox.getValue().trim();
       

      String error = CoolSuppliesFeatureSet4Controller.addBundle(name, discount, grade);
      
      if (error.equals("Succesfully added the bundle")) {
        handleClearFields(event);
        refreshBundleComboBox();
        refreshBundles();
      } else {
        showError(error);
      }
    } catch (NumberFormatException e) {
      showError("Grade must be a valid number");
    }
  }

  private void refreshGradeLevels() {

    List<TOGrade> gradeLevels = CoolSuppliesFeatureSet7Controller.getGrades();

    ObservableList<String> gradeLevelNames = FXCollections.observableArrayList();
    for (TOGrade gradeLevel : gradeLevels) {
        gradeLevelNames.add(gradeLevel.getLevel());
    }

    gradeComboBox.getItems().clear();
    gradeComboBox.setItems(gradeLevelNames);
  }

  @FXML
  private void handleUpdate(){
    refresh();
  }
}
