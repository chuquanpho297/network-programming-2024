package com.networking.auction.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.networking.auction.models.RoomLog;
import com.networking.auction.models.RoomLog.RoomLogStateEnum;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TableViewRoomLogController implements Initializable {
    @FXML
    private TableView<RoomLog> roomLogTableView;

    @FXML
    private TableColumn<RoomLog, Integer> logIdColumn;

    @FXML
    private TableColumn<RoomLog, Integer> itemIdColumn;

    @FXML
    private TableColumn<RoomLog, Integer> roomIdColumn;

    @FXML
    private TableColumn<RoomLog, String> actionColumn;

    @FXML
    private TableColumn<RoomLog, String> timestampColumn;

    @FXML
    private TableColumn<RoomLog, RoomLogStateEnum> stateColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logIdColumn.setCellValueFactory(new PropertyValueFactory<>("logId"));
        roomIdColumn.setCellValueFactory(new PropertyValueFactory<>("roomId"));
        actionColumn.setCellValueFactory(new PropertyValueFactory<>("action"));
        timestampColumn.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
        stateColumn.setCellValueFactory(new PropertyValueFactory<>("state"));
        itemIdColumn.setCellValueFactory(new PropertyValueFactory<>("itemId"));
    }

    public void setRoomLogData(ObservableList<RoomLog> roomLogData) {
        roomLogTableView.setItems(roomLogData);
    }

    public void clearTableViewData() {
        roomLogTableView.getItems().clear();
    }
}