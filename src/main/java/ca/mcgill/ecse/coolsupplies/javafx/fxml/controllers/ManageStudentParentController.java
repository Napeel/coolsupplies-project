package ca.mcgill.ecse.coolsupplies.javafx.fxml.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet6Controller;
import ca.mcgill.ecse.coolsupplies.controller.TOStudent;
import ca.mcgill.ecse.coolsupplies.controller.TOParent;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet2Controller;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet1Controller;
import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;
import ca.mcgill.ecse.coolsupplies.model.Parent;
import ca.mcgill.ecse.coolsupplies.model.Student;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.stage.Modality;
import java.util.ArrayList;
public class ManageStudentParentController implements Refreshable {
    @FXML private ComboBox<String> studentComboBox;
    @FXML private ComboBox<String> parentComboBox;
    @FXML private Button addStudentToParentButton;
    @FXML private Button removeStudentFromParentButton;
    @FXML private Button clearButton;
    @FXML private Button viewLinksButton;
    @FXML private Button refreshButton;

    public void refresh(){
        handleRefresh(null);
    }

    @FXML
    public void initialize() {
        refreshStudents();
        refreshParents();

        // Make the ComboBoxes editable
        studentComboBox.setEditable(true);
        parentComboBox.setEditable(true);
    }

    private void refreshStudents() {
        List<TOStudent> students = CoolSuppliesFeatureSet2Controller.getStudents();
        ObservableList<String> studentNames = FXCollections.observableArrayList();
        for (TOStudent student : students) {
            studentNames.add(student.getName());
        }
        studentComboBox.setItems(studentNames);
    }

    private void refreshParents() {
        List<TOParent> parents = CoolSuppliesFeatureSet1Controller.getParents();
        ObservableList<String> parentEmails = FXCollections.observableArrayList();
        for (TOParent parent : parents) {
            parentEmails.add(parent.getEmail());
        }
        parentComboBox.setItems(parentEmails);
    }

    @FXML
    public void handleAddStudentToParent(ActionEvent event) {
        String studentName = studentComboBox.getValue();
        String parentEmail = parentComboBox.getValue();

        if (studentName == null || parentEmail == null) {
            showError("Please select both a student and a parent.");
            return;
        }

        // Check if the student is already linked to a parent
        CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();
        for (Student student : coolSupplies.getStudents()) {
            if (student.getName().equals(studentName) && student.getParent() != null) {
                showError("Student already assigned to parent.");
                return;
            }
        }

        String result = CoolSuppliesFeatureSet6Controller.addStudentToParent(studentName, parentEmail);
        if (!result.contains("Successfully")) {
            showError(result);
        } else {
            showInfo(result);
        }
    }

    @FXML
    public void handleRemoveStudentFromParent(ActionEvent event) {
        String studentName = studentComboBox.getValue();
        String parentEmail = parentComboBox.getValue();

        if (studentName == null || parentEmail == null) {
            showError("Please select both a student and a parent.");
            return;
        }

        // Check if the student exists
        List<TOStudent> students = CoolSuppliesFeatureSet2Controller.getStudents();
        boolean studentExists = students.stream().anyMatch(student -> student.getName().equals(studentName));

        if (!studentExists) {
            showError("Student does not exist.");
            return;
        }

        String result = CoolSuppliesFeatureSet6Controller.deleteStudentFromParent(studentName, parentEmail);
        if (!result.contains("Successfully")) {
            showError(result);
        } else {
            showInfo(result);
        }
    }

    @FXML
    public void handleClear(ActionEvent event) {
        studentComboBox.getSelectionModel().clearSelection();
        parentComboBox.getSelectionModel().clearSelection();
    }
    
    @FXML
    public void handleRefresh(ActionEvent event) {
    	refreshStudents();
        refreshParents();
    }

    @FXML
    public void handleViewLinks(ActionEvent event) {
        CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();
        Map<String, List<String>> parentStudentMap = new HashMap<>();

        for (Parent parent : coolSupplies.getParents()) {
            List<String> studentNames = new ArrayList<>();
            for (Student student : parent.getStudents()) {
                studentNames.add(student.getName());
            }
            parentStudentMap.put(parent.getEmail(), studentNames);
        }

        // Create dialog
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Parent-Student Links");
        dialog.setHeaderText("Current Parent-Student Associations");

        // Create content
        VBox content = new VBox(10);
        content.setPadding(new Insets(20, 20, 20, 20));

        // Create text area for links
        TextArea linksText = new TextArea();
        linksText.setEditable(false);
        linksText.setPrefRowCount(15);
        linksText.setPrefColumnCount(40);

        // Build the links text
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, List<String>> entry : parentStudentMap.entrySet()) {
            sb.append(entry.getKey()).append(" : ");
            sb.append(String.join(", ", entry.getValue()));
            sb.append("\n");
        }

        linksText.setText(sb.toString());

        // Add text area to content and set up dialog
        content.getChildren().add(linksText);
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.setContent(content);
        dialogPane.getButtonTypes().add(ButtonType.CLOSE);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.showAndWait();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}