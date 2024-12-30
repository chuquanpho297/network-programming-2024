package com.networking.auction.controller.item;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import com.networking.auction.models.Item;
import com.networking.auction.models.Item.ItemStateEnum;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Getter;

@Getter
public class TableViewItemController implements Initializable {
    @FXML
    private TableColumn<Item, Float> buyNowPriceColumn;

    @FXML
    private TableColumn<Item, Float> currentPriceColumn;

    @FXML
    private TableColumn<Item, LocalDateTime> endTimeColumn;

    @FXML
    private TableColumn<Item, Integer> itemIdColumn;

    @FXML
    private TableColumn<Item, String> itemNameColumn;

    @FXML
    private TableView<Item> itemTableView;

    @FXML
    private TableColumn<Item, Integer> ownerIdColumn;

    @FXML
    private TableColumn<Item, Integer> roomIDColumn;

    @FXML
    private TableColumn<Item, LocalDateTime> startTimeColumn;

    @FXML
    private TableColumn<Item, ItemStateEnum> stateColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        itemTableView.getProperties().put("controller", this);
        itemIdColumn.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        buyNowPriceColumn.setCellValueFactory(new PropertyValueFactory<>("buyNowPrice"));
        currentPriceColumn.setCellValueFactory(new PropertyValueFactory<>("currentPrice"));
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        stateColumn.setCellValueFactory(new PropertyValueFactory<>("state"));
        ownerIdColumn.setCellValueFactory(new PropertyValueFactory<>("ownerId"));
        roomIDColumn.setCellValueFactory(new PropertyValueFactory<>("roomID"));
    }

    public void addTableViewData(ObservableList<Item> roomObservableList) {
        itemTableView.setItems(roomObservableList);
    }

    public void clearTableViewData() {
        itemTableView.getItems().clear();
    }

}
