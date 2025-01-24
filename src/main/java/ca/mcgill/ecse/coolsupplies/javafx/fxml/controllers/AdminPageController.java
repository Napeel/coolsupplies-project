package ca.mcgill.ecse.coolsupplies.javafx.fxml.controllers;

import java.time.LocalDate;
import java.util.List;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet1Controller;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet7Controller;
import ca.mcgill.ecse.coolsupplies.controller.TOGrade;
import ca.mcgill.ecse.coolsupplies.javafx.fxml.CoolSuppliesFxmlView;
import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;
import ca.mcgill.ecse.coolsupplies.persistence.CoolSuppliesPersistence;

public class AdminPageController implements Refreshable{

    @FXML private Label currentSchoolYear; 
    @FXML private Button startSchoolYearButton;
    @FXML private Button changeAdminPasswordButton;
    @FXML private TextField newAdminPasswordField;
    @FXML private ComboBox<String> gradeComboBox;
    @FXML private TextField gradeNameField;
    @FXML private Button addGradeLevelButton;
    @FXML private Button updateGradeLevelButton;
    @FXML private Button deleteGradeLevelButton;
    @FXML private Button clearGradeLevelButton;
    
    


    /**
     * Helper Method to show the error
     */
    public void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("An Error Occurred");

        Label messageLabel = new Label(message);
        messageLabel.setWrapText(true);

        ScrollPane scrollPane = new ScrollPane(messageLabel);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefSize(400, 200);

        alert.getDialogPane().setExpandableContent(scrollPane);
        alert.getDialogPane().setExpanded(true);

        alert.showAndWait();
    }

    public void refresh(){
        refreshGradeLevels();
    }

    /**
     * Initializing the Admin Page
     */
    @FXML
    public void initialize() {
        startSchoolYearButton.setOnAction(this::handleStartSchoolYear);
        currentSchoolYear.textProperty().bind(
            Bindings.concat("Today's Date: " + LocalDate.now())
        );

        refreshGradeLevels();
        CoolSuppliesFxmlView.getInstance().registerRefreshEvent(gradeComboBox);

        // Make the ComboBox editable. Whoever did this is cooked cuz I hate comboboxes but whatever Jeff I guess YOU like CSS.
        gradeComboBox.setEditable(true);

        // Listen for changes in the ComboBox
        gradeComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                TOGrade gradeLevel = CoolSuppliesFeatureSet7Controller.getGrade(newVal);
                if (gradeLevel != null) {
                    gradeNameField.setText(gradeLevel.getLevel());
                }
            }
        });

        // Add styling to ComboBox
        gradeComboBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item);
                    setStyle("-fx-text-fill: black; -fx-background-color: white;");
                }
            }
        });

        gradeComboBox.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item);
                    setStyle("-fx-text-fill: black; -fx-background-color: white;");
                }
            }
        });
    }

    /**
     * Refreshes the grades so the dropdown menu is updated
     */
    private void refreshGradeLevels() {
        List<TOGrade> gradeLevels = CoolSuppliesFeatureSet7Controller.getGrades();
        ObservableList<String> gradeLevelNames = FXCollections.observableArrayList();
        for (TOGrade gradeLevel : gradeLevels) {
            gradeLevelNames.add(gradeLevel.getLevel());
        }
        gradeComboBox.setItems(gradeLevelNames);
    }

    /**
     * Handle for starting the school year
     */
    @FXML
    private void handleStartSchoolYear(ActionEvent event) {
        CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();

        coolSupplies.getOrders().forEach(order -> {
            try {
                order.schoolYearStarts();
            } catch (RuntimeException e) {
                // Skip orders that can't start school year
            }
        });

        CoolSuppliesPersistence.save();

        currentSchoolYear.textProperty().bind(
            Bindings.concat("School Year Started - " + LocalDate.now())
        );

        startSchoolYearButton.setDisable(true);
    }

    /**
     * Handles the change of admin password
     */
    @FXML
    public void handleChangeAdminPassword() {
        String inputText = newAdminPasswordField.getText();
        String status = CoolSuppliesFeatureSet1Controller.updateAdmin(inputText);
        if (status.equals("Successfully updated admin's password")) {
            return;
        }
        newAdminPasswordField.clear();
        showError(status);
    }

    /**
     * Adding the grade level handler
     */
    @FXML
    public void handleAddGradeLevel(ActionEvent event) {
        String gradeLevelName = gradeNameField.getText();
        String status = CoolSuppliesFeatureSet7Controller.addGrade(gradeLevelName);

        if (status.equals("Grade added successfully.")) {
            handleClear(event);
        } else {
            showError(String.format("%s This is an error for %s", status, gradeLevelName));
        }
        refreshGradeLevels();
    }

    /**
     * Handles updating the grade level
     */
    @FXML
    public void handleUpdateGradeLevel(ActionEvent event) {
        String gradeLevelName = gradeNameField.getText();
        String currentGradeLevel = gradeComboBox.getValue();
        
        // Add validation for non-existent grade
        if (currentGradeLevel == null || CoolSuppliesFeatureSet7Controller.getGrade(currentGradeLevel) == null) {
            showError("Cannot update: Selected grade does not exist");
            return;
        }

        String status = CoolSuppliesFeatureSet7Controller.updateGrade(currentGradeLevel, gradeLevelName);

        if (status.equals("Grade updated successfully.")) {
            handleClear(event);
        } else {
            handleClear(event);
            showError(String.format("%s This is an error for the new value: %s", status, gradeLevelName));
        }
        refreshGradeLevels();
    }

    /**
     * Handles deleting the grade level
     */
    @FXML
    public void handleDeleteGradeLevel(ActionEvent event) {
        String currentGradeLevel = gradeComboBox.getValue();
        
        // Add validation for non-existent grade
        if (currentGradeLevel == null || CoolSuppliesFeatureSet7Controller.getGrade(currentGradeLevel) == null) {
            showError("Cannot delete: Selected grade does not exist");
            return;
        }

        String status = CoolSuppliesFeatureSet7Controller.deleteGrade(currentGradeLevel);

        if (status.equals("Grade deleted successfully.")) {
            handleClear(event);
        } else {
            showError(String.format("%s This is an error for %s", status, currentGradeLevel));
        }
        refreshGradeLevels();
    }

    /**
     * Clears the fields
     */
    @FXML
    public void handleClear(ActionEvent event) {
        gradeComboBox.getSelectionModel().clearSelection();
        gradeNameField.clear();
    }
}
