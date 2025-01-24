package ca.mcgill.ecse.coolsupplies.javafx.fxml.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet2Controller;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet7Controller;
import ca.mcgill.ecse.coolsupplies.controller.TOStudent;
import ca.mcgill.ecse.coolsupplies.controller.TOGrade;
import ca.mcgill.ecse.coolsupplies.javafx.fxml.CoolSuppliesFxmlView;
import javafx.event.ActionEvent;
import java.util.List;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.geometry.Insets;
import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;
import ca.mcgill.ecse.coolsupplies.model.Parent;
import ca.mcgill.ecse.coolsupplies.model.Student;

public class ManageStudentsController implements Refreshable{
    @FXML private TextField nameField;
    @FXML private ComboBox<String> gradeLevelComboBox; 
    @FXML private Button addButton;
    @FXML private Button updateButton;
    @FXML private Button deleteButton;
    @FXML private Button clearButton;
    @FXML private ComboBox<String> studentComboBox; 

    
    public void refresh() {
        refreshStudents();
        refreshGrades();
    }

    /**
     * Initializing the ManageStudentsController
     */
    @FXML
    public void initialize() {
        refreshStudents();
        refreshGrades();
        CoolSuppliesFxmlView.getInstance().registerRefreshEvent(studentComboBox);

        // Listen for changes in the selected student
        studentComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                TOStudent student = CoolSuppliesFeatureSet2Controller.getStudent(newVal);
                if (student != null) {
                    nameField.setText(student.getName());
                    gradeLevelComboBox.setValue(student.getGradeLevel());
                }
            }
        });
    }

    /**
     * Adding new student to system 
     */
    @FXML
    public void handleAddStudent(ActionEvent event) {
        String name = nameField.getText();
        String gradeLevel = gradeLevelComboBox.getValue();

        String error = CoolSuppliesFeatureSet2Controller.addStudent(name, gradeLevel);

        if (error.isEmpty()) {
            handleClear(null);
            refreshStudents();
        } else {
            showError("Failed to Add Student", error);
        }
    }

    /**
     * Updating an existing student in the system
     */
    @FXML
    public void handleUpdateStudent(ActionEvent event) {
        String currentName = studentComboBox.getValue();
        String newName = nameField.getText();
        String newGradeLevel = gradeLevelComboBox.getValue();

        if (newName == null || newName.trim().isEmpty()) {
            showError("Failed to Update Student", "Name is required.");
            return;
        }

        String error = CoolSuppliesFeatureSet2Controller.updateStudent(currentName, newName, newGradeLevel);

        if (error.isEmpty()) {
            handleClear(null);
            refreshStudents();
        } else {
            showError("Failed to Update Student", error);
        }
    }

    /**
     * Deleting student from system
     */
    @FXML
    public void handleDeleteStudent(ActionEvent event) {
        String name = studentComboBox.getValue();
        String error = CoolSuppliesFeatureSet2Controller.deleteStudent(name);

        if (error.isEmpty()) {
            handleClear(null);
            refreshStudents();
        } else {
            showError("Failed to Delete Student", "Student does not exist.");
        }
    }

    /**
     * Clearing input fields in the ManageStudentsController
     */
    @FXML
    public void handleClear(ActionEvent event) {
        nameField.clear();
        gradeLevelComboBox.getSelectionModel().clearSelection();
        studentComboBox.getSelectionModel().clearSelection();
    }

    /**
     * Refreshing the list of students in the system
     */
    private void refreshStudents() {
        List<TOStudent> students = CoolSuppliesFeatureSet2Controller.getStudents();
        ObservableList<String> studentNames = FXCollections.observableArrayList();
        for (TOStudent student : students) {
            studentNames.add(student.getName());
        }
        studentComboBox.setItems(studentNames); // Updated for ComboBox
    }
    
    private void refreshGrades() {
        List<TOGrade> grades = CoolSuppliesFeatureSet7Controller.getGrades();
        ObservableList<String> gradeLevels = FXCollections.observableArrayList();
        for (TOGrade grade : grades) {
            gradeLevels.add(grade.getLevel());
        }
        gradeLevelComboBox.setItems(gradeLevels); // Updated for ComboBox
    }

    /**
     * Handle viewing all students in a popup
     */
    @FXML
    private void handleViewAllStudents() {
        // Create dialog
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("All Students");
        dialog.setHeaderText("Current Students in System");

        // Create content
        VBox content = new VBox(10);
        content.setPadding(new Insets(20, 20, 20, 20));

        // Get students
        List<TOStudent> students = CoolSuppliesFeatureSet2Controller.getStudents();

        // Create text area for students
        TextArea studentsText = new TextArea();
        studentsText.setEditable(false);
        studentsText.setPrefRowCount(15);
        studentsText.setPrefColumnCount(40);

        // Build the students text
        StringBuilder sb = new StringBuilder();
        for (TOStudent student : students) {
            sb.append(String.format("Student: %s\n", student.getName()));
            sb.append(String.format("Grade Level: %s\n", student.getGradeLevel()));

            // Get parent info if available
            String parentEmail = getParentEmailFromStudent(student.getName());
            if (parentEmail != null) {
                sb.append(String.format("Parent Email: %s\n", parentEmail));
            } else {
                sb.append("No Parent Assigned\n");
            }
            sb.append("\n");
        }

        studentsText.setText(sb.toString());

        // Add text area to content and set up dialog
        content.getChildren().add(studentsText);
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.setContent(content);
        dialogPane.getButtonTypes().add(ButtonType.CLOSE);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.showAndWait();
    }

    /**
     * Helper function to get parent email from student name
     */
    private String getParentEmailFromStudent(String studentName) {
        CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();
        for (Student student : coolSupplies.getStudents()) {
            if (student.getName().equals(studentName)) {
                Parent parent = student.getParent();
                if (parent != null) {
                    return parent.getEmail();
                }
            }
        }
        return null;
    }

    /**
     * Helper function to show error alerts
     */
    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
