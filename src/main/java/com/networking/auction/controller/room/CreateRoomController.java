package com.networking.auction.controller.room;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreateRoomController extends Controller implements Initializable {

    @FXML
    private TextField roomNameField;

    @FXML
    private Button createRoomBtn;

    @FXML
    private Button backBtn;

    private final RoomService roomService = RoomService.getInstance();

    public CreateRoomController(Stage stage, String fxmlPath, Controller prev) throws IOException {
        super(stage, fxmlPath);
        this.setPreviousController(prev);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        createRoomBtn.setOnAction(event -> createRoom());
        backBtn.setOnAction(event -> goBack(event));
    }

    private void createRoom() {
        String roomName = roomNameField.getText();

        if (roomName.isEmpty()) {
            JavaFxUtil.createAlert("Error Dialog", "Room Error", "Room name cannot be empty");
            return;
        }

        Task<CreateRoomResponse> task = new Task<>() {
            @Override
            protected CreateRoomResponse call() {
                return roomService.createRoom(roomName);
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