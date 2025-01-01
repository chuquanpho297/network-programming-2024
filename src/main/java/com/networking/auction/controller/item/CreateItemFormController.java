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
import com.networking.auction.protocol.response.item.CreateItemResponse;
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

public class CreateItemFormController extends Controller implements Initializable {

    @FXML
    private TextField nameField;

    @FXML
    private TextField buyNowPriceField;

    @FXML
    private Button createItemBtn;

    @FXML
    private Button backButton;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ComboBox<String> timeSlotBox;

    private final ItemService itemService = ItemService.getInstance();

    public CreateItemFormController(Stage stage, String fxmlPath, Controller prev) throws IOException {
        super(stage, fxmlPath);
        this.setPreviousController(prev);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize ComboBox with time options
        timeSlotBox.getItems().addAll(generateTimeSlots());

        backButton.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED,
                event -> {
                    if (this.mainController == null) {
                        this.getPreviousController().show();
                    } else {
                        this.mainController.show();
                    }
                });
        createItemBtn.setOnAction(event -> handleSubmitButtonAction());
    }

    // TODO: change time slots
    private List<String> generateTimeSlots() {
        List<String> timeSlots = new ArrayList<>();
        LocalTime now = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        LocalTime slot1Start = now.plusMinutes(5);
        LocalTime slot1End = slot1Start.plusMinutes(5);
        LocalTime slot2Start = slot1End.plusMinutes(5);
        LocalTime slot2End = slot2Start.plusMinutes(5);
        LocalTime slot3Start = slot2End.plusMinutes(5);
        LocalTime slot3End = slot3Start.plusMinutes(5);

        timeSlots.add(slot1Start.format(formatter) + " - " + slot1End.format(formatter));
        timeSlots.add(slot2Start.format(formatter) + " - " + slot2End.format(formatter));
        timeSlots.add(slot3Start.format(formatter) + " - " + slot3End.format(formatter));

        return timeSlots;
    }

    @FXML
    private void handleSubmitButtonAction() {
        try {
            String name = nameField.getText();
            LocalDate date = datePicker.getValue();
            String timeSlot = timeSlotBox.getValue();
            float buyNowPrice = Float.parseFloat(buyNowPriceField.getText());

            if (name.isEmpty() || date == null || timeSlot == null) {
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

            Task<CreateItemResponse> task = new Task<>() {
                @Override
                protected CreateItemResponse call() {
                    return itemService.createItem(name,
                            buyNowPrice,
                            startDateTime,
                            endDateTime);
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
            });

            new Thread(task).start();

        } catch (Exception e) {
            JavaFxUtil.createAlert("Error Dialog", "Create Item Error", "Invalid input");
        }
    }
}