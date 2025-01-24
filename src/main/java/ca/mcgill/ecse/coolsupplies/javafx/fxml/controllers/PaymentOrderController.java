package ca.mcgill.ecse.coolsupplies.javafx.fxml.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet8Controller;
import ca.mcgill.ecse.coolsupplies.javafx.fxml.CoolSuppliesFxmlView;
import ca.mcgill.ecse.coolsupplies.model.Order;
import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;

public class PaymentOrderController implements Refreshable{
    @FXML private ComboBox<Integer> ordersBox;
    @FXML private TextField authCodeField;
    @FXML private TextField penaltyAuthCodeField;
    @FXML private Button payOrderButton;
    @FXML private Button payPenaltyButton;
    @FXML private Button refreshButton;

    public void refresh(){
        handleRefresh();
    }

    @FXML
    public void initialize() {
        refreshOrders();
        CoolSuppliesFxmlView.getInstance().registerRefreshEvent(ordersBox);
    }

    @FXML public void handleRefresh(){
        refreshOrders();
    }

    @FXML
    public void handlePayOrder(ActionEvent event) {
        Integer orderNumber = ordersBox.getValue();
        String authCode = authCodeField.getText();
        
        if (orderNumber == null) {
            showError("Please select an order to pay for");
            return;
        }

        if (authCode == null || authCode.trim().isEmpty()) {
            showError("Please enter an authorization code");
            return;
        }

        String error = CoolSuppliesFeatureSet8Controller.processOrderPayment(orderNumber, authCode);

        if (error.equals("Succesfully processed the payment for the order")) {
            handleClear();
            refreshOrders();
        } else {
            showError(error);
        }
    }

    @FXML
    public void handlePayPenalty(ActionEvent event) {
        Integer orderNumber = ordersBox.getValue();
        String penaltyAuthCode = penaltyAuthCodeField.getText();
        String orderAuthCode = authCodeField.getText();
        
        if (orderNumber == null) {
            showError("Please select an order to pay penalty for");
            return;
        }

        if (penaltyAuthCode == null || penaltyAuthCode.trim().isEmpty() || 
            orderAuthCode == null || orderAuthCode.trim().isEmpty()) {
            showError("Please enter both authorization codes");
            return;
        }

        String error = CoolSuppliesFeatureSet8Controller.processPenaltyPayment(
            orderNumber, penaltyAuthCode, orderAuthCode);

        if (error.equals("Successfully processed penalty payment")) {
            handleClear();
            refreshOrders();
        } else {
            showError(error);
        }
    }

    private void handleClear() {
        ordersBox.getSelectionModel().clearSelection();
        authCodeField.clear();
        penaltyAuthCodeField.clear();
    }

    private void refreshOrders() {
        CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();
        ObservableList<Integer> orderNumbers = FXCollections.observableArrayList();
        for (Order order : coolSupplies.getOrders()) {
            orderNumbers.add(order.getNumber());
        }
        ordersBox.setItems(orderNumbers);
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}