package ca.mcgill.ecse.coolsupplies.javafx.fxml.controllers;

import ca.mcgill.ecse.coolsupplies.controller.TOOrder;
import ca.mcgill.ecse.coolsupplies.controller.TOOrderItem;
import ca.mcgill.ecse.coolsupplies.model.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet8Controller;

public class IndividualOrderController {
    @FXML private Label parentLabel;
    @FXML private Label studentLabel;
    @FXML private Label statusLabel;
    @FXML private Label dateLabel;
    @FXML private Label levelLabel;
    @FXML private Label authCodeLabel;
    @FXML private Label penCodeLabel;
    @FXML private Label totalPriceLabel;

    @FXML private TableView<TOOrderItem> itemsTable;
    @FXML private TableColumn<TOOrderItem, String> nameColumn;
    @FXML private TableColumn<TOOrderItem, String> bundleColumn;
    @FXML private TableColumn<TOOrderItem, Double> priceColumn;
    @FXML private TableColumn<TOOrderItem, Double> discountColumn;
    @FXML private TableColumn<TOOrderItem, Double> finalPriceColumn;

    private TOOrder selectedOrder; 
    private Integer orderNum;

    /**
     * Initializes the table of item properties
     * @author Sofia Galanopoulos
     */
    @FXML public void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        bundleColumn.setCellValueFactory(new PropertyValueFactory<>("bundle"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        discountColumn.setCellValueFactory(new PropertyValueFactory<>("discount"));
        finalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("finalPrice"));
    }

    /**
     * Sets the selected order and updates the UI with the order details
     * @author Sofia Galanopoulos
     */
    public void setOrder(Order order) {
        this.selectedOrder = CoolSuppliesFeatureSet8Controller.getOrder(orderNum);
  
        //make sure order not null
        if (selectedOrder == null) {
            return; 
        }

        //set all label values of the order details
        parentLabel.setText("Parent: " + selectedOrder.getParentEmail());
        studentLabel.setText("Student: " + selectedOrder.getStudentName());
        statusLabel.setText("Status: " + selectedOrder.getStatus());
        dateLabel.setText("Date: " + selectedOrder.getDate());
        levelLabel.setText("Level: " + selectedOrder.getLevel());
        authCodeLabel.setText("Authorization Code: " + selectedOrder.getAuthorizationCode());
        penCodeLabel.setText("Penalty Authorization Code: " + selectedOrder.getPenaltyAuthorizationCode());
        totalPriceLabel.setText(String.format("Total Price: $%.2f", selectedOrder.getTotalPrice()));

        //fill in table with the order items
        ObservableList<TOOrderItem> items = FXCollections.observableArrayList(selectedOrder.getTOOrderItems());
        itemsTable.setItems(items);

    }

    /**
     * Handles when "View All Orders" button is pressed
     * @author Sofia Galanopoulos
     */
    @FXML private void pressViewAllOrders(ActionEvent event) {
        try {
            // Load the Orders Panel FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewAllOrders.fxml"));
            Parent root = loader.load();

            // Get the current stage and set the new scene
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


