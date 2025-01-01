package com.networking.auction.controller.room;

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
import com.networking.auction.protocol.response.room.CreateRoomResponse;
import com.networking.auction.service.RoomService;
import com.networking.auction.util.JavaFxUtil;

import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreateRoomController extends Controller implements Initializable {

    @FXML
    private TextField roomNameField;

    @FXML
    private Button createRoomBtn;

    @FXML
    private Button backBtn;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ComboBox<String> timeSlotBox;

    private final RoomService roomService = RoomService.getInstance();

    public CreateRoomController(Stage stage, String fxmlPath, Controller prev) throws IOException {
        super(stage, fxmlPath);
        this.setPreviousController(prev);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        createRoomBtn.setOnAction(event -> createRoom());
        backBtn.setOnAction(event -> goBack(event));
        // Initialize ComboBox with time options
        timeSlotBox.getItems().addAll(generateTimeSlots());
    }

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

    private void createRoom() {
        String roomName = roomNameField.getText();
        LocalDate date = datePicker.getValue();
        String timeSlot = timeSlotBox.getValue();

        if (roomName.isEmpty()) {
            JavaFxUtil.createAlert("Error Dialog", "Room Error", "Room name cannot be empty");
            return;
        }

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

        Task<CreateRoomResponse> task = new Task<>() {
            @Override
            protected CreateRoomResponse call() {
                return roomService.createRoom(roomName, startDateTime, endDateTime);
            }
        };

        task.setOnRunning((e) -> {
            createRoomBtn.setDisable(true);
            createRoomBtn.setText("Creating...");
            backBtn.setDisable(true);
        });

        task.setOnSucceeded((e) -> {
            CreateRoomResponse response = task.getValue();
            if (response == null || response.getStatusCode() == 0) {
                JavaFxUtil.createAlert("Error Dialog", "Room Error", "Failed to create room");
            }

            if (response.getStatusCode() == 2) {
                JavaFxUtil.createAlert("Error Dialog", "Room Error", "Room name already exists");
            }

            if (response.getStatusCode() == 1) {
                JavaFxUtil.createAlert(AlertType.INFORMATION, "Information Dialog", "Room Information",
                        "Room created successfully");
            }

            roomNameField.clear();
            createRoomBtn.setDisable(false);
            createRoomBtn.setText("Create Room");
            backBtn.setDisable(false);

        });
        task.setOnFailed((e) -> {
            Throwable exception = task.getException();
            exception.printStackTrace();
            JavaFxUtil.createAlert("Error Dialog", "Room Error", "An error occurred during room creation");
            roomNameField.clear();
            createRoomBtn.setDisable(false);
            createRoomBtn.setText("Create Room");
            backBtn.setDisable(false);
        });

        new Thread(task).start();

    }

    private void goBack(Event event) {
        if (this.mainController == null) {
            this.getPreviousController().show();
        } else {
            this.mainController.show();
        }
    }

}