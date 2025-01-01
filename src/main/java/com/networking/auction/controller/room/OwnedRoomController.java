package com.networking.auction.controller.room;

import static javafx.scene.input.MouseEvent.MOUSE_CLICKED;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.networking.auction.controller.Controller;
import com.networking.auction.models.Room;
import com.networking.auction.protocol.response.room.GetAllOwnedRoomResponse;
import com.networking.auction.service.RoomService;
import com.networking.auction.util.JavaFxUtil;

import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class OwnedRoomController extends Controller implements Initializable {
    @FXML
    private TableView<Room> roomTableView;

    @FXML
    private TableViewRoomController tableViewRoomController;

    @FXML
    private Button createRoomBtn;

    @FXML
    private Label totalOwnedRoomLabel;

    private final RoomService roomService = RoomService.getInstance();

    public OwnedRoomController(ProgressIndicator progressIndicator) {
        this.progressIndicator = progressIndicator;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableViewRoomController = (TableViewRoomController) roomTableView.getProperties().get("controller");
        getObservableOwnedRoomList();
        createRoomBtn.addEventHandler(MOUSE_CLICKED,
                e -> {
                    try {
                        switchToScreenNotStyle((Stage) ((Node) e.getSource()).getScene().getWindow(),
                                "room/create_room.fxml");
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                });
        addButtonToTable();
    }

    private void addButtonToTable() {
        Callback<TableColumn<Room, Void>, TableCell<Room, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Room, Void> call(final TableColumn<Room, Void> param) {
                final TableCell<Room, Void> cell = new TableCell<>() {

                    private final Button joinButton = new Button("Room Request");

                    {
                        joinButton.setOnAction((event) -> {
                            Room room = getTableView().getItems().get(getIndex());
                            try {
                                switchToScreenNotStyle((Stage) joinButton.getScene().getWindow(),
                                        "room/room_log.fxml",
                                        new RoomLogController(room));
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

    public void getObservableOwnedRoomList() {
        Task<GetAllOwnedRoomResponse> loadDataTask = new Task<>() {
            @Override
            protected GetAllOwnedRoomResponse call() {
                return roomService.getAllOwnedRooms();
            }
        };

        loadDataTask.setOnRunning((e) -> {
            totalOwnedRoomLabel.setText("");
            this.tableViewRoomController.clearTableViewData();
            progressIndicator.setVisible(true);
        });
        loadDataTask.setOnSucceeded((e) -> {
            GetAllOwnedRoomResponse response = loadDataTask.getValue();
            ArrayList<Room> roomList;
            if (response == null || response.getStatusCode() == 0) {
                JavaFxUtil.createAlert("Error Dialog", "Room Error", "Failed to get room list");
                roomList = new ArrayList<>();
            }

            roomList = (ArrayList<Room>) response.getLists();
            totalOwnedRoomLabel.setText("Total Room: " + roomList.size());
            progressIndicator.setVisible(false);
            this.tableViewRoomController.addTableViewData(FXCollections.observableArrayList(roomList));
        });
        loadDataTask.setOnFailed((e) -> progressIndicator.setVisible(false));

        new Thread(loadDataTask).start();
    }

}
