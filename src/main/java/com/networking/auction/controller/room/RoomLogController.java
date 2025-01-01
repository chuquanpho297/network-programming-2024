package com.networking.auction.controller.room;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.networking.auction.controller.Controller;
import com.networking.auction.controller.TableViewRoomLogController;
import com.networking.auction.models.Item;
import com.networking.auction.models.Room;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class RoomLogController extends Controller implements Initializable {
    @FXML
    private TableView<Item> roomLogTableView;

    @FXML
    private TableViewRoomLogController tableViewItemController;

    @FXML
    private Button backBtn;

    @FXML
    private Label titleLabel;

    private Room room;

    @FXML
    private Label totalRequestLabel;

    @FXML
    private ProgressIndicator progressIndicator;

    public RoomLogController(Room room) {
        this.room = room;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableViewItemController = (TableViewRoomLogController) roomLogTableView.getProperties().get("controller");
        // Initialize table columns if needed
        titleLabel.setText(room.getRoomName());
        backBtn.setOnAction(this::handleBackButton);
        progressIndicator.setVisible(true);
        totalRequestLabel.setText("Total request: " + 1);
    }

    @FXML
    private void handleBackButton(Event e) {
        try {
            switchToScreen((Stage) ((Node) e.getSource()).getScene().getWindow(), "room");
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}