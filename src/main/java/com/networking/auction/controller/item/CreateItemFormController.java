package com.networking.auction.controller.item;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.networking.auction.controller.Controller;
import com.networking.auction.protocol.response.item.CreateItemResponse;
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

public class CreateItemFormController extends Controller implements Initializable {

    @FXML
    private TextField nameField;

    @FXML
    private TextField buyNowPriceField;

    @FXML
    private TextField bidIncrementField;

    @FXML
    private Button createItemBtn;

    @FXML
    private Button backButton;

    private final ItemService itemService = ItemService.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        backButton.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED,
                event -> {
                    try {
                        switchToScreen((Stage) ((Node) event.getSource()).getScene().getWindow(), "room");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }

    @FXML
    private void handleSubmitButtonAction() {
        try {
            String name = nameField.getText();
            String buyNowPriceStr = buyNowPriceField.getText();
            String bidIncrementStr = bidIncrementField.getText();

            if (name.isEmpty() || bidIncrementStr.isEmpty()) {
                JavaFxUtil.createAlert("Error Dialog", "Create Item Error", "Invalid input");
                return;
            }

            Optional<Float> buyNowPrice = buyNowPriceStr.isEmpty() ? Optional.empty()
                    : Optional.of(Float.parseFloat(buyNowPriceStr));

            float bidIncrement = Float.parseFloat(bidIncrementStr);

            Task<CreateItemResponse> task = new Task<>() {
                @Override
                protected CreateItemResponse call() {
                    return itemService.createItem(name,
                            buyNowPrice,
                            bidIncrement);
                }
            };

            task.setOnRunning((e) -> {
                createItemBtn.setDisable(true);
                backButton.setDisable(true);
                createItemBtn.setText("Creating...");
            });
            task.setOnSucceeded((e) -> {
                CreateItemResponse response = task.getValue();
                if (response == null || response.getStatusCode() == 0) {
                    JavaFxUtil.createAlert("Error Dialog", "Item Error", "Failed to create item");
                }

                if (response.getStatusCode() == 2) {
                    JavaFxUtil.createAlert("Error Dialog", "Item Error", "Item name already exists");
                }

                if (response.getStatusCode() == 1) {
                    JavaFxUtil.createAlert(AlertType.INFORMATION, "Information Dialog", "Item Information",
                            "Item created successfully");
                }

                createItemBtn.setDisable(false);
                backButton.setDisable(false);
                createItemBtn.setText("Create Item");
                nameField.clear();
                buyNowPriceField.clear();
                bidIncrementField.clear();

            });
            task.setOnFailed((e) -> {
                Throwable exception = task.getException();
                exception.printStackTrace();
                JavaFxUtil.createAlert("Error Dialog", "Item Error", "An error occurred during item creation");
                createItemBtn.setDisable(false);
                backButton.setDisable(false);
                createItemBtn.setText("Create Item");
                nameField.clear();
                buyNowPriceField.clear();
                bidIncrementField.clear();
            });

            new Thread(task).start();

        } catch (Exception e) {
            JavaFxUtil.createAlert("Error Dialog", "Create Item Error", "Invalid input");
        }
    }
}