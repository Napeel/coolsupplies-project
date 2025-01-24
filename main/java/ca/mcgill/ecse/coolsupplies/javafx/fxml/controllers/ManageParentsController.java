package ca.mcgill.ecse.coolsupplies.javafx.fxml.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet1Controller;
import ca.mcgill.ecse.coolsupplies.controller.TOParent;
import ca.mcgill.ecse.coolsupplies.javafx.fxml.CoolSuppliesFxmlView;
import javafx.event.ActionEvent;
import java.util.List;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.geometry.Insets;

public class ManageParentsController implements Refreshable{
    @FXML private TextField emailField;
    @FXML private TextField passwordField;
    @FXML private TextField nameField;
    @FXML private TextField phoneNumberField;
    @FXML private Button addButton;
    @FXML private Button updateButton;
    @FXML private Button deleteButton;
    @FXML private Button clearButton;
    @FXML private ComboBox<String> parentComboBox; 

    public void refresh(){
        refreshParents();
    }

    /**
     * Initializes the ManageParentsController
     */
    @FXML
    public void initialize() {
        refreshParents();
        CoolSuppliesFxmlView.getInstance().registerRefreshEvent(parentComboBox);

        // Listen for changes in the ComboBox
        parentComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                TOParent parent = CoolSuppliesFeatureSet1Controller.getParent(newVal);
                if (parent != null) {
                    emailField.setText(parent.getEmail());
                    passwordField.setText(parent.getPassword());
                    nameField.setText(parent.getName());
                    phoneNumberField.setText(String.valueOf(parent.getPhoneNumber()));
                }
            }
        });
    }

    /**
     * Adding a new parent to the system
     */
    @FXML
    public void handleAddParent(ActionEvent event) {
        String email = emailField.getText();
        String password = passwordField.getText();
        String name = nameField.getText();
        String phoneNumber = phoneNumberField.getText();

        if (password == null || password.trim().isEmpty()) {
            showError("Failed to Add Parent", "Password is required.");
            return;
        }

        try {
            String error = CoolSuppliesFeatureSet1Controller.addParent(email, password, name, Integer.parseInt(phoneNumber));
            if (!error.isEmpty() && !error.equals("Successfully added parent")) {
                showError("Failed to Add Parent", error);
            } else {
                handleClear(null);
                refreshParents();
            }
        } catch (NumberFormatException e) {
            showError("Invalid Phone Number", "Phone number must be a valid number.");
        }
    }

    /**
     * Updating an existing parent in the system
     */
    @FXML
    public void handleUpdateParent(ActionEvent event) {
        String email = emailField.getText();
        String newPassword = passwordField.getText();
        String newName = nameField.getText();
        String newPhoneNumber = phoneNumberField.getText();

        if (newPassword == null || newPassword.trim().isEmpty()) {
            showError("Failed to Update Parent", "Password is required.");
            return;
        }

        try {
            String error = CoolSuppliesFeatureSet1Controller.updateParent(email, newPassword, newName, Integer.parseInt(newPhoneNumber));
            if (error.equals("Successfully updated parent")) {
                handleClear(null);
                refreshParents();
            } else {
                showError("Failed to Update Parent", error);
            }
        } catch (NumberFormatException e) {
            showError("Invalid Phone Number", "Phone number must be a valid number.");
        }
    }

    /**
     * Deleting a parent from the system
     */
    @FXML
    public void handleDeleteParent(ActionEvent event) {
        String email = emailField.getText();
        String error = CoolSuppliesFeatureSet1Controller.deleteParent(email);

        if (error.equals("Parent successfully deleted")) {
            handleClear(null);
            refreshParents();
        } else {
            showError("Failed to Delete Parent", "Parent does not exist.");
        }
    }

    /**
     * Clearing all input fields
     */
    @FXML
    public void handleClear(ActionEvent event) {
        emailField.clear();
        passwordField.clear();
        nameField.clear();
        phoneNumberField.clear();
        parentComboBox.getSelectionModel().clearSelection();
    }

    /**
     * Refreshing the list of parents in the ComboBox
     */
    private void refreshParents() {
        List<TOParent> parents = CoolSuppliesFeatureSet1Controller.getParents();
        ObservableList<String> parentEmails = FXCollections.observableArrayList();
        for (TOParent parent : parents) {
            parentEmails.add(parent.getEmail());
        }
        parentComboBox.setItems(parentEmails); // Updated for ComboBox
    }

    /**
     * Handle viewing all parents in a popup
     */
    @FXML
    private void handleViewAllParents() {
        // Create dialog
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("All Parents");
        dialog.setHeaderText("Current Parents in System");

        // Create content
        VBox content = new VBox(10);
        content.setPadding(new Insets(20, 20, 20, 20));

        // Get parents
        List<TOParent> parents = CoolSuppliesFeatureSet1Controller.getParents();

        // Create text area for parents
        TextArea parentsText = new TextArea();
        parentsText.setEditable(false);
        parentsText.setPrefRowCount(15);
        parentsText.setPrefColumnCount(50);

        // Build the parents text
        StringBuilder sb = new StringBuilder();
        for (TOParent parent : parents) {
            sb.append(String.format("Name: %s\n", parent.getName()));
            sb.append(String.format("Email: %s\n", parent.getEmail()));
            sb.append(String.format("Phone Number: %s\n", parent.getPhoneNumber()));
            sb.append("\n");
        }

        parentsText.setText(sb.toString());

        // Add text area to content and set up dialog
        content.getChildren().add(parentsText);
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.setContent(content);
        dialogPane.getButtonTypes().add(ButtonType.CLOSE);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.showAndWait();
    }

    /**
     * Helper method to show error alerts
     */
    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
