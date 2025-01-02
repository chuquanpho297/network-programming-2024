package com.networking.auction.controller.room;

import static javafx.scene.input.MouseEvent.MOUSE_CLICKED;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.networking.auction.controller.Controller;
import com.networking.auction.controller.item.CreateItemFormController;
import com.networking.auction.controller.item.TableViewItemController;
import com.networking.auction.models.Item;
import com.networking.auction.models.Item.ItemStateEnum;
import com.networking.auction.protocol.response.item.GetAllOwnedItemResponse;
import com.networking.auction.protocol.response.room.PlaceItemInRoomResponse;
import com.networking.auction.service.ItemService;
import com.networking.auction.service.RoomService;
import com.networking.auction.util.JavaFxUtil;

import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class AddItemToRoomController extends Controller implements Initializable {

    @FXML
    private TableView<Item> itemTableView;

    @FXML
    private Label itemNameLabel;

    @FXML
    private TableViewItemController tableViewItemController;

    @FXML
    private Button createItemBtn;

    @FXML
    private Button backBtn;

    private final ItemService itemService = ItemService.getInstance();
    private final RoomService roomService = RoomService.getInstance();
    private final int roomId;

    public AddItemToRoomController(Stage stage, String fxmlPath, ProgressIndicator progressIndicator, Controller prev,
            int roomId)
            throws IOException {
        super(stage, fxmlPath);
        this.progressIndicator = progressIndicator;
        this.roomId = roomId;
        getObservableOwnedItemList();
        addCheckBoxToTable();
        this.setPreviousController(prev);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.tableViewItemController = (TableViewItemController) itemTableView.getProperties().get("controller");
        this.createItemBtn.addEventHandler(MOUSE_CLICKED, e -> {
            try {
                CreateItemFormController createItemController = new CreateItemFormController(this.getStage(),
                        "item/create_item.fxml", this);
                createItemController.show();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        this.backBtn.setOnAction(event -> backButtonHandler());

    }

    public void getObservableOwnedItemList() {
        Task<GetAllOwnedItemResponse> loadDataTask = new Task<>() {
            @Override
            protected GetAllOwnedItemResponse call() {
                return itemService.getAllOwnedItems();
            }
        };

        loadDataTask.setOnRunning((e) -> {
            this.tableViewItemController.clearTableViewData();
            progressIndicator.setVisible(true);
        });
        loadDataTask.setOnSucceeded((e) -> {
            GetAllOwnedItemResponse response = loadDataTask.getValue();
            ArrayList<Item> itemList;
            if (response == null || response.getStatusCode() == 0) {
                JavaFxUtil.createAlert("Error Dialog", "Item Error", "Failed to get item list");
                itemList = new ArrayList<>();
            }

            itemList = (ArrayList<Item>) response.getLists();
            progressIndicator.setVisible(false);
            this.tableViewItemController.addTableViewData(FXCollections.observableArrayList(itemList));
        });
        loadDataTask.setOnFailed((e) -> progressIndicator.setVisible(false));

        new Thread(loadDataTask).start();
    }

    private void backButtonHandler() {
        // Implement the back button handler logic here
        this.getPreviousController().show();
    }

    private void addCheckBoxToTable() {
        Callback<TableColumn<Item, Void>, TableCell<Item, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Item, Void> call(final TableColumn<Item, Void> param) {
                final TableCell<Item, Void> cell = new TableCell<>() {

                    private final Button addButton = new Button("Add");

                    {
                        addButton.setOnAction((e) -> {
                            Item item = getTableView().getItems().get(getIndex());
                            placeItemInRoom(item);
                            // Add item to room
                        });

                    }

                    private final HBox pane = new HBox(addButton);

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || getTableView().getItems().get(getIndex()).getState() != ItemStateEnum.CREATED) {
                            setGraphic(null);
                        } else {
                            setGraphic(pane);
                        }
                    }
                };

                return cell;
            }
        };
        // Add the column to the table (assuming you have a column to add this to)
        tableViewItemController.getActionColumn().setCellFactory(cellFactory);
    }

    public void placeItemInRoom(Item item) {
        Task<PlaceItemInRoomResponse> task = new Task<>() {
            @Override
            protected PlaceItemInRoomResponse call() {
                return roomService.placeItemInRoom(roomId, item.getItemId());
            }
        };

        task.setOnRunning((e) -> {
            progressIndicator.setVisible(true);
        });

        task.setOnSucceeded((e) -> {
            PlaceItemInRoomResponse response = task.getValue();
            if (response == null || response.getStatusCode() == 0) {
                JavaFxUtil.createAlert("Error Dialog", "Item Error", "Failed to add item to room");
            } else {
                JavaFxUtil.createAlert(AlertType.INFORMATION, "Information Dialog",
                        "Item Information", "Item added to room successfully");
                tableViewItemController.removeTableViewData(item);
            }
            progressIndicator.setVisible(false);
        });

        task.setOnFailed((e) -> {
            progressIndicator.setVisible(false);
            JavaFxUtil.createAlert("Error Dialog", "Item Error", "An error occurred during adding item to room");
        });

        new Thread(task).start();
    }

}
