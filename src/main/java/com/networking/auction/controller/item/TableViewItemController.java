package com.networking.auction.controller.item;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

import com.networking.auction.models.Item;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TableViewItemController implements Initializable {

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
    private TableColumn<Item, Integer> ownerIdColumn;

    @FXML
    private TableColumn<Item, Integer> roomIdColumn;

    @FXML
    private TableColumn<Item, String> ownerNameColumn;

    @FXML
    private TableColumn<Item, String> roomNameColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        itemIdColumn.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        currentPriceColumn.setCellValueFactory(new PropertyValueFactory<>("currentPrice"));
        stateColumn.setCellValueFactory(new PropertyValueFactory<>("state"));
        buyNowPriceColumn.setCellValueFactory(new PropertyValueFactory<>("buyNowPrice"));
        ownerIdColumn.setCellValueFactory(new PropertyValueFactory<>("ownerId"));
        roomIdColumn.setCellValueFactory(new PropertyValueFactory<>("roomId"));
        ownerNameColumn.setCellValueFactory(new PropertyValueFactory<>("ownerName"));
        roomNameColumn.setCellValueFactory(new PropertyValueFactory<>("roomName"));

        roomIdColumn.setCellValueFactory(
                cellData -> new SimpleObjectProperty<>(cellData.getValue().getRoomId().orElse(null)));
        roomNameColumn.setCellValueFactory(
                cellData -> new SimpleObjectProperty<>(cellData.getValue().getRoomName().orElse("")));

        itemTableView.getProperties().put("controller", this);
    }

    public void addTableViewData(ObservableList<Item> itemObservableList) {
        itemTableView.setItems(itemObservableList);
    }

    public void clearTableViewData() {
        itemTableView.getItems().clear();
    }
}