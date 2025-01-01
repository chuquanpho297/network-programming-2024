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

    UpdateItemFormController(Item item) {
        this.item = item;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nameField.setText(item.getName());
        buyNowPriceField.setText(String.valueOf(item.getBuyNowPrice()));

        backButton.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED,
                event -> {
                    try {
                        switchToScreen((Stage) ((Node) event.getSource()).getScene().getWindow(), "room");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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
}