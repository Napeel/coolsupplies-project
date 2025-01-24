package ca.mcgill.ecse.coolsupplies.javafx.fxml;

import ca.mcgill.ecse.coolsupplies.javafx.fxml.controllers.Refreshable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainPagecontroller {

    @FXML
    private TabPane mainTabPane;

    // Map to store controllers for included FXML files
    private final Map<Tab, Refreshable> tabControllers = new HashMap<>();

    @FXML
    public void initialize() {
        for (Tab tab : mainTabPane.getTabs()) {
            loadControllerForTab(tab);
        }

        mainTabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> refreshTabController(newTab));
    }

    /**
     * Loads the controller for the given tab. If the tab contains another TabPane, it recursively processes children and add listener.
     */
    private void loadControllerForTab(Tab tab) {
        try {
            // Check if the content is another TabPane (nested tabs)
            if (tab.getContent() instanceof TabPane nestedTabPane) {

              // Add listener for nested TabPane
              nestedTabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldNestedTab, newNestedTab) -> refreshTabController(newNestedTab));

                // Recursively load controllers for the nested TabPane
                for (Tab nestedTab : nestedTabPane.getTabs()) {
                    loadControllerForTab(nestedTab);
                }
            } else {
                // Otherwise, load the FXML for the tab
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(getFXMLFileForTab(tab)));
                tab.setContent(loader.load());

                // Retrieve and store the controller if it implements Refreshable
                Object controller = loader.getController();
                if (controller instanceof Refreshable) {
                    tabControllers.put(tab, (Refreshable) controller);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Calls the `refresh` method on the controller associated with the given tab.
     */
    private void refreshTabController(Tab tab) {
        if (tabControllers.containsKey(tab)) {
            Refreshable controller = tabControllers.get(tab);
            if (controller != null) {
                controller.refresh();
            }
        }
    }

    /**
     * Returns the FXML file path for a given tab.
     */
    private String getFXMLFileForTab(Tab tab) {
        switch (tab.getText()) {
            case "Admin":
                return "pages/AdminPage.fxml";
            case "Parents":
                return "pages/ManageParents.fxml";
            case "Students":
                return "pages/ManageStudents.fxml";
            case "System Items":
                return "pages/ManageSystemItems.fxml";
            case "Manage Bundles":
                return "pages/ManageBundles.fxml";
            case "Manage Bundle Items":
                return "pages/ManageBundleItems.fxml";
            case "Add to Bundles":
                return "pages/AddItemToBundle.fxml";
            case "Start Order":
                return "pages/StartAnOrder.fxml";
            case "Pick Up Order":
                return "pages/PickUpAnOrder.fxml";
            case "Payment Order":
                return "pages/PaymentOrder.fxml";
            case "Modify Order":
                return "pages/ModifyOrder.fxml";
            case "Student-Parent":
                return "pages/ManageStudentParent.fxml";
            default:
                return null;
        }
    }
}
