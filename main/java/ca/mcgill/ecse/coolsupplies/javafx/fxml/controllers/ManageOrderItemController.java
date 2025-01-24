package ca.mcgill.ecse.coolsupplies.javafx.fxml.controllers;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet3Controller;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet8Controller;
import ca.mcgill.ecse.coolsupplies.controller.TOOrder;
import ca.mcgill.ecse.coolsupplies.controller.TOOrderItem;
import ca.mcgill.ecse.coolsupplies.controller.TOItem;

public class ManageOrderItemController implements Refreshable {

    @FXML private ChoiceBox<Integer> orderChoiceBox;
    @FXML private ChoiceBox<String> itemChoiceBox;
    @FXML private TextField itemQuantityField;
    @FXML private Button updateButton;
    @FXML private Button deleteButton;
    @FXML private Button addButton;
    @FXML private Button viewAllOrdersButton;
    @FXML private Button clearButton;

    public void refresh(){
      refreshItems();
    }
    
    public void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("An Error Occurred");

        // Create a Label for the message
        Label messageLabel = new Label(message);
        messageLabel.setWrapText(true);

        // Wrap the label in a scroll pane
        ScrollPane scrollPane = new ScrollPane(messageLabel);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefSize(400, 200);

        // Set the scroll pane as expandable content
        alert.getDialogPane().setExpandableContent(scrollPane);
        alert.getDialogPane().setExpanded(true);

        alert.showAndWait();
    }

     @FXML
    public void initialize() {
      addButton.setOnAction(this::handleAddOrderItem);
      deleteButton.setOnAction(this::handleUpdateOrderItem);
      updateButton.setOnAction(this::handleUpdateOrderItem);
      clearButton.setOnAction(this::handleClear);


      // listen for changes in the choice box
      orderChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
          if (newVal != null) {
              TOOrder order = CoolSuppliesFeatureSet8Controller.getOrder(newVal);
              if (order != null) {
                List<TOOrderItem> orderItems = order.getTOOrderItems();
                for (TOOrderItem orderItem : orderItems) {
                  if (orderItem.getItemName().equals(itemChoiceBox.getValue())) {
                    String quantity = Integer.toString(orderItem.getQuantity());
                    itemQuantityField.setText(quantity);
                  }
                }
                
              }
          }
      });
    }

    @FXML
    public void handleAddOrderItem(ActionEvent event) {
      Integer currOrderNum = orderChoiceBox.getValue();
      String currItem = itemChoiceBox.getValue();
      String currQuantity = itemQuantityField.getText();
      Integer quantity = Integer.parseInt(currQuantity);
      
      String status = CoolSuppliesFeatureSet8Controller.AddItemOrder(currOrderNum, currItem, quantity);
      
      if (status.equals("Succesfully updated the order")) {
          handleClear(event);
      } else{
          showError(String.format("%s",status));
      }
      refreshItems();
    }

    @FXML
    public void handleUpdateOrderItem(ActionEvent event) {
      Integer currOrderNum = orderChoiceBox.getValue();
      String currItem = itemChoiceBox.getValue();
      String currQuantity = itemQuantityField.getText();
      Integer quantity = Integer.parseInt(currQuantity);
      
      String status = CoolSuppliesFeatureSet8Controller.UpdateItem(currOrderNum, currItem, quantity);
      
      if (status.equals("Succesfully updated item")) {
          handleClear(event);
      } else{
          showError(String.format("%s",status));
      }
      refreshItems();
    }

    @FXML
    public void handleDeleteOrderItem(ActionEvent event) {
      Integer currOrderNum = orderChoiceBox.getValue();
      String currItem = itemChoiceBox.getValue();
      String status = CoolSuppliesFeatureSet8Controller.removeItem(currOrderNum, currItem);

      if (status.equals("")) {
          handleClear(event);
          
      } else{
          showError(String.format("%s",status));
      }
      refreshItems();
    }

    private void refreshItems() {
      List<TOItem> items = CoolSuppliesFeatureSet3Controller.getItems();
      ObservableList<String> itemNames = FXCollections.observableArrayList();
      for (TOItem item : items) {
          itemNames.add(item.getName());
      }
      itemChoiceBox.setItems(itemNames);
   }

    @FXML
    public void handleClear(ActionEvent event) {
        orderChoiceBox.getSelectionModel().clearSelection();       
        itemChoiceBox.getSelectionModel().clearSelection();
        itemQuantityField.clear();
    }
}
