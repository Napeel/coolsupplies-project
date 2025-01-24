package ca.mcgill.ecse.coolsupplies.javafx.fxml.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import java.util.List;
import javafx.scene.control.Alert;
import java.sql.Date;
import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet6Controller;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet7Controller;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet8Controller;
import ca.mcgill.ecse.coolsupplies.controller.TOOrder;
import ca.mcgill.ecse.coolsupplies.javafx.fxml.CoolSuppliesFxmlView;
import ca.mcgill.ecse.coolsupplies.model.BundleItem;
import ca.mcgill.ecse.coolsupplies.model.GradeBundle;
import ca.mcgill.ecse.coolsupplies.model.Item;
import ca.mcgill.ecse.coolsupplies.model.OrderItem;
import ca.mcgill.ecse.coolsupplies.model.BundleItem.PurchaseLevel;
import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;
import ca.mcgill.ecse.coolsupplies.model.Parent;
import ca.mcgill.ecse.coolsupplies.model.Student;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet1Controller;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet2Controller;
import ca.mcgill.ecse.coolsupplies.controller.TOStudent;
import ca.mcgill.ecse.coolsupplies.model.Order;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.geometry.Insets;
import java.util.ArrayList;
import javafx.scene.control.ButtonType;
import ca.mcgill.ecse.coolsupplies.controller.TOOrderItem;
import ca.mcgill.ecse.coolsupplies.controller.TOParent;
import java.time.LocalDate;

public class StartOrderController implements Refreshable{
    @FXML private ComboBox<String> StudentNameBox;
    @FXML private Button handleStartOrder;
    @FXML private Button viewAllOrders;
    @FXML private Button handleUpdateOrder;
    @FXML private Button handleCancelOrder;
    @FXML private TextField orderField;
    @FXML private TextField parentEmailBox;
    @FXML private TextField levelBox;
    @FXML private DatePicker dateField;
    @FXML private Button refreshButton;

    public void refresh(){
        handleRefresh();
    }

    /**
     * Initialize the controller
     * 
     * @author: Yarno Grenier
     */
    @FXML
    public void initialize() {

        // Add student names to StudentNameBox
        refreshStudents();
        CoolSuppliesFxmlView.getInstance().registerRefreshEvent(StudentNameBox);

        // Add handler for viewAllOrders button
        viewAllOrders.setOnAction(e -> handleViewAllOrders());
    }

    /**
     * Handle starting a new order
     * 
     * @author: Yarno Grenier
     */
    @FXML
    public void handleStartOrder(ActionEvent event) {
        String purchaseLevel = levelBox.getText();
        String studentName = StudentNameBox.getValue();
        String parentEmail = parentEmailBox.getText();
        
        boolean parentExists = false;
        for (TOParent parent : CoolSuppliesFeatureSet1Controller.getParents()) {
        	if (parent.getEmail().equals(parent.getEmail())) {
        		parentExists = true;
        		break;
        	}
        }
        
        if (!parentExists) {
        	showError("There is no parent with this email.");
            return;
        }
        
        boolean studentExists = false;
        for (TOStudent student : CoolSuppliesFeatureSet6Controller.getStudentsOfParent(parentEmail)) {
        	if (student.getName().equals(studentName)) {
        		studentExists = true;
        		break;
        	}
        }
        
        if (!studentExists) {
            showError("The student doesn't exist for this parent");
            return;
        }
        
        String orderNumText = orderField.getText();
    	
        if (orderNumText == null || orderNumText.equals("")) {
            showError("Please specify a valid order number");
            return;
        }
    	
        Integer orderNumber = Integer.parseInt(orderNumText);
        
        for (TOOrder order : CoolSuppliesFeatureSet8Controller.getAllOrders()) {
        	if (order.getNumber() == orderNumber) {
                showError("Order number already exists");
                return;
        	}
        }
        
        if (purchaseLevel == null || !(purchaseLevel.equals("Mandatory") || purchaseLevel.equals("Recommended") || purchaseLevel.equals("Optional"))) {
            showError("Please choose an appropriate purchase level");
            return;
        }

        LocalDate currentDate = dateField.getValue();
        
        if (currentDate == null) {
        	showError("Date cannot be null");
        	return;
        }
        Date realDate = Date.valueOf(currentDate);

        String error = CoolSuppliesFeatureSet6Controller.startOrder(
            orderNumber, 
            realDate,
            purchaseLevel,
            parentEmail,
            studentName
        );

        if (error.contains("fully")) {
            handleClear();
        } else {
            showError(error);
        }
    }

