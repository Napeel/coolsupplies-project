package ca.mcgill.ecse.coolsupplies.javafx.fxml.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import java.util.List;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet5Controller;
import ca.mcgill.ecse.coolsupplies.controller.TOBundleItem;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet4Controller;
import ca.mcgill.ecse.coolsupplies.controller.TOGradeBundle;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet3Controller;
import ca.mcgill.ecse.coolsupplies.controller.TOItem;

/**
 * Controller for adding items to a bundle.
 * 
 * @author Nabil Bin Muzafar Shah
 */
public class AddItemToBundleController implements Refreshable{

    @FXML
    private ComboBox<String> bundleComboBox;

    @FXML
    private TextField itemQuantityField;

    @FXML
    private ComboBox<String> itemTypeComboBox; // Updated to ComboBox

    @FXML
    private ComboBox<String> itemSelectedComboBox;

    @FXML
    private ListView<String> itemsInBundleListView;

    @FXML
    private Button addItemButton;

    @FXML
    private Button clearButton;

    @FXML
    private Button refreshButton;

    public void refresh() {
        handleRefresh(null);
    }

    @FXML
    public void initialize() {
        refreshBundles();
        refreshAvailableItems();
        itemTypeComboBox.setItems( // Updated from ChoiceBox to ComboBox
                FXCollections.observableArrayList("Mandatory", "Recommended", "Optional"));
        bundleComboBox.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        refreshItemsInBundle(newValue);
                    }
                });
    }

    private void refreshBundles() {
        List<TOGradeBundle> bundles = CoolSuppliesFeatureSet4Controller.getBundles();
        ObservableList<String> bundleNames = FXCollections.observableArrayList();
        for (TOGradeBundle bundle : bundles) {
            bundleNames.add(bundle.getName());
        }
        bundleComboBox.setItems(bundleNames);
    }

    private void refreshAvailableItems() {
        List<TOItem> allItems = CoolSuppliesFeatureSet3Controller.getItems();
        ObservableList<String> itemNames = FXCollections.observableArrayList();
        for (TOItem item : allItems) {
            itemNames.add(item.getName());
        }
        itemSelectedComboBox.setItems(itemNames);
    }

    @FXML
    private void handleAddItemToBundle(ActionEvent event) {
        String bundleName = bundleComboBox.getSelectionModel().getSelectedItem();
        String itemName = itemSelectedComboBox.getSelectionModel().getSelectedItem();

        List<TOBundleItem> bundleItems =
                CoolSuppliesFeatureSet5Controller.getBundleItems(bundleName);
        if (bundleItems != null) {
            for (TOBundleItem item : bundleItems) {
                if (item.getItemName().equals(itemName)) {
                    showError("This item is already in the bundle!");
                    return;
                }
            }
        }

        try {
            int quantity = Integer.parseInt(itemQuantityField.getText());
            String type = itemTypeComboBox.getSelectionModel().getSelectedItem(); // Updated to ComboBox

            String error = CoolSuppliesFeatureSet5Controller.addBundleItem(quantity, type, itemName,
                    bundleName);
            if (error.isEmpty()) {
                handleClearFields(event);
                refreshItemsInBundle(bundleName);
            } else {
                showError(error);
            }
        } catch (NumberFormatException e) {
            showError("Please enter a valid quantity.");
        }
    }

    @FXML
    private void handleClearFields(ActionEvent event) {
        itemQuantityField.clear();
        itemTypeComboBox.getSelectionModel().clearSelection(); // Updated to ComboBox
        itemSelectedComboBox.getSelectionModel().clearSelection();
        itemsInBundleListView.getSelectionModel().clearSelection();
        System.out.println("Cleared input fields.");
    }

    private void refreshItemsInBundle(String bundleName) {
        if (bundleName != null) {
            List<TOBundleItem> items = CoolSuppliesFeatureSet5Controller.getBundleItems(bundleName);
            if (items == null) {
                itemsInBundleListView.setItems(null);
                return;
            }
            ObservableList<String> itemDetails = FXCollections.observableArrayList();
            for (TOBundleItem bundleItem : items) {
                String itemDetail = String.format("%s (Qty: %d, Type: %s)", 
                    bundleItem.getItemName(), 
                    bundleItem.getQuantity(), 
                    bundleItem.getLevel());
                itemDetails.add(itemDetail);
            }
            itemsInBundleListView.setItems(itemDetails);
        } else {
            itemsInBundleListView.setItems(null);
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleRefresh(ActionEvent event) {
        String currentBundle = bundleComboBox.getValue();

        refreshBundles();
        refreshAvailableItems();

        if (currentBundle != null && bundleComboBox.getItems().contains(currentBundle)) {
            bundleComboBox.setValue(currentBundle);
            refreshItemsInBundle(currentBundle);
        }
    }

}


