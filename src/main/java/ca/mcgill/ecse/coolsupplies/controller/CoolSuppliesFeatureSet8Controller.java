package ca.mcgill.ecse.coolsupplies.controller;

import java.util.List;

import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.model.BundleItem;
import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;
import ca.mcgill.ecse.coolsupplies.model.GradeBundle;
import ca.mcgill.ecse.coolsupplies.model.InventoryItem;
import ca.mcgill.ecse.coolsupplies.model.Item;
import ca.mcgill.ecse.coolsupplies.model.Order;
import ca.mcgill.ecse.coolsupplies.model.OrderItem;

import java.util.ArrayList;

import ca.mcgill.ecse.coolsupplies.persistence.CoolSuppliesPersistence;

import java.sql.Date;

public class CoolSuppliesFeatureSet8Controller {

  /**
   * Update the order with the given purchase level and student name
   *
   * @param orderNumber       The number identity of the order.
   * @param purchaseLevelName The purchase level desired.
   * @param studentName       The student name desired.
   * @return Message giving the status of the update order operation.
   * @author Jeffrey Lim
   */

  public static String UpdateOrder(int orderNumber, String purchaseLevelName, String studentName) {

    if (!Order.hasWithNumber(orderNumber)) {
      return "Order " + orderNumber + " does not exist";
    }

    try {
      Order currentOrder = Order.getWithNumber(orderNumber);
      currentOrder.updateOrder(purchaseLevelName, studentName);
      CoolSuppliesPersistence.save();
    } catch (Exception e) {
      return e.getMessage();
    }
    return "Succesfully updated the order";
  }


  /**
   * @param orderNumber The number identity of the order.
   * @param itemName    The name of the item to be changed.
   * @param Quantity    The quantity desired.
   * @return Message giving the status of the add item operation.
   * @author Jeffrey Lim
   */
  public static String AddItemOrder(int orderNumber, String itemName, int Quantity) {


    if (!Order.hasWithNumber(orderNumber)) {
      return "Order " + orderNumber + " does not exist";
    }

    try {
      Order currentOrder = Order.getWithNumber(orderNumber);
      currentOrder.addItem(itemName, Quantity);
      CoolSuppliesPersistence.save();
    } catch (Exception e) {
      return e.getMessage();
    }

    return "Succesfully updated the order";
  }

  /**
   * Updates the quantity of an item in an order.
   *
   * @param orderNumber The order number with the item to update.
   * @param itemName    The name of the item to update.
   * @param quantity    The new quantity to update the item with.
   * @return Returns an empty string if successful, or an error message otherwise.
   * @author Sofia Galanopoulos
   */
  public static String UpdateItem(int orderNumber, String itemName, int quantity) {

    if (!Order.hasWithNumber(orderNumber)) {
      return "Order " + orderNumber + " does not exist";
    }

    try {
      Order currentOrder = Order.getWithNumber(orderNumber);
      currentOrder.updateItem(itemName, quantity);
      CoolSuppliesPersistence.save();
    } catch (Exception e) {
      String errorMessage = e.getMessage();
      return errorMessage;
    }
    return "Succesfully updated item";

  }

  /**
   * Removes a specific item from an order.
   *
   * @param orderNumber The order number to remove the item from.
   * @param itemName    The name of the item to be removed.
   * @return Returns an empty string if successful, or an error message if unsuccesful.
   * @author Sofia Galanopoulos
   */
  public static String removeItem(int orderNumber, String itemName) {

    // check if order number is in bounds
    if (!Order.hasWithNumber(orderNumber)) {
      return "Order " + orderNumber + " does not exist";
    }

    try {
      // get order
      Order currentOrder = Order.getWithNumber(orderNumber);
      currentOrder.removeItem(itemName);
      CoolSuppliesPersistence.save();
    } catch (Exception e) {
      return e.getMessage();
    }

    return "Item has been removed successfully!";
  }