    /**
     * Handle updating an existing order
     * 
     * @author: Yarno Grenier
     */
    @FXML
    public void handleUpdateOrder(ActionEvent event) {
    	
    	
    	String orderNumText = orderField.getText();
        String purchaseLevel = levelBox.getText();
        String studentName = StudentNameBox.getValue();
        if (orderNumText == null || orderNumText.equals("")) {
            showError("Please specify a valid order number");
            return;
        }
    	
        Integer orderNumber = Integer.parseInt(orderNumText);
        
        
        boolean orderExists = false;
        TOOrder rightOrder = null;
        for (TOOrder order : CoolSuppliesFeatureSet8Controller.getAllOrders()) {
        	if (order.getNumber() == orderNumber) {
                orderExists = true;
                rightOrder = order;
                break;
        	}
        }
        
        if (!orderExists || rightOrder == null) {
        	showError("Order doesn't exist");
            return;
        }

        String parentEmail = rightOrder.getParentEmail();

        boolean studentExists = false;
        for (TOStudent student : CoolSuppliesFeatureSet6Controller.getStudentsOfParent(parentEmail)) {
        	if (student.getName().equals(studentName)) {
        		studentExists = true;
        	}
        }
        
        if (!studentExists) {
            showError("The student doesn't exist for this parent");
            return;
        }
        
        if (purchaseLevel == null || !(purchaseLevel.equals("Mandatory") || purchaseLevel.equals("Recommended") || purchaseLevel.equals("Optional"))) {
            showError("Please choose an appropriate purchase level");
            return;
        }


        String error = CoolSuppliesFeatureSet8Controller.UpdateOrder(
            orderNumber,
            purchaseLevel,
            studentName
        );

        if (error.contains("fully")) {
            handleClear();
        } else {
            showError(error);
        }
    }

    /**
     * Handle cancelling an order
     * 
     * @author: Yarno Grenier
     */
    @FXML
    public void handleCancelOrder(ActionEvent event) {
    	String orderNumText = orderField.getText();
        if (orderNumText == null || orderNumText.equals("")) {
            showError("Please specify a valid order number");
            return;
        }
    	
        Integer orderNumber = Integer.parseInt(orderNumText);
        
        boolean orderExists = false;
        for (TOOrder order : CoolSuppliesFeatureSet8Controller.getAllOrders()) {
        	if (order.getNumber() == orderNumber) {
                orderExists = true;
                break;
        	}
        }
        
        if (!orderExists) {
        	showError("Order doesn't exist");
        	return;
        }

        String error = CoolSuppliesFeatureSet8Controller.CancelOrder(orderNumber);

        if (error.contains("fully")) {
            handleClear();
        } else {
            showError(error);
        }
    }

