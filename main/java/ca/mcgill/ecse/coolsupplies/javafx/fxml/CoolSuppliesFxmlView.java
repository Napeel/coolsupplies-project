package ca.mcgill.ecse.coolsupplies.javafx.fxml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.javafx.fxml.controllers.AddItemToBundleController;
import ca.mcgill.ecse.coolsupplies.javafx.fxml.controllers.ManageBundleItemsController;
import ca.mcgill.ecse.coolsupplies.javafx.fxml.controllers.ManageBundlesController;
import ca.mcgill.ecse.coolsupplies.javafx.fxml.controllers.ManageSystemItemsController;


public class CoolSuppliesFxmlView extends Application {

  public static final EventType<Event> REFRESH_EVENT = new EventType<>("REFRESH");
  private static CoolSuppliesFxmlView instance;
  private List<Node> refreshableNodes = new ArrayList<>();

  @Override
  public void start(Stage primaryStage) {
    instance = this;
    try {
      
      var root = (Pane) FXMLLoader.load(getClass().getResource("LogInPage.fxml"));
      root.setStyle(CoolSuppliesApplication.DARK_MODE ? "-fx-base: rgba(20, 20, 20, 255);" : "");
      var scene = new Scene(root);
      String cssFile = getClass().getResource("/styles.css").toExternalForm();
      scene.getStylesheets().add(cssFile);
      primaryStage.setScene(scene);
      primaryStage.setMinWidth(800);
      primaryStage.setMinHeight(600);
      primaryStage.setTitle("CoolSuppliesLogInPage");
      primaryStage.show();
      refresh();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void registerRefreshEvent(Node node) {
    refreshableNodes.add(node);
  }

  public void registerRefreshEvent(Node... nodes) {
    for (var node: nodes) {
      refreshableNodes.add(node);
    }
  }

  public void removeRefreshableNode(Node node) {
    refreshableNodes.remove(node);
  }

  public void refresh() {
    for (Node node : refreshableNodes) {
      node.fireEvent(new Event(REFRESH_EVENT));
    }
  }

  public static CoolSuppliesFxmlView getInstance() {
    return instance;
  }

}