  /**
   * Cancels an order.
   *
   * @param orderNumber The number of the order to cancel.
   * @return Returns a success message if successful, or an error message if unsuccessful.
   * @author Yarno Grenier
   */
  public static String CancelOrder(int orderNumber) {


    if (!Order.hasWithNumber(orderNumber)) {
      return "Order " + orderNumber + " does not exist";
    }

    try {
      // get order
      Order order = Order.getWithNumber(orderNumber);
      order.cancel();
      CoolSuppliesPersistence.save();
    } catch (Exception e) {
      return e.getMessage();
    }
    return "Succesfully cancelled order";
  }

  /**
   * Marks an order as picked up.
   *
   * @param orderNumber The number of the order to mark as picked up.
   * @return Returns a success message if successful, or an error message if unsuccessful.
   * @author Yarno Grenier
   */
  public static String PickedUpByStudent(int orderNumber) {

    // check if order number is in bounds
    if (!Order.hasWithNumber(orderNumber)) {
      return "Order " + orderNumber + " does not exist";
    }

    try {
      // get order
      Order order = Order.getWithNumber(orderNumber);
      order.pickedUpByStudent();
      CoolSuppliesPersistence.save();
    } catch (Exception e) {
      return e.getMessage();
    }
    return "Succesfully picked up";
  }


  /**
   * Process a regular payment for an order.
   *
   * @param orderNumber       ID of the order to process payment for
   * @param authorizationCode Payment authorization code
   * @return Success message if payment is processed
   * @throws RuntimeException if order is not found or payment processing fails
   * @author Nabil Bin Muzafar Shah
   */
  public static String processOrderPayment(int orderNumber, String authorizationCode) {
    if (!Order.hasWithNumber(orderNumber)) {
      return "Order " + orderNumber + " does not exist";
    }

    try {
      Order currentOrder = Order.getWithNumber(orderNumber);
      currentOrder.paymentForOrder(authorizationCode);
      CoolSuppliesPersistence.save();
    } catch (Exception e) {
      return e.getMessage();
    }
    return "Succesfully processed the payment for the order";
  }


  /**
   * Process a penalty payment for an order that requires both penalty and order payments.
   *
   * @param orderNumber              ID of the order to process penalty payment for
   * @param penaltyAuthorizationCode Authorization code for the penalty payment
   * @param orderAuthorizationCode   Authorization code for the order payment
   * @return Success message if both payments are processed
   * @throws RuntimeException if order is not found, not in penalized state, or payment processing
   *                          fails
   * @author Nabil Bin Muzafar Shah
   */
  public static String processPenaltyPayment(int orderNumber, String penaltyAuthorizationCode,
                                             String orderAuthorizationCode) {

    if (!Order.hasWithNumber(orderNumber)) {
      return "Order " + orderNumber + " does not exist";
    }

    try {
      Order order = Order.getWithNumber(orderNumber);
      order.paymentForPenalty(penaltyAuthorizationCode, orderAuthorizationCode);
      CoolSuppliesPersistence.save();
      return "Successfully processed penalty payment";
    } catch (Exception e) {
      return e.getMessage();
    }
  }


  /**
   * Start the school year for an order, which transitions it to either Penalized or Prepared state
   * depending on its current state
   *
   * @param orderNumber ID of the order to start school year for
   * @return Success message if school year is started successfully
   * @throws RuntimeException if order is not found or in invalid state
   * @author Matthew Petruzziello
   */
  public static String startSchoolYear(int orderNumber) {

    if (!Order.hasWithNumber(orderNumber)) {
      return "Order " + orderNumber + " does not exist";
    }

    try {
      Order order = Order.getWithNumber(orderNumber);
      order.schoolYearStarts();
      CoolSuppliesPersistence.save();
      return "Successfully started school year for order " + orderNumber;
    } catch (RuntimeException e) {
      return e.getMessage();
    }
  }

  /**
   * Get an order from the system
   *
   * @return TOOrder object with its order information
   * @author Bryan Viet Hoang Vu
   */

