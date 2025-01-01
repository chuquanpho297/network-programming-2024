package com.networking.auction.controller.item;

import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.networking.auction.controller.Controller;
import com.networking.auction.controller.room.AuctionRoomController;
import com.networking.auction.models.Item;
import com.networking.auction.models.Room;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

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

    @FXML
    private TableColumn<Item, Void> actionColumn;

    private TableViewItemController tableViewItemController;

    private final ItemService itemService = ItemService.getInstance();

    @SuppressWarnings("exports")
    public AllItemController(ProgressIndicator progressIndicator, Stage stage, String fxmlPath) throws IOException {
        super(stage, fxmlPath);
        this.progressIndicator = progressIndicator;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableViewItemController = (TableViewItemController) itemTableView.getProperties().get("controller");

        searchItemBtn.setOnAction(event -> searchItem());

        addButtonToTable();

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

    private void addButtonToTable() {
        Callback<TableColumn<Item, Void>, TableCell<Item, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Item, Void> call(final TableColumn<Item, Void> param) {
                final TableCell<Item, Void> cell = new TableCell<>() {

                    private final Button joinButton = new Button("Join Room");

                    {
                        joinButton.setOnAction((event) -> {
                            Item item = getTableView().getItems().get(getIndex());
                            try {
                                AuctionRoomController auctionRoomController = new AuctionRoomController(
                                        AllItemController.this.getStage(), "room/auction_room.fxml",
                                        Room.builder().roomId(item.getRoomId().get())
                                                .roomName(item.getRoomName().get())
                                                .build(),
                                        progressIndicator, AllItemController.this);
                                auctionRoomController.show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                    }

                    private final HBox pane = new HBox(joinButton);

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(pane);
                        }
                    }
                };
                return cell;
            }
        };

        tableViewItemController.getActionColumn().setCellFactory(cellFactory);
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
