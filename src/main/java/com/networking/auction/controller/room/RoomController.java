package com.networking.auction.controller.room;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.networking.auction.controller.Controller;
import com.networking.auction.models.Room;
import com.networking.auction.protocol.response.room.GetAllRoomResponse;
import com.networking.auction.service.RoomService;
import com.networking.auction.util.JavaFxUtil;

import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableView;

public class RoomController extends Controller implements Initializable {
    @FXML
    private TableView<Room> roomTableView;

    @FXML
    private Label totalRoomLabel;

    private TableViewRoomController tableViewRoomController;

    private final RoomService roomService = RoomService.getInstance();

    public RoomController(ProgressIndicator progressIndicator) {
        this.progressIndicator = progressIndicator;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableViewRoomController = (TableViewRoomController) roomTableView.getProperties().get("controller");

        getObservableAllRoomList();
    }

    public void getObservableAllRoomList() {
        Task<GetAllRoomResponse> loadDataTask = new Task<>() {
            @Override
            protected GetAllRoomResponse call() {
                return roomService.getAllRooms();
            }
        };

        loadDataTask.setOnRunning((e) -> {
            totalRoomLabel.setText("");
            progressIndicator.setVisible(true);
        });
        loadDataTask.setOnSucceeded((e) -> {
            GetAllRoomResponse response = loadDataTask.getValue();
            ArrayList<Room> roomList;
            if (response == null || response.getStatusCode() == 0) {
                JavaFxUtil.createAlert("Error Dialog", "Room Error", "Failed to get room list");
                roomList = new ArrayList<>();
            }

            roomList = (ArrayList<Room>) response.getLists();
            totalRoomLabel.setText("Total Room: " + roomList.size());
            progressIndicator.setVisible(false);
            this.tableViewRoomController.addTableViewData(FXCollections.observableArrayList(roomList));
        });
        loadDataTask.setOnFailed((e) -> progressIndicator.setVisible(false));

        new Thread(loadDataTask).start();
    }
}
