package ca.mcgill.ecse.coolsupplies.javafx.fxml.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import ca.mcgill.ecse.coolsupplies.controller.*;
import javafx.event.ActionEvent;
import java.util.List;

public class ManageSystemItemsController implements Refreshable{
    @FXML
    private ComboBox<String> itemComboBox;
    @FXML
    private TextField itemNameField;
    @FXML
    private TextField itemPriceField;
    @FXML
    private Button addItemButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button refreshButton;
    @FXML
    private Button viewAllItemsButton;

    private ObservableList<String> allItems;

    public void refresh(){
        handleRefresh(null);
    }

    @FXML
    public void initialize() {
        allItems = FXCollections.observableArrayList();
        setupComboBox();
        refreshItems();
        
        itemComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                TOItem selectedItem = CoolSuppliesFeatureSet3Controller.getItem(newVal);
                if (selectedItem != null) {
                    itemNameField.setText(selectedItem.getName());
                    itemPriceField.setText(String.valueOf(selectedItem.getPrice()));
                }
            }
        });
    }

    private void setupComboBox() {
        itemComboBox.setItems(allItems);

        itemComboBox.getEditor().textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal == null || newVal.isEmpty()) {
                itemComboBox.setItems(allItems);
            } else {
                FilteredList<String> filteredItems = new FilteredList<>(allItems, item ->
                    item.toLowerCase().contains(newVal.toLowerCase())
                );
                itemComboBox.setItems(filteredItems);
            }
        });
    }

    @FXML
    public void handleAddItem(ActionEvent event) {
        String name = itemNameField.getText().trim();
        try {
            int price = Integer.parseInt(itemPriceField.getText().trim());
            String error = CoolSuppliesFeatureSet3Controller.addItem(name, price);
            if (error.isEmpty()) {
                refreshItems();
                handleClearItemFields();
            } else {
                showError(error);
            }
        } catch (NumberFormatException e) {
            showError("Invalid price. Please enter a valid number.");
        }
    }

    @FXML
    public void handleUpdateItem(ActionEvent event) {
        String originalName = itemComboBox.getValue();
        String newName = itemNameField.getText().trim();

        if (originalName == null || originalName.isEmpty()) {
            showError("Please select an item to update");
            return;
        }

        try {
            int price = Integer.parseInt(itemPriceField.getText().trim());
            String error = CoolSuppliesFeatureSet3Controller.updateItem(originalName, newName, price);
            if (error.isEmpty()) {
                refreshItems();
                handleClearItemFields();
            } else {
                showError(error);
            }
        } catch (NumberFormatException e) {
            showError("Invalid price. Please enter a valid number.");
        }
    }

    @FXML
    public void handleDeleteItem(ActionEvent event) {
        String name = itemComboBox.getValue();
        if (name == null || name.isEmpty()) {
            showError("Please select an item to delete");
            return;
        }

        String error = CoolSuppliesFeatureSet3Controller.deleteItem(name);
        if (error == null) {
            refreshItems();
            handleClearItemFields();
        } else {
            showError(error);
        }
    }

    @FXML
    private void handleRefresh(ActionEvent event) {
        refreshItems();
        handleClearItemFields();
    }

    private void refreshItems() {
        List<TOItem> items = CoolSuppliesFeatureSet3Controller.getItems();
        allItems.clear();
        for (TOItem item : items) {
            allItems.add(item.getName());
        }
        itemComboBox.getSelectionModel().clearSelection();
        itemComboBox.setValue(null);
        itemComboBox.setItems(allItems);
    }

    private void handleClearItemFields() {
        itemNameField.clear();
        itemPriceField.clear();
        itemComboBox.setItems(allItems);
        itemComboBox.getEditor().clear();
    }

    @FXML
    private void handleClearItemFieldsButton() {
        refreshItems();
        handleClearItemFields();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleViewAllItems() {
        // Create dialog
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("All Items");
        dialog.setHeaderText("Current Items in system");

        // Create content
        VBox content = new VBox(10);
        content.setPadding(new Insets(20, 20, 20, 20));

        // Get parents
        List<TOItem> items = CoolSuppliesFeatureSet3Controller.getItems();

        // Create text area for parents
        TextArea itemstText = new TextArea();
        itemstText.setEditable(false);
        itemstText.setPrefRowCount(15);
        itemstText.setPrefColumnCount(50);

        // Build the parents text
        StringBuilder sb = new StringBuilder();
        for (TOItem item : items) {
            sb.append(String.format("Name: %s\n", item.getName()));
            sb.append(String.format("Price: %s\n", item.getPrice()));
            sb.append("\n");
        }

        itemstText.setText(sb.toString());

        // Add text area to content and set up dialog
        content.getChildren().add(itemstText);
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.setContent(content);
        dialogPane.getButtonTypes().add(ButtonType.CLOSE);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.showAndWait();
    }
}
