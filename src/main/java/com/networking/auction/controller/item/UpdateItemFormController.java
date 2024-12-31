package com.networking.auction.controller.item;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
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

    @FXML
    private DatePicker datePicker;

    @FXML
    private ComboBox<String> timeSlotBox;

    private final ItemService itemService = ItemService.getInstance();
    private Item item;

    UpdateItemFormController(Item item) {
        this.item = item;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize ComboBox with time options
        timeSlotBox.getItems().addAll(generateTimeSlots());
        nameField.setText(item.getName());
        buyNowPriceField.setText(String.valueOf(item.getBuyNowPrice()));
        LocalDateTime startDateTime = item.getStartTime();
        datePicker.setValue(startDateTime.toLocalDate());
        timeSlotBox.setValue(startDateTime.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")) + " - " +
                item.getEndTime().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")));

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

    private List<String> generateTimeSlots() {
        List<String> timeSlots = new ArrayList<>();
        LocalTime now = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        LocalTime slot1Start = now.plusMinutes(5);
        LocalTime slot1End = slot1Start.plusMinutes(5);
        LocalTime slot2Start = slot1End.plusMinutes(5);
        LocalTime slot2End = slot2Start.plusMinutes(5);

        timeSlots.add(slot1Start.format(formatter) + " - " + slot1End.format(formatter));
        timeSlots.add(slot2Start.format(formatter) + " - " + slot2End.format(formatter));

        return timeSlots;
    }

    @FXML
    private void handleSubmitButtonAction() {
        try {
            LocalDate date = datePicker.getValue();
            String timeSlot = timeSlotBox.getValue();
            float buyNowPrice = Float.parseFloat(buyNowPriceField.getText());

            if (date == null || timeSlot == null) {
                JavaFxUtil.createAlert("Error Dialog", "Room Error", "Invalid input");
                return;
            }

            // Parse the time slot to get start and end times
            String[] times = timeSlot.split(" - ");
            LocalTime startTime = LocalTime.parse(times[0]);
            LocalTime endTime = LocalTime.parse(times[1]);

            // Combine date and time to get LocalDateTime
            LocalDateTime startDateTime = LocalDateTime.of(date, startTime);
            LocalDateTime endDateTime = LocalDateTime.of(date, endTime);

            Task<UpdateItemResponse> task = new Task<>() {
                @Override
                protected UpdateItemResponse call() {
                    return itemService.updateItem(item.getItemId(),
                            buyNowPrice,
                            startDateTime,
                            endDateTime);
                }
            };

            task.setOnRunning((e) -> {
                updateItemBtn.setDisable(true);
                backButton.setDisable(true);
                updateItemBtn.setText("Creating...");
            });
            task.setOnSucceeded((e) -> {
                UpdateItemResponse response = task.getValue();
                if (response == null || response.getStatusCode() == 0) {
                    JavaFxUtil.createAlert("Error Dialog", "Item Error", "Failed to create item");
                }

                if (response.getStatusCode() == 1) {
                    JavaFxUtil.createAlert(AlertType.INFORMATION, "Information Dialog", "Item Information",
                            "Item updated successfully");
                }

                updateItemBtn.setDisable(false);
                backButton.setDisable(false);
                updateItemBtn.setText("Update Item");
                buyNowPriceField.clear();
                datePicker.setValue(null);
                timeSlotBox.setValue(null);

            });
            task.setOnFailed((e) -> {
                Throwable exception = task.getException();
                exception.printStackTrace();
                JavaFxUtil.createAlert("Error Dialog", "Item Error", "An error occurred during item creation");
                updateItemBtn.setDisable(false);
                backButton.setDisable(false);
                updateItemBtn.setText("Update Item");
                buyNowPriceField.clear();
                datePicker.setValue(null);
                timeSlotBox.setValue(null);
            });

            new Thread(task).start();

        } catch (Exception e) {
            JavaFxUtil.createAlert("Error Dialog", "Update Item Error", "Invalid input");
        }
    }
}