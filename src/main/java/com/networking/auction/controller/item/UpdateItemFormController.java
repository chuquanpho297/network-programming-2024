package com.networking.auction.controller.item;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.networking.auction.controller.Controller;
import com.networking.auction.models.Item;
import com.networking.auction.protocol.response.item.UpdateItemResponse;
import com.networking.auction.service.ItemService;
import com.networking.auction.util.JavaFxUtil;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UpdateItemFormController extends Controller implements Initializable {

    @FXML
    private TextField nameField;

    @FXML
    private TextField buyNowPriceField;

    @FXML
    private Button updateItemBtn;

    @FXML
    private Button backButton;

    private final ItemService itemService = ItemService.getInstance();
    private Item item;

    public UpdateItemFormController(Item item, Stage stage, String fxmlPath, Controller prev) throws IOException {
        super(stage, fxmlPath);
        this.item = item;
        nameField.setText(item.getName());
        buyNowPriceField.setText(String.valueOf(item.getBuyNowPrice()));
        
        this.setPreviousController(prev);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize ComboBox with time options

        backButton.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED,
                event -> {
                    if (this.mainController == null)
                        this.getPreviousController().show();
                    else
                        this.mainController.show();
                });
        updateItemBtn.setOnAction(event -> handleSubmitButtonAction());
    }

    @FXML
    private void handleSubmitButtonAction() {
        try {
            float buyNowPrice = Float.parseFloat(buyNowPriceField.getText());

            Task<UpdateItemResponse> task = new Task<>() {
                @Override
                protected UpdateItemResponse call() {
                    return itemService.updateItem(item.getItemId(),
                            buyNowPrice);
                }
            };

            task.setOnRunning((e) -> {
                updateItemBtn.setDisable(true);
                backButton.setDisable(true);
                updateItemBtn.setText("Updating...");
            });
            task.setOnSucceeded((e) -> {
                UpdateItemResponse response = task.getValue();
                if (response == null || response.getStatusCode() == 0) {
                    JavaFxUtil.createAlert("Error Dialog", "Item Error", "Failed to update item");
                }

                if (response.getStatusCode() == 1) {
                    JavaFxUtil.createAlert(AlertType.INFORMATION, "Information Dialog", "Item Information",
                            "Item updated successfully");
                }

                updateItemBtn.setDisable(false);
                backButton.setDisable(false);
                updateItemBtn.setText("Update Item");
                buyNowPriceField.clear();

            });
            task.setOnFailed((e) -> {
                Throwable exception = task.getException();
                exception.printStackTrace();
                JavaFxUtil.createAlert("Error Dialog", "Item Error", "An error occurred during item creation");
                updateItemBtn.setDisable(false);
                backButton.setDisable(false);
                updateItemBtn.setText("Update Item");
                buyNowPriceField.clear();
            });

            new Thread(task).start();

        } catch (Exception e) {
            JavaFxUtil.createAlert("Error Dialog", "Update Item Error", "Invalid input");
        }
    }

    void setItem(Item item) {
        this.item = item;
    }
}