  public static TOOrder getOrder(int orderNumber) {
    Order order = Order.getWithNumber(orderNumber);

    if (order == null) {
      return null;
    }

    String parentEmail = order.getParent().getEmail();
    String studentName = order.getStudent().getName();
    String status = order.getStatusFullName();
    Date date = order.getDate();
    String purchaseLevel = order.getLevel().toString();
    String authorizationCode = order.getAuthorizationCode();
    String penaltyAuthorizationCode = order.getPenaltyAuthorizationCode();

    // Need to calculate the final price and figure out all the order items in an array
    TOOrderItem[] orderItemArray;

    // Set an int that we can increment
    double totalPrice = 0;

    // Grab order items from a bundle and prepare an array list to create transfer objects
    List<OrderItem> orderItems = order.getOrderItems();
    List<TOOrderItem> TOOrderItems = new ArrayList<TOOrderItem>();

    for (OrderItem orderItem : orderItems) {
      InventoryItem item = orderItem.getItem();

      int quantity = orderItem.getQuantity();
      String itemName;
      String gradeBundleName = "";
      double price;
      double discount;

      // Case for if we have just a singular item
      if (item instanceof Item) {
        Item castedItem = (Item) item;
        itemName = castedItem.getName();
        price = castedItem.getPrice() * orderItem.getQuantity();
        totalPrice += price;
        discount = 0;
        price = castedItem.getPrice();
        TOOrderItems.add(new TOOrderItem(quantity, itemName, gradeBundleName, price, discount));
      }
      // Case for if we have a bundle
      else {
        GradeBundle castedItem = (GradeBundle) item;
        TOOrderItems.add(new TOOrderItem(quantity, item.getName(), item.getName(), 0, 0));

        List<BundleItem> bundleItems = castedItem.getBundleItems();

        double priceValue = 0;
        double discountValue = 0;

        for (BundleItem bundleItem : bundleItems) {
          String bundlePurchaseLevel = bundleItem.getLevel().toString();
          quantity = orderItem.getQuantity() * bundleItem.getQuantity();

          // Skip items that don't match the purchase level
          if (purchaseLevel.equals("Mandatory") &&
              (bundlePurchaseLevel.equals("Recommended") || bundlePurchaseLevel.equals("Optional"))) {
              continue;
          } else if (purchaseLevel.equals("Recommended") &&
                     bundlePurchaseLevel.equals("Optional")) {
              continue;
          }

          int discountNumber = 0;
          int count = 0;

          for (BundleItem bundleItemCheck : bundleItems) {
              bundlePurchaseLevel = bundleItemCheck.getLevel().toString();
              if (bundleItem.getBundle().getName().equals(bundleItemCheck.getBundle().getName())) {
                  if (purchaseLevel.equals("Mandatory") &&
                      (bundlePurchaseLevel.equals("Recommended") || bundlePurchaseLevel.equals("Optional"))) {
                      continue;
                  } else if (purchaseLevel.equals("Recommended") &&
                             bundlePurchaseLevel.equals("Optional")) {
                      continue;
                  }
                  count += 1;
              }
          }

          if (count >= 2) {
              discountNumber = castedItem.getDiscount();
          }

          itemName = bundleItem.getItem().getName();
          gradeBundleName = bundleItem.getBundle().getName();

          priceValue = bundleItem.getItem().getPrice() * bundleItem.getQuantity() * orderItem.getQuantity();
          discountValue = priceValue * discountNumber * 0.01;

          price = priceValue - discountValue;
          totalPrice += price;

          discount = -bundleItem.getItem().getPrice() * discountNumber * 0.01;

          price = bundleItem.getItem().getPrice();
          TOOrderItems.add(new TOOrderItem(quantity, itemName, gradeBundleName, price, discount));
        }
      }
    }
    orderItemArray = TOOrderItems.toArray(new TOOrderItem[0]);
    return new TOOrder(parentEmail, studentName, status, orderNumber, date,
            purchaseLevel, authorizationCode, penaltyAuthorizationCode, totalPrice,
            orderItemArray);
  }

  /**
   * Get all orders in the system
   *
   * @return List of TOOrder objects with all order information
   * @author Matthew Petruzziello
   */
  public static List<TOOrder> getAllOrders() {
    CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();
    List<TOOrder> orders = new ArrayList<TOOrder>();

    for (Order order : coolSupplies.getOrders()) {
      TOOrder toOrder = getOrder(order.getNumber());
      if (toOrder != null) {
        orders.add(toOrder);
      }
    }

    return orders;
  }
}
