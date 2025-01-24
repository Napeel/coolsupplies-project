package ca.mcgill.ecse.coolsupplies.javafx.fxml.controllers;

import ca.mcgill.ecse.coolsupplies.controller.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class ModifyOrderController implements Refreshable{
    private int currentOrderNumber;
    private String selectedItemName = "";
    private String selectedBundleName = "";
    private String statusMessage = "";

    @FXML
    private Button deleteItemButton;
    @FXML
    private Button refreshPageButton;
    @FXML
    private Button addItemButton;
    @FXML
    private Button updateItemButton;
    @FXML
    private Label parentEmail;
    @FXML
    private Label studentName;
    @FXML
    private Label status;
    @FXML
    private Label date;
    @FXML
    private Label level;
    @FXML
    private Label totalPrice;
    @FXML
    private Label authCode;
    @FXML
    private Label penAuthCode;
    @FXML
    private TextField quantityTextField;
    @FXML
    private ComboBox<String> selectItemComboBox;
    @FXML
    private ComboBox<Integer> orderNumberComboBox;
    @FXML
    private TableView<TOOrderItem> orderItemTable;
    @FXML
    private TableColumn<TOOrderItem, Integer> quantityColumn;
    @FXML
    private TableColumn<TOOrderItem, String> itemNameColumn;
    @FXML
    private TableColumn<TOOrderItem, String> gradeBundleNameColumn;
    @FXML
    private TableColumn<TOOrderItem, Double> priceColumn;
    @FXML
    private TableColumn<TOOrderItem, Double> discountDeductedColumn;

    public void refresh(){
        refreshItems();
        refreshOrders();
    }

    @FXML
    public void initialize() {
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        gradeBundleNameColumn.setCellValueFactory(new PropertyValueFactory<>("gradeBundleName"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        discountDeductedColumn.setCellValueFactory(new PropertyValueFactory<>("discountDeducted"));

        deleteItemButton.setDisable(true);
        updateItemButton.setDisable(true);

        orderItemTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                deleteItemButton.setDisable(false);
                updateItemButton.setDisable(false);
                selectedItemName = newValue.getItemName();
                if (newValue.getGradeBundleName() != null) {
                    selectedBundleName = newValue.getGradeBundleName();
                } else {
                    selectedBundleName = "";
                }
            } else {
                selectedItemName = "";
                selectedBundleName = "";
                deleteItemButton.setDisable(true);
                updateItemButton.setDisable(true);
            }
        });
        handleRefresh();
    }

    @FXML
    public void handleUpdateOrderSelection() {
        if (orderNumberComboBox.getValue() != null) {
            currentOrderNumber = orderNumberComboBox.getValue();
        }
        updateOrderItems();
        updateOrderInfo();
    }

    @FXML
    public void handleRefresh() {
        updateOrderInfo();
        refreshItems();
        refreshOrders();
        updateOrderItems();
    }

    @FXML
    public void handleDeleteItem() {
        if (selectedItemName.isEmpty() || currentOrderNumber == 0) {
            showError("Please select an item in the order.");
            return;
        }
        if (selectedBundleName.isEmpty()) {
            statusMessage = CoolSuppliesFeatureSet8Controller.removeItem(currentOrderNumber, selectedItemName);
        } else {
            statusMessage = CoolSuppliesFeatureSet8Controller.removeItem(currentOrderNumber, selectedBundleName);
        }
        if (statusMessage.equals("Item has been removed successfully!")) {
            selectedBundleName = "";
            selectedItemName = "";
        } else {
            showError(statusMessage);
        }
        updateOrderItems();
        updateOrderInfo();
    }

    @FXML
    public void handleUpdateItemInOrder() {
        if (quantityTextField.getText().isEmpty()) {
            showError("Please enter a quantity.");
            return;
        }
        int quantity;
        try {
            quantity = Integer.parseInt(quantityTextField.getText());
        } catch (NumberFormatException e) {
            showError("Please enter a number in the quantity box.");
            return;
        }

        if (selectedItemName.isEmpty() && selectedBundleName.isEmpty()) {
            showError("Please select an item in the order.");
            return;
        }
        if (selectedBundleName.isEmpty()) {
            statusMessage = CoolSuppliesFeatureSet8Controller.UpdateItem(currentOrderNumber, selectedItemName, quantity);
        } else {
            statusMessage = CoolSuppliesFeatureSet8Controller.UpdateItem(currentOrderNumber, selectedBundleName, quantity);
        }
        if (!statusMessage.equals("Succesfully updated item")) {
            showError(statusMessage);
        }
        updateOrderItems();
        updateOrderInfo();
    }

    @FXML
    public void handleAddItemToOrder() {
        if (selectItemComboBox.getValue().isEmpty()) {
            showError("Please enter a valid item.");
            return;
        }
        if (quantityTextField.getText().isEmpty()) {
            showError("Please enter a valid quantity.");
            return;
        }
        int quantityInBox = 0;
        try {
            quantityInBox = Integer.parseInt(quantityTextField.getText());
        } catch (NumberFormatException e) {
            showError("Please enter a valid integer quantity.");
        }

        String itemInBox = selectItemComboBox.getValue();

        List<TOGradeBundle> gradeBundles = CoolSuppliesFeatureSet4Controller.getBundles();
        for (TOGradeBundle bundle : gradeBundles) {
            if (bundle.getName().equals(itemInBox)) {
                if (CoolSuppliesFeatureSet5Controller.getBundleItems(itemInBox).isEmpty()) {
                    showError("Cannot add empty bundle to an order.");
                    return;
                }
                break;
            }
        }

        statusMessage = CoolSuppliesFeatureSet8Controller.AddItemOrder(currentOrderNumber, itemInBox, quantityInBox);

        if (!statusMessage.equals("Succesfully updated the order")) {
            showError(statusMessage);
        }

        updateOrderItems();
        updateOrderInfo();
    }

    private void updateOrderItems() {
        if (currentOrderNumber != 0) {
            displayItems(CoolSuppliesFeatureSet8Controller.getOrder(currentOrderNumber).getTOOrderItems());
        }
        orderItemTable.getSortOrder().addAll(gradeBundleNameColumn, priceColumn);
        gradeBundleNameColumn.setSortType(TableColumn.SortType.ASCENDING);
        priceColumn.setSortType(TableColumn.SortType.ASCENDING);
        orderItemTable.sort();
    }

    private void displayItems(List<TOOrderItem> orderItems) {
        ObservableList<TOOrderItem> observableItems = FXCollections.observableArrayList(orderItems);
        orderItemTable.setItems(observableItems);
    }

    private void refreshItems() {
        List<TOItem> items = CoolSuppliesFeatureSet3Controller.getItems();
        ObservableList<String> itemNames = FXCollections.observableArrayList();
        for (TOItem item : items) {
            itemNames.add(item.getName());
        }
        List<TOGradeBundle> bundles = CoolSuppliesFeatureSet4Controller.getBundles();
        for (TOGradeBundle bundle : bundles) {
            itemNames.add(bundle.getName());
        }
        selectItemComboBox.setItems(itemNames);
    }

    private void refreshOrders() {
        List<TOOrder> orders = CoolSuppliesFeatureSet8Controller.getAllOrders();
        ObservableList<Integer> orderNumbers = FXCollections.observableArrayList();
        for (TOOrder order : orders) {
            orderNumbers.add(order.getNumber());
        }
        orderNumberComboBox.setItems(orderNumbers);
    }

    private void updateOrderInfo() {
        TOOrder order = CoolSuppliesFeatureSet8Controller.getOrder(currentOrderNumber);
        if (order == null) {
            return;
        }
        parentEmail.setText(order.getParentEmail());
        studentName.setText(order.getStudentName());
        status.setText(order.getStatus());
        level.setText(order.getLevel());
        date.setText(order.getDate() + "");
        totalPrice.setText(order.getTotalPrice() + "");
        authCode.setText(order.getAuthorizationCode());
        penAuthCode.setText(order.getPenaltyAuthorizationCode());
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
