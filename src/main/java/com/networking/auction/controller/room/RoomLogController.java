package com.networking.auction.controller.room;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.networking.auction.controller.Controller;
import com.networking.auction.controller.TableViewRoomLogController;
import com.networking.auction.models.Item;
import com.networking.auction.models.Item.ItemStateEnum;
import com.networking.auction.models.RoomLog.RoomLogStateEnum;
import com.networking.auction.models.Room;
import com.networking.auction.models.RoomLog;
import com.networking.auction.protocol.response.room.GetRoomLogResponse;
import com.networking.auction.service.RoomService;
import com.networking.auction.util.JavaFxUtil;

import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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

public class RoomLogController extends Controller implements Initializable {
    @FXML
    private TableView<RoomLog> roomLogTableView;

    @FXML
    private TableViewRoomLogController tableViewRoomLogController;

    @FXML
    private Button backBtn;

    @FXML
    private Label titleLabel;

    private Room room;

    @FXML
    private Label totalRequestLabel;

    @FXML
    private ProgressIndicator progressIndicator;

    private final RoomService roomService = RoomService.getInstance();

    public RoomLogController(Stage stage, String fxmlPath, Room room, Controller prev) throws IOException {
        super(stage, fxmlPath);
        this.room = room;
        titleLabel.setText(room.getRoomName());
        this.setPreviousController(prev);

        getRoomLogData();
        addButtonsToTable();
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize table columns if needed
        tableViewRoomLogController = (TableViewRoomLogController) roomLogTableView.getProperties()
        .get("controller");
        backBtn.setOnAction(this::handleBackButton);
        progressIndicator.setVisible(true);
        totalRequestLabel.setText("Total request: " + 1);
    }

    @FXML
    private void handleBackButton(Event e) {
        if (this.mainController != null) {
            this.mainController.show();
        } else
            this.getPreviousController().show();
    }

    public void getRoomLogData() {
        Task<GetRoomLogResponse> task = new Task<GetRoomLogResponse>() {
            @Override
            protected GetRoomLogResponse call() throws Exception {
                return roomService.getRoomLog(room.getRoomId());
            }

        };
        task.setOnRunning((e) -> {
            progressIndicator.setVisible(true);
            tableViewRoomLogController.clearTableViewData();
        });

        task.setOnSucceeded((e) -> {
            GetRoomLogResponse response = task.getValue();
            ArrayList<RoomLog> roomLogList;
            if (response == null || response.getStatusCode() == 0) {
                JavaFxUtil.createAlert("Error Dialog", "Item Error", "Failed to get item list");
                roomLogList = new ArrayList<>();
                progressIndicator.setVisible(false);
                return;
            }
            progressIndicator.setVisible(false);

            roomLogList = (ArrayList<RoomLog>) response.getLists();
            this.tableViewRoomLogController
                    .setRoomLogData(FXCollections.observableArrayList(roomLogList));
        });

        task.setOnFailed((e) -> {
            progressIndicator.setVisible(false);
            JavaFxUtil.createAlert("Error Dialog", "Item Error", "Failed to get item list");
        });
        new Thread(task).start();
    }

    public void addButtonsToTable() {
        // Implement the logic to add buttons to the table here
        Callback<TableColumn<RoomLog, Void>, TableCell<RoomLog, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<RoomLog, Void> call(final TableColumn<RoomLog, Void> param) {
                final TableCell<RoomLog, Void> cell = new TableCell<>() {

                    private final Button addButton = new Button("Accept");
                    private final Button rejectButton = new Button("Reject");

                    {
                        addButton.setOnAction((e) -> {
                            RoomLog item = getTableView().getItems().get(getIndex());
                            // Add item to room
                        });

                        rejectButton.setOnAction((e) -> {
                            RoomLog item = getTableView().getItems().get(getIndex());
                            // Reject item
                        });

                    }

                    private final HBox pane = new HBox(addButton, rejectButton);

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || getTableView().getItems().get(getIndex()).getState() != RoomLogStateEnum.PENDING) {
                            setGraphic(null);
                        } else {
                            setGraphic(pane);
                        }
                    }
                };

                return cell;
            }
        };
        tableViewRoomLogController.getActionColumn().setCellFactory(cellFactory);
        // Add the column to the table (assuming you have a column to add this to)
    }

}