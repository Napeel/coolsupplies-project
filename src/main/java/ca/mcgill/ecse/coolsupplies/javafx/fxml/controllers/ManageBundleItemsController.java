package ca.mcgill.ecse.coolsupplies.javafx.fxml.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet4Controller;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet5Controller;
import ca.mcgill.ecse.coolsupplies.controller.TOGradeBundle;
import ca.mcgill.ecse.coolsupplies.controller.TOBundleItem;
import javafx.event.ActionEvent;

import java.util.List;

/**
 * Controller for managing bundle items.
 * 
 * @author Nabil Bin Muzafar Shah
 */
public class ManageBundleItemsController implements Refreshable{
    @FXML
    private ComboBox<String> bundleComboBox;
    @FXML
    private TextField itemNameField;
    @FXML
    private TextField itemQuantityField;
    @FXML
    private ComboBox<String> itemTypeComboBox; 
    @FXML
    private ListView<String> itemsInBundleListView;
    @FXML
    private Button updateItemButton;
    @FXML
    private Button removeItemButton;
    @FXML
    private Button clearButton;
    @FXML
    private ComboBox<String> ItemSelectedDropdown;
    @FXML
    private Button refreshButton;

    public void refresh() {
        handleRefresh();
    }

    @FXML
    public void initialize() {
        refreshBundles();
        itemTypeComboBox.getItems().addAll("Mandatory", "Recommended", "Optional"); 

        bundleComboBox.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldVal, newVal) -> {
                    if (newVal != null) {
                        refreshItemsInBundle(newVal);
                    }
                });
    }

    @FXML
    public void handleUpdateBundleItem(ActionEvent event) {
        String bundleName = bundleComboBox.getSelectionModel().getSelectedItem();
        String itemName = ItemSelectedDropdown.getSelectionModel().getSelectedItem();

        try {
            int newQuantity = Integer.parseInt(itemQuantityField.getText());
            String newType = itemTypeComboBox.getSelectionModel().getSelectedItem(); 
            String error = CoolSuppliesFeatureSet5Controller.updateBundleItem(itemName, bundleName,
                    newQuantity, newType);

            if (error.isEmpty()) {
                handleClearItemFields();
                refreshItemsInBundle(bundleName);
            } else {
                showError(error);
            }
        } catch (NumberFormatException e) {
            showError("Invalid quantity. Please enter a valid number.");
        }
    }

    @FXML
    public void handleRemoveItemFromBundle(ActionEvent event) {
        String bundleName = bundleComboBox.getSelectionModel().getSelectedItem();
        String itemName = ItemSelectedDropdown.getSelectionModel().getSelectedItem();
        String error = CoolSuppliesFeatureSet5Controller.deleteBundleItem(itemName, bundleName);

        if (error.isEmpty()) {
            handleClearItemFields();
            refreshItemsInBundle(bundleName);
        } else {
            showError(error);
        }
    }

    @FXML
    public void handleClearFields() {
        handleClearItemFields();
    }

    @FXML
    private void handleRefresh() {
        String currentBundle = bundleComboBox.getValue();
        
        refreshBundles();
    
        if (currentBundle != null && bundleComboBox.getItems().contains(currentBundle)) {
            bundleComboBox.setValue(currentBundle);
            refreshItemsInBundle(currentBundle);
        } else {
            itemsInBundleListView.setItems(null);
            ItemSelectedDropdown.setItems(null);
        }
    }

    private void refreshBundles() {
        List<TOGradeBundle> bundles = CoolSuppliesFeatureSet4Controller.getBundles();
        ObservableList<String> bundleNames = FXCollections.observableArrayList();
        for (TOGradeBundle bundle : bundles) {
            bundleNames.add(bundle.getName());
        }
        bundleComboBox.setItems(bundleNames);
    }

    private void refreshItemsInBundle(String bundleName) {
        if (bundleName != null) {
            List<TOBundleItem> items = CoolSuppliesFeatureSet5Controller.getBundleItems(bundleName);
            if (items == null) {
                itemsInBundleListView.setItems(null);
                ItemSelectedDropdown.setItems(null);
                return;
            }
            ObservableList<String> itemNames = FXCollections.observableArrayList();
            for (TOBundleItem bundleItem : items) {
                itemNames.add(bundleItem.getItemName() + " (Qty: " + bundleItem.getQuantity()
                        + ", Type: " + bundleItem.getLevel() + ")");
            }
            itemsInBundleListView.setItems(itemNames);

            ObservableList<String> dropdownItems = FXCollections.observableArrayList();
            for (TOBundleItem bundleItem : items) {
                dropdownItems.add(bundleItem.getItemName());
            }
            ItemSelectedDropdown.setItems(dropdownItems);
        } else {
            itemsInBundleListView.setItems(null);
            ItemSelectedDropdown.setItems(null);
        }
    }

    private void handleClearItemFields() {
        itemQuantityField.clear();
        itemTypeComboBox.getSelectionModel().clearSelection(); 
        ItemSelectedDropdown.getSelectionModel().clearSelection();
        itemsInBundleListView.getSelectionModel().clearSelection();
    }

    private void showError(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}


