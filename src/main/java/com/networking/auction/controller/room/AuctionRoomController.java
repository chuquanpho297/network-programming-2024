package com.networking.auction.controller.room;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.networking.auction.controller.Controller;
import com.networking.auction.controller.item.TableViewItemController;
import com.networking.auction.models.Item;
import com.networking.auction.models.Room;
import com.networking.auction.protocol.response.item.GetAllItemInRoomResponse;
import com.networking.auction.service.RoomService;
import com.networking.auction.util.JavaFxUtil;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class AuctionRoomController extends Controller implements Initializable {
    @FXML
    private TableView<Item> itemTableView;

    @FXML
    private TableViewItemController tableViewItemController;

    @FXML
    private Button backBtn;

    @FXML
    private Button addItemBtn;

    @FXML
    private Label titleLabel;

    @FXML
    private Label countdownLabel;

    private Timeline timeline;
    private LocalTime endTime;
    private Room room;
    private final RoomService roomService = RoomService.getInstance();

    public AuctionRoomController(Stage stage, String fxmlPath, Room room, ProgressIndicator progressIndicator,
            Controller prev) throws IOException {
        super(stage, fxmlPath);
        this.room = room;
        this.progressIndicator = progressIndicator;
        this.setPreviousController(prev);
        titleLabel.setText(room.getRoomName());
        getObservableItemListInRoom();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableViewItemController = (TableViewItemController) itemTableView.getProperties().get("controller");
        // Initialize table columns if needed
        startCountdown(Duration.ofMinutes(5)); // Example: 5-minute countdown
        backBtn.setOnAction(this::handleBackButton);
        addItemBtn.setOnAction(this::handleAddItemButton);
        tableViewItemController.getBuyNowPriceColumn().setVisible(false);
        tableViewItemController.getCurrentPriceColumn().setVisible(false);
        tableViewItemController.getStateColumn().setVisible(false);
    }

    @FXML
    private void handleBackButton(Event e) {
        this.mainController.show();
    }

    @FXML
    private void handleAddItemButton(Event e) {
        try {
            AddItemToRoomController addItemToRoomController = new AddItemToRoomController(
                    this.getStage(), "room/add_item_to_room.fxml",
                    progressIndicator, this, room.getRoomId());
            addItemToRoomController.show();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private void startCountdown(Duration duration) {
        endTime = LocalTime.now().plusSeconds(duration.getSeconds());
        timeline = new Timeline(
                new KeyFrame(javafx.util.Duration.seconds(1), event -> {
                    Duration remaining = Duration.between(LocalTime.now(), endTime);
                    if (!remaining.isNegative() && !remaining.isZero()) {
                        countdownLabel.setText(formatDuration(remaining));
                    } else {
                        countdownLabel.setText("00:00:00");
                        timeline.stop();
                    }
                }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private String formatDuration(Duration duration) {
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;
        long seconds = duration.getSeconds() % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public void getObservableItemListInRoom() {
        Task<GetAllItemInRoomResponse> loadDataTask = new Task<>() {
            @Override
            protected GetAllItemInRoomResponse call() {
                return roomService.getAllItemInRoomResponse(room.getRoomId());
            }
        };
        loadDataTask.setOnRunning((e) -> {
            GetAllItemInRoomResponse response = loadDataTask.getValue();
            ArrayList<Item> itemList;
            if (response.getStatusCode() == 0) {
                JavaFxUtil.createAlert("Error Dialog", "Item Error", "Failed to get item list");
                itemList = new ArrayList<>();
            }
            itemList = (ArrayList<Item>) response.getLists();
            progressIndicator.setVisible(true);
            this.tableViewItemController.addTableViewData(FXCollections.observableArrayList(itemList));

        });
        loadDataTask.setOnSucceeded((e) -> {
            progressIndicator.setVisible(false);
        });
        loadDataTask.setOnFailed((e) -> progressIndicator.setVisible(false));
        new Thread(loadDataTask).start();
    }

}