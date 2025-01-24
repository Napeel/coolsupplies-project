package ca.mcgill.ecse.coolsupplies.javafx.fxml;

import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet1Controller;
import ca.mcgill.ecse.coolsupplies.controller.TOParent;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;


public class LogInPageController {
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private PasswordField passwordPasswordField;
    @FXML
    private CheckBox showPasswordCheckBox;
    @FXML
    private Button logInButton;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private VBox vbox;
    @FXML
    private Label errorMessageLabel;
    @FXML
    private Button hintButton;
    @FXML
    private Label hintMessageLabel;
    private String password;

    @FXML
    public void initialize() {
        anchorPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            vbox.setPrefWidth(newVal.doubleValue() * 1);
        });

        anchorPane.heightProperty().addListener((obs, oldVal, newVal) -> {
            vbox.setPrefHeight(newVal.doubleValue() * 1);
        });

        passwordPasswordField.setOnKeyPressed( keyPress -> {
            if( keyPress.getCode() == KeyCode.ENTER ) {
                handleLogInButtonPress();
            }
        } );

        passwordTextField.setOnKeyPressed( keyPress -> {
            if( keyPress.getCode() == KeyCode.ENTER ) {
                handleLogInButtonPress();
            }
        } );
    }

    @FXML
    public void handleLogInButtonPress() {
        if (emailTextField.getText().isEmpty()) {
            showError("Please enter an email.");
            return;
        }
        if ((passwordTextField.getText().isEmpty() && showPasswordCheckBox.isSelected()) || (passwordPasswordField.getText().isEmpty() && !showPasswordCheckBox.isSelected())) {
            showError("Please enter a password.");
            return;
        }
        String email = emailTextField.getText();
        String password = showPasswordCheckBox.isSelected() ? passwordTextField.getText() : passwordPasswordField.getText();

        if (!validAdminLogIn(email, password) && !validParentLogIn(email, password)) {
            errorMessageLabel.setText("Invalid login credentials.");
        } else {
            showMainStage((Stage) emailTextField.getScene().getWindow());
        }
    }

    @FXML
    public void handleHintButtonPress() {
        String adminEmail = CoolSuppliesApplication.getCoolSupplies().getAdmin().getEmail();
        String adminPassword = CoolSuppliesApplication.getCoolSupplies().getAdmin().getPassword();
        hintMessageLabel.setText("Admin email is: " + adminEmail + "\nAdmin password is: " + adminPassword);
        hintButton.setDisable(true);
    }

    @FXML
    public void handleShowPassword() {
        if (showPasswordCheckBox.isSelected()) {
            passwordTextField.setText(passwordPasswordField.getText());
            passwordPasswordField.setVisible(false);
            passwordTextField.setVisible(true);
        } else {
            passwordPasswordField.setText(passwordTextField.getText());
            passwordTextField.setVisible(false);
            passwordPasswordField.setVisible(true);
        }
    }

    private void showMainStage(Stage primaryStage) {
        try {
            var root = (Pane) FXMLLoader.load(getClass().getResource("MainPage.fxml"));
            root.setStyle(CoolSuppliesApplication.DARK_MODE ? "-fx-base: rgba(20, 20, 20, 255);" : "");
            var scene = new Scene(root);
            String cssFile = getClass().getResource("/styles.css").toExternalForm();
            scene.getStylesheets().add(cssFile);
            primaryStage.setScene(scene);
            primaryStage.setMinWidth(800);
            primaryStage.setMinHeight(600);
            primaryStage.setTitle("CoolSuppliesMainPage");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean validAdminLogIn(String email, String password) {
        return email.equals(CoolSuppliesApplication.getCoolSupplies().getAdmin().getEmail()) && password.equals(CoolSuppliesApplication.getCoolSupplies().getAdmin().getPassword());
    }

    private boolean validParentLogIn(String email, String password) {
        TOParent parent = CoolSuppliesFeatureSet1Controller.getParent(email);
        if (parent == null) {
            return false;
        }
        return password.equals(parent.getPassword());
    }


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
}
