package com.networking.meetingclient.controller.room;

import java.net.URL;
import java.util.ResourceBundle;

import com.networking.meetingclient.models.Room;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Getter;

@Getter
public class TableViewRoomController implements Initializable {
    @FXML
    private TableView<Room> roomTableView;
    @FXML
    private TableColumn<Room, String> roomNameColumn;

    @FXML
    private TableColumn<Room, Integer> roomIdColumn;

    @FXML
    private TableColumn<Room, Integer> ownerIdColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        roomNameColumn.setCellValueFactory(new PropertyValueFactory<>("roomName"));
        roomIdColumn.setCellValueFactory(new PropertyValueFactory<>("roomId"));
        ownerIdColumn.setCellValueFactory(new PropertyValueFactory<>("ownerId"));
        roomTableView.getProperties().put("controller", this);
    }

    public void addTableViewData(ObservableList<Room> roomObservableList) {
        roomTableView.setItems(roomObservableList);
    }

    public void clearTableViewData() {
        roomTableView.getItems().clear();
    }

}