    /**
     * Clear all input fields
     */
    private void handleClear() {
        StudentNameBox.getSelectionModel().clearSelection();
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

    /**
     * Refresh the list of students in the choice box
     */
    private void refreshStudents() {
        List<TOStudent> students = CoolSuppliesFeatureSet2Controller.getStudents();
        ObservableList<String> studentNames = FXCollections.observableArrayList();
        for (TOStudent student : students) {
            studentNames.add(student.getName());
        }
        StudentNameBox.setItems(studentNames);
    }

    /**
     * Handle viewing all orders in a popup
     */
    private void handleViewAllOrders() {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("All Orders");
        dialog.setHeaderText("Current Orders in System");
        
        VBox content = new VBox(10);
        content.setPadding(new Insets(20, 20, 20, 20));
        
        List<TOOrder> orders = CoolSuppliesFeatureSet8Controller.getAllOrders();
        orders.sort((o1, o2) -> Integer.compare(o1.getNumber(), o2.getNumber()));
        
        TextArea ordersText = new TextArea();
        ordersText.setEditable(false);
        ordersText.setPrefRowCount(15);
        ordersText.setPrefColumnCount(80);
        
        StringBuilder sb = new StringBuilder();
        for (TOOrder order : orders) {
            sb.append(String.format("Order:   %s id = %d; date = %s; parent = %s; student = %s; level = %s\n",
                order.getStatus().toUpperCase(),
                order.getNumber(),
                order.getDate(),
                order.getParentEmail(),
                order.getStudentName(),
                order.getLevel().toLowerCase()));
            
            List<TOOrderItem> items = order.getTOOrderItems();
            if (!items.isEmpty()) {
                sb.append("               with ");
                
                String currentBundle = null;
                StringBuilder bundleBuilder = new StringBuilder();
                boolean firstItem = true;
                
                for (TOOrderItem item : items) {
                    String gradeBundleName = item.getGradeBundleName();
                    
                    if (gradeBundleName != null && !gradeBundleName.isEmpty()) {
                        boolean isEmptyBundle = items.stream()
                            .filter(i -> gradeBundleName.equals(i.getGradeBundleName()))
                            .count() == 1;
                            
                        if (!gradeBundleName.equals(currentBundle)) {
                            if (currentBundle != null) {
                                bundleBuilder.append(")");
                                if (!firstItem) sb.append(", ");
                                sb.append(bundleBuilder);
                                bundleBuilder = new StringBuilder();
                                firstItem = false;
                            }
                            currentBundle = gradeBundleName;
                            
                            bundleBuilder.append(String.format("%d %s ", 
                                item.getQuantity(), 
                                gradeBundleName));
                                
                            if (isEmptyBundle) {
                                bundleBuilder.append("(no applicable items in bundle)");
                                firstItem = false;
                            } else {
                                bundleBuilder.append("(");
                                if (!item.getItemName().equals(gradeBundleName)) {
                                    bundleBuilder.append(String.format("%d %s, $%.2f", 
                                        item.getQuantity(), 
                                        item.getItemName(),
                                        item.getPrice()));
                                    firstItem = false;
                                }
                            }
                        } else if (!isEmptyBundle && !item.getItemName().equals(gradeBundleName)) {
                            if (!firstItem) {
                                bundleBuilder.append("; ");
                            }
                            bundleBuilder.append(String.format("%d %s, $%.2f", 
                                item.getQuantity(), 
                                item.getItemName(),
                                item.getPrice()));
                            
                            if (item.getDiscountDeducted() != 0) {
                                bundleBuilder.append(String.format(", discount=%.2f", 
                                    Math.abs(item.getDiscountDeducted())));
                            }
                            firstItem = false;
                        }
                    } else {
                        if (currentBundle != null) {
                            bundleBuilder.append(")");
                            if (!firstItem) sb.append(", ");
                            sb.append(bundleBuilder);
                            bundleBuilder = new StringBuilder();
                            currentBundle = null;
                            firstItem = false;
                        }
                        if (!firstItem) sb.append(", ");
                        sb.append(String.format("%d %s%s ($%.2f)", 
                            item.getQuantity(), 
                            item.getItemName(),
                            item.getQuantity() > 1 ? "s" : "",
                            item.getPrice()));
                        if (item.getDiscountDeducted() != 0) {
                            sb.append(String.format(", discount=%.2f", 
                                Math.abs(item.getDiscountDeducted())));
                        }
                        firstItem = false;
                    }
                }
                
                if (currentBundle != null) {
                    bundleBuilder.append(")");
                    if (!firstItem) sb.append(", ");
                    sb.append(bundleBuilder);
                }
                
                if (order.getTotalPrice() > 0) {
                    sb.append(String.format(" [total: $%.2f]\n", order.getTotalPrice()));
                } else {
                    sb.append("\n");
                }
            }
            sb.append("\n");
        }
        
        ordersText.setText(sb.toString());
        content.getChildren().add(ordersText);
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.setContent(content);
        dialogPane.getButtonTypes().add(ButtonType.CLOSE);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.showAndWait();
    }

    @FXML
    private void handleRefresh() {
        refreshStudents();
        handleClear();
        orderField.clear();
        parentEmailBox.clear();
        levelBox.clear();
        dateField.setValue(null);
    }
}
