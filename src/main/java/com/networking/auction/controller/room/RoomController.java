package com.networking.auction.controller.room;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.networking.auction.controller.Controller;
import com.networking.auction.models.Room;
import com.networking.auction.protocol.response.room.GetAllRoomResponse;
import com.networking.auction.service.RoomService;
import com.networking.auction.util.JavaFxUtil;

import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class RoomController extends Controller implements Initializable {
    @FXML
    private TableView<Room> roomTableView;

    @FXML
    private Label totalRoomLabel;

    private TableViewRoomController tableViewRoomController;

    private final RoomService roomService = RoomService.getInstance();

    public RoomController(Stage stage, String fxmlPath, ProgressIndicator progressIndicator) throws IOException {
        super(stage, fxmlPath, "room/styles.css");
        this.progressIndicator = progressIndicator;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableViewRoomController = (TableViewRoomController) roomTableView.getProperties().get("controller");
        addButtonToTable();
        getObservableAllRoomList();
    }

    private void addButtonToTable() {
        Callback<TableColumn<Room, Void>, TableCell<Room, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Room, Void> call(final TableColumn<Room, Void> param) {
                final TableCell<Room, Void> cell = new TableCell<>() {

                    private final Button joinButton = new Button("Join Room");

                    {
                        joinButton.setOnAction((event) -> {
                            Room room = getTableView().getItems().get(getIndex());
                            try {
                                AuctionRoomController auctionRoomController = new AuctionRoomController(
                                        RoomController.this.getStage(), "room/auction_room.fxml", room,
                                        progressIndicator, RoomController.this);
                                auctionRoomController.setMainController(RoomController.this.mainController);
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

        tableViewRoomController.getActionColumn().setCellFactory(cellFactory);
    }

    public void getObservableAllRoomList() {
        Task<GetAllRoomResponse> loadDataTask = new Task<>() {
            @Override
            protected GetAllRoomResponse call() {
                return roomService.getAllRooms();
            }
        };

        loadDataTask.setOnRunning((e) -> {
            totalRoomLabel.setText("");
            progressIndicator.setVisible(true);
        });
        loadDataTask.setOnSucceeded((e) -> {
            GetAllRoomResponse response = loadDataTask.getValue();
            ArrayList<Room> roomList;
            if (response == null || response.getStatusCode() == 0) {
                JavaFxUtil.createAlert("Error Dialog", "Room Error", "Failed to get room list");
                roomList = new ArrayList<>();
            }

            roomList = (ArrayList<Room>) response.getLists();
            totalRoomLabel.setText("Total Room: " + roomList.size());
            progressIndicator.setVisible(false);
            this.tableViewRoomController.addTableViewData(FXCollections.observableArrayList(roomList));
        });
        loadDataTask.setOnFailed((e) -> progressIndicator.setVisible(false));

        new Thread(loadDataTask).start();
    }
}
