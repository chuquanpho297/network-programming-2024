package com.networking.auction.controller.room;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import com.networking.auction.models.Room;

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

    @FXML
    private TableColumn<Room, Integer> totalItemsColumn;

    @FXML
    private TableColumn<Room, Integer> totalParticipantsColumn;

    @FXML
    private TableColumn<Room, String> ownerNameColumn;

    @FXML
    private TableColumn<Room, LocalDateTime> startTimeColumn;

    @FXML
    private TableColumn<Room, LocalDateTime> endTimeColumn;

    @FXML
    private TableColumn<Room, Void> actionColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        roomNameColumn.setCellValueFactory(new PropertyValueFactory<>("roomName"));
        ownerNameColumn.setCellValueFactory(new PropertyValueFactory<>("ownerName"));
        totalItemsColumn.setCellValueFactory(new PropertyValueFactory<>("totalItems"));
        totalParticipantsColumn.setCellValueFactory(new PropertyValueFactory<>("totalParticipants"));
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        roomTableView.getProperties().put("controller", this);
    }

    public void addTableViewData(ObservableList<Room> roomObservableList) {
        roomTableView.setItems(roomObservableList);
    }

    public void clearTableViewData() {
        roomTableView.getItems().clear();
    }

}
