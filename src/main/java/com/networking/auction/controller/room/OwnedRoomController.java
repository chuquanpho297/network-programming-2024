package com.networking.auction.controller.room;

import static javafx.scene.input.MouseEvent.MOUSE_CLICKED;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.networking.auction.controller.Controller;
import com.networking.auction.models.Room;
import com.networking.auction.protocol.response.CreateRoomResponse;
import com.networking.auction.protocol.response.GetAllOwnedRoomResponse;
import com.networking.auction.service.RoomService;
import com.networking.auction.util.JavaFxUtil;

import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class OwnedRoomController extends Controller implements Initializable {
    @FXML
    private TableView<Room> roomTableView;

    @FXML
    private Label roomNameLabel;

    @FXML
    private TextField roomNameField;

    @FXML
    private TableViewRoomController tableViewRoomController;

    @FXML
    private Button createRoomBtn;

    @FXML
    private Label totalOwnedRoomLabel;

    private final RoomService roomService = RoomService.getInstance();

    public OwnedRoomController(ProgressIndicator progressIndicator) {
        this.progressIndicator = progressIndicator;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableViewRoomController = (TableViewRoomController) roomTableView.getProperties().get("controller");
        getObservableOwnedRoomList();
        createRoomBtn.addEventHandler(MOUSE_CLICKED, e -> createRoom());
    }

    public void getObservableOwnedRoomList() {
        Task<GetAllOwnedRoomResponse> loadDataTask = new Task<>() {
            @Override
            protected GetAllOwnedRoomResponse call() {
                return roomService.getAllOwnedRooms();
            }
        };

        loadDataTask.setOnRunning((e) -> {
            totalOwnedRoomLabel.setText("");
            this.tableViewRoomController.clearTableViewData();
            progressIndicator.setVisible(true);
        });
        loadDataTask.setOnSucceeded((e) -> {
            GetAllOwnedRoomResponse response = loadDataTask.getValue();
            ArrayList<Room> roomList;
            if (response == null || response.getStatusCode() == 0) {
                JavaFxUtil.createAlert("Error Dialog", "Room Error", "Failed to get room list");
                roomList = new ArrayList<>();
            }

            roomList = (ArrayList<Room>) response.getLists();
            System.out.println("Room list: " + roomList);
            totalOwnedRoomLabel.setText("Total Room: " + roomList.size());
            progressIndicator.setVisible(false);
            this.tableViewRoomController.addTableViewData(FXCollections.observableArrayList(roomList));
        });
        loadDataTask.setOnFailed((e) -> progressIndicator.setVisible(false));

        new Thread(loadDataTask).start();
    }

    public void createRoom() {
        if (roomNameField.getText().isEmpty()) {
            JavaFxUtil.createAlert("Error Dialog", "Room Error", "Room name cannot be empty");
            return;
        }
        Task<CreateRoomResponse> task = new Task<>() {
            @Override
            protected CreateRoomResponse call() {
                return roomService.createRoom(roomNameField.getText());
            }
        };

        task.setOnRunning((e) -> {
            createRoomBtn.setDisable(true);
            createRoomBtn.setText("Creating...");
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
                getObservableOwnedRoomList();
            }

            roomNameField.clear();
            createRoomBtn.setDisable(false);
            createRoomBtn.setText("Create Room");

        });
        task.setOnFailed((e) -> {
            Throwable exception = task.getException();
            exception.printStackTrace();
            JavaFxUtil.createAlert("Error Dialog", "Room Error", "An error occurred during room creation");
            roomNameField.clear();
            createRoomBtn.setDisable(false);
            createRoomBtn.setText("Create Room");
        });

        new Thread(task).start();
    }
}
