package com.networking.auction.controller.item;

import java.time.LocalDateTime;

import com.networking.auction.models.Item;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TableViewItemController {

    @FXML
    private TableView<Item> itemTableView;

    @FXML
    private TableColumn<Item, Integer> itemIdColumn;

    @FXML
    private TableColumn<Item, String> nameColumn;

    @FXML
    private TableColumn<Item, LocalDateTime> startTimeColumn;

    @FXML
    private TableColumn<Item, LocalDateTime> endTimeColumn;

    @FXML
    private TableColumn<Item, Float> currentPriceColumn;

    @FXML
    private TableColumn<Item, Item.ItemStateEnum> stateColumn;

    @FXML
    private TableColumn<Item, Float> buyNowPriceColumn;

    @FXML
    private TableColumn<Item, Float> bidIncrementColumn;

    @FXML
    private TableColumn<Item, Integer> ownerIdColumn;

    @FXML
    private TableColumn<Item, Integer> roomIdColumn;

    @FXML
    private TableColumn<Item, String> ownerNameColumn;

    @FXML
    private TableColumn<Item, String> roomNameColumn;

    private ObservableList<Item> itemList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        itemIdColumn.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        currentPriceColumn.setCellValueFactory(new PropertyValueFactory<>("currentPrice"));
        stateColumn.setCellValueFactory(new PropertyValueFactory<>("state"));
        buyNowPriceColumn.setCellValueFactory(new PropertyValueFactory<>("buyNowPrice"));
        bidIncrementColumn.setCellValueFactory(new PropertyValueFactory<>("bidIncrement"));
        ownerIdColumn.setCellValueFactory(new PropertyValueFactory<>("ownerId"));
        roomIdColumn.setCellValueFactory(new PropertyValueFactory<>("roomId"));
        ownerNameColumn.setCellValueFactory(new PropertyValueFactory<>("ownerName"));
        roomNameColumn.setCellValueFactory(new PropertyValueFactory<>("roomName"));

        itemTableView.setItems(itemList);
    }

    public void addTableViewData(ObservableList<Item> roomObservableList) {
        itemTableView.setItems(roomObservableList);
    }

    public void clearTableViewData() {
        itemTableView.getItems().clear();
    }
}