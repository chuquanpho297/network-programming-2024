package com.networking.auction.controller.item;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.networking.auction.controller.Controller;
import com.networking.auction.models.Item;
import com.networking.auction.protocol.response.item.GetAllItemResponse;
import com.networking.auction.service.ItemService;
import com.networking.auction.util.JavaFxUtil;

import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableView;

public class AllItemController extends Controller implements Initializable {
    @FXML
    private TableView<Item> itemTableView;

    @FXML
    private Label totalItemLabel;

    private TableViewItemController tableViewItemController;

    private final ItemService itemService = ItemService.getInstance();

    public AllItemController(ProgressIndicator progressIndicator) {
        this.progressIndicator = progressIndicator;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableViewItemController = (TableViewItemController) itemTableView.getProperties().get("controller");

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
}
