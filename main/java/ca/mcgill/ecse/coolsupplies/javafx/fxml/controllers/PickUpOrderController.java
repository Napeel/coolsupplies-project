package ca.mcgill.ecse.coolsupplies.javafx.fxml.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet8Controller;
import ca.mcgill.ecse.coolsupplies.javafx.fxml.CoolSuppliesFxmlView;
import ca.mcgill.ecse.coolsupplies.model.Order;
import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;

public class PickUpOrderController implements Refreshable{
    @FXML private ComboBox<Integer> ordersBox;
    @FXML private Button pickUpButton;
    @FXML private Button viewOrdersButton;
    @FXML private Button refreshButton;
    /**
     * Initialize the controller
     */
    @FXML
    public void initialize() {
        refreshOrders();
        CoolSuppliesFxmlView.getInstance().registerRefreshEvent(ordersBox);
    }

    /**
     * Handle picking up an order
     */
    @FXML
    public void handlePickUpOrder(ActionEvent event) {
        Integer orderNumber = ordersBox.getValue();
        
        if (orderNumber == null) {
            showError("Please select an order to pick up");
            return;
        }

        String error = CoolSuppliesFeatureSet8Controller.PickedUpByStudent(orderNumber);

        if (error.equals("Succesfully picked up")) {
            handleClear();
            refreshOrders();
        } else {
            showError(error);
        }
    }

    /**
     * Clear selection
     */
    private void handleClear() {
        ordersBox.getSelectionModel().clearSelection();
    }

    /**
     * Refresh the orders list
     */
    private void refreshOrders() {
        CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();
        ObservableList<Integer> orderNumbers = FXCollections.observableArrayList();
        for (Order order : coolSupplies.getOrders()) {
            orderNumbers.add(order.getNumber());
        }
        ordersBox.setItems(orderNumbers);
    }

    /**
     * Show error dialog
     */
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleRefresh(){
        refreshOrders();
    }

    public void refresh(){
        handleRefresh();
    }
}