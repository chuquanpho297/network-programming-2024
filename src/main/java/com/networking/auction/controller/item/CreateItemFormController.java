package com.networking.auction.controller.item;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import com.networking.auction.StateManager;
import com.networking.auction.controller.Controller;
import com.networking.auction.protocol.response.item.CreateItemResponse;
import com.networking.auction.util.JavaFxUtil;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class CreateItemFormController extends Controller implements Initializable {

    @FXML
    private TextField nameField;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private TextField buyNowPriceField;

    @FXML
    private TextField bidIncrementField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        startDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && endDatePicker.getValue() != null && !newValue.isBefore(endDatePicker.getValue())) {
                endDatePicker.setValue(newValue.plusDays(1));
            }
        });

        endDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && startDatePicker.getValue() != null
                    && !newValue.isAfter(startDatePicker.getValue())) {
                startDatePicker.setValue(newValue.minusDays(1));
            }
        });
    }

    @FXML
    private void handleSubmitButtonAction() {
        try {
            String name = nameField.getText();
            LocalDateTime startTime = startDatePicker.getValue().atStartOfDay();
            LocalDateTime endTime = endDatePicker.getValue().atStartOfDay();
            float buyNowPrice = Float.parseFloat(buyNowPriceField.getText());
            int ownerId = StateManager.getInstance().getUserId().orElse(-1);
            float bidIncrement = Float.parseFloat(bidIncrementField.getText());

            if (name.isEmpty() || startTime == null || endTime == null || ownerId == -1
                    || bidIncrement <= 0 || buyNowPrice < 0) {
                JavaFxUtil.createAlert("Error Dialog", "Create Item Error", "Invalid input");
                return;
            }
        } catch (Exception e) {
            JavaFxUtil.createAlert("Error Dialog", "Create Item Error", "Invalid input");
            return;
        }
        // Handle form submission logic here
    }

    // void createItem() {
    //     if (itemNameField.getText().isEmpty()) {
    //         JavaFxUtil.createAlert("Error Dialog", "Item Error", "Item name cannot be empty");
    //         return;
    //     }
    //     Task<CreateItemResponse> task = new Task<>() {
    //         @Override
    //         protected CreateItemResponse call() {
    //             return itemService.createItem(itemNameField.getText(), startDatePicker.getValue().atStartOfDay(),
    //                     endDatePicker.getValue().atStartOfDay(), 0);
    //         }
    //     };

    //     task.setOnRunning((e) -> {
    //         createItemBtn.setDisable(true);
    //         createItemBtn.setText("Creating...");
    //     });
    //     task.setOnSucceeded((e) -> {
    //         CreateItemResponse response = task.getValue();
    //         if (response == null || response.getStatusCode() == 0) {
    //             JavaFxUtil.createAlert("Error Dialog", "Item Error", "Failed to create item");
    //         }

    //         if (response.getStatusCode() == 2) {
    //             JavaFxUtil.createAlert("Error Dialog", "Item Error", "Item name already exists");
    //         }

    //         if (response.getStatusCode() == 1) {
    //             JavaFxUtil.createAlert(AlertType.INFORMATION, "Information Dialog", "Item Information",
    //                     "Item created successfully");
    //             getObservableOwnedItemList();
    //         }

    //         itemNameField.clear();
    //         createItemBtn.setDisable(false);
    //         createItemBtn.setText("Create Item");

    //     });
    //     task.setOnFailed((e) -> {
    //         Throwable exception = task.getException();
    //         exception.printStackTrace();
    //         JavaFxUtil.createAlert("Error Dialog", "Item Error", "An error occurred during item creation");
    //         itemNameField.clear();
    //         createItemBtn.setDisable(false);
    //         createItemBtn.setText("Create Item");
    //     });

    //     new Thread(task).start();
    // }

    @FXML
    private void handleBackButtonAction() {
        // Handle back button action to navigate to owned item list
    }
}