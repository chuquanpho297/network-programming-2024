package com.networking.auction.controller.item;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import com.networking.auction.models.Item;
import com.networking.auction.util.JavaFxUtil;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import lombok.Getter;

@Getter
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

    @FXML
    private TableColumn<Item, Void> actionColumn;

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

        startTimeColumn.setCellValueFactory(
                cellData -> new SimpleObjectProperty<>(cellData.getValue().getStartTime().orElse(null)));
        endTimeColumn.setCellValueFactory(
                cellData -> new SimpleObjectProperty<>(cellData.getValue().getEndTime().orElse(null)));

        // Custom cell factory to format price columns
        currentPriceColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Item, Float> call(TableColumn<Item, Float> param) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(Float price, boolean empty) {
                        super.updateItem(price, empty);
                        if (empty || price == null) {
                            setText(null);
                        } else {
                            setText(JavaFxUtil.formatPrice(price));
                        }
                    }
                };
            }
        });

        buyNowPriceColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Item, Float> call(TableColumn<Item, Float> param) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(Float price, boolean empty) {
                        super.updateItem(price, empty);
                        if (empty || price == null) {
                            setText(null);
                        } else {
                            setText(JavaFxUtil.formatPrice(price));
                        }
                    }
                };
            }
        });

        itemTableView.getProperties().put("controller", this);
    }

    public void addTableViewData(ObservableList<Item> itemObservableList) {
        itemTableView.setItems(itemObservableList);
    }

    public void clearTableViewData() {
        itemTableView.getItems().clear();
    }

    public void removeTableViewData(Item item) {
        itemTableView.getItems().remove(item);
    }
}