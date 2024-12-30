package com.networking.auction.controller.item;

import java.net.URL;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.networking.auction.controller.Controller;
import com.networking.auction.models.Item;
import com.networking.auction.models.Item.ItemStateEnum;
import com.networking.auction.protocol.response.item.GetAllItemResponse;
import com.networking.auction.protocol.response.item.SearchItemResponse;
import com.networking.auction.service.ItemService;
import com.networking.auction.util.JavaFxUtil;

import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class AllItemController extends Controller implements Initializable {
    @FXML
    private TableView<Item> itemTableView;

    @FXML
    private Label totalItemLabel;

    @FXML
    private TextField itemNameField;

    @FXML
    private Button searchItemBtn;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    private TableViewItemController tableViewItemController;

    private final ItemService itemService = ItemService.getInstance();

    public AllItemController(ProgressIndicator progressIndicator) {
        this.progressIndicator = progressIndicator;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableViewItemController = (TableViewItemController) itemTableView.getProperties().get("controller");

        startDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && (endDatePicker.getValue() == null || newValue.isAfter(endDatePicker.getValue()))) {
                endDatePicker.setValue(newValue);
            }
        });

        endDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null
                    && (startDatePicker.getValue() == null || newValue.isBefore(startDatePicker.getValue()))) {
                startDatePicker.setValue(newValue);
            }
        });

        searchItemBtn.setOnAction(event -> searchItem());

        getObservableAllItemList();
    }

    public void getObservableAllItemList() {
        Task<GetAllItemResponse> loadDataTask = new Task<>() {
            @Override
            protected GetAllItemResponse call() {
                return itemService.getAllItem();
            }
        };

        loadDataTask.setOnRunning((e) -> {
            totalItemLabel.setText("");
            progressIndicator.setVisible(true);
        });
        loadDataTask.setOnSucceeded((e) -> {
            GetAllItemResponse response = loadDataTask.getValue();
            ArrayList<Item> itemList;
            if (response == null || response.getStatusCode() == 0) {
                JavaFxUtil.createAlert("Error Dialog", "Item Error", "Failed to get item list");
                itemList = new ArrayList<>();
            }

            itemList = (ArrayList<Item>) response.getLists();
            totalItemLabel.setText("Total item: " + itemList.size());
            progressIndicator.setVisible(false);
            this.tableViewItemController.addTableViewData(FXCollections.observableArrayList(itemList));
        });
        loadDataTask.setOnFailed((e) -> progressIndicator.setVisible(false));

        new Thread(loadDataTask).start();
    }

    void searchItem() {

        Task<SearchItemResponse> task = new Task<>() {
            @Override
            protected SearchItemResponse call() {
                return itemService.searchItem(
                        Optional.of(itemNameField.getText()),
                        Optional.of(startDatePicker.getValue().atStartOfDay()),
                        Optional.of(endDatePicker.getValue().atTime(LocalTime.MAX)),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.of(List.of(ItemStateEnum.ACTIVE, ItemStateEnum.WAITING)));
            }
        };

        task.setOnRunning((e) -> {
            searchItemBtn.setDisable(true);
            searchItemBtn.setText("Searching...");
        });
        task.setOnSucceeded((e) -> {
            SearchItemResponse response = task.getValue();
            if (response == null || response.getStatusCode() == 0) {
                JavaFxUtil.createAlert("Error Dialog", "Item Error", "Failed to search item");
            }

            if (response.getStatusCode() == 1) {
                ArrayList<Item> itemList = (ArrayList<Item>) response.getLists();
                totalItemLabel.setText("Total Item: " + itemList.size());
                this.tableViewItemController.clearTableViewData();
                this.tableViewItemController.addTableViewData(FXCollections.observableArrayList(itemList));
            }

            searchItemBtn.setDisable(false);
            searchItemBtn.setText("Search Item");

        });
        task.setOnFailed((e) -> {
            Throwable exception = task.getException();
            exception.printStackTrace();
            JavaFxUtil.createAlert("Error Dialog", "Item Error", "An error occurred during item search");
            searchItemBtn.setDisable(false);
            searchItemBtn.setText("Search Item");
        });

        new Thread(task).start();
    }
}
