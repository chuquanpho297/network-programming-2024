
package com.networking.auction.controller.item;

import static javafx.scene.input.MouseEvent.MOUSE_CLICKED;

import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import com.networking.auction.StateManager;
import com.networking.auction.controller.Controller;
import com.networking.auction.models.Item;
import com.networking.auction.models.Item.ItemStateEnum;
import com.networking.auction.protocol.response.item.DeleteItemResponse;
import com.networking.auction.protocol.response.item.GetAllOwnedItemResponse;
import com.networking.auction.protocol.response.item.SearchItemResponse;
import com.networking.auction.service.ItemService;
import com.networking.auction.util.JavaFxUtil;

import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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

public class OwnedItemController extends Controller implements Initializable {
    @FXML
    private TableView<Item> itemTableView;

    @FXML
    private Label itemNameLabel;

    @FXML
    private TableViewItemController tableViewItemController;

    @FXML
    private Button createItemBtn;

    @FXML
    private Label totalOwnedItemLabel;

    @FXML
    private Button searchItemBtn;

    @FXML
    private TextField itemNameField;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    private final ItemService itemService = ItemService.getInstance();

    public OwnedItemController(ProgressIndicator progressIndicator, Stage stage, String fxmlPath) throws IOException {
        super(stage, fxmlPath);
        this.progressIndicator = progressIndicator;
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableViewItemController = (TableViewItemController) itemTableView.getProperties().get("controller");

        createItemBtn.addEventHandler(MOUSE_CLICKED, e -> {
            try {
                CreateItemFormController createItemController = new CreateItemFormController(this.getStage(),
                        "item/create_item.fxml", this);
                createItemController.setMainController(this.mainController);
                createItemController.show();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        searchItemBtn.addEventHandler(MOUSE_CLICKED, e -> searchItem());

        addButtonToTable();

        getObservableOwnedItemList();
    }

    private void addButtonToTable() {
        Callback<TableColumn<Item, Void>, TableCell<Item, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Item, Void> call(final TableColumn<Item, Void> param) {
                final TableCell<Item, Void> cell = new TableCell<>() {

                    private final Button updateButton = new Button("Update");
                    private final Button deleteButton = new Button("Delete");

                    {
                        updateButton.setOnAction((event) -> {
                            try {
                                Item item = getTableView().getItems().get(getIndex());
                                UpdateItemFormController updateItemController = new UpdateItemFormController(item,
                                        OwnedItemController.this.getStage(), "item/update_item.fxml",
                                        OwnedItemController.this);
                                updateItemController.setMainController(OwnedItemController.this.mainController);

                                updateItemController.show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                        deleteButton.setOnAction((event) -> {
                            Item item = getTableView().getItems().get(getIndex());
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Confirmation Dialog");
                            alert.setHeaderText("Delete Item");
                            alert.setContentText("Are you sure you want to delete this item?");

                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.isPresent() && result.get() == ButtonType.OK) {
                                Task<DeleteItemResponse> task = new Task<>() {
                                    @Override
                                    protected DeleteItemResponse call() {
                                        return itemService.deleteItem(item.getItemId());
                                    }
                                };

                                task.setOnRunning((e) -> {
                                    progressIndicator.setVisible(true);
                                });
                                task.setOnSucceeded((e) -> {
                                    DeleteItemResponse response = task.getValue();
                                    if (response == null || response.getStatusCode() == 0) {
                                        JavaFxUtil.createAlert("Error Dialog", "Item Error", "Failed to delete item");
                                    }

                                    if (response.getStatusCode() == 1) {
                                        JavaFxUtil.createAlert(AlertType.INFORMATION, "Information Dialog",
                                                "Item Information",
                                                "Item deleted successfully");
                                        getObservableOwnedItemList();
                                    }

                                    progressIndicator.setVisible(false);
                                });
                                task.setOnFailed((e) -> {
                                    Throwable exception = task.getException();
                                    exception.printStackTrace();
                                    JavaFxUtil.createAlert("Error Dialog", "Item Error",
                                            "An error occurred during item deletion");
                                    progressIndicator.setVisible(false);
                                });

                                new Thread(task).start();
                            }
                        });
                    }

                    private final HBox pane = new HBox(updateButton, deleteButton);

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

        tableViewItemController.getActionColumn().setCellFactory(cellFactory);
    }

    public void getObservableOwnedItemList() {
        Task<GetAllOwnedItemResponse> loadDataTask = new Task<>() {
            @Override
            protected GetAllOwnedItemResponse call() {
                return itemService.getAllOwnedItems(0);
            }
        };

        loadDataTask.setOnRunning((e) -> {
            totalOwnedItemLabel.setText("");
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
            totalOwnedItemLabel.setText("Total Item: " + itemList.size());
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
                        itemNameField.getText() != null ? Optional.of(itemNameField.getText()) : Optional.empty(),
                        startDatePicker.getValue() != null ? Optional.of(startDatePicker.getValue().atStartOfDay())
                                : Optional.empty(),
                        endDatePicker.getValue() != null ? Optional.of(endDatePicker.getValue().atTime(LocalTime.MAX))
                                : Optional.empty(),
                        Optional.empty(),
                        StateManager.getInstance().getUserId(), Optional.empty());
            }
        };

        task.setOnRunning((e) -> {
            searchItemBtn.setDisable(true);
            searchItemBtn.setText("Searching...");
            progressIndicator.setVisible(true);
        });
        task.setOnSucceeded((e) -> {
            SearchItemResponse response = task.getValue();
            if (response == null || response.getStatusCode() == 0) {
                JavaFxUtil.createAlert("Error Dialog", "Item Error", "Failed to search item");
            }

            if (response.getStatusCode() == 1) {
                ArrayList<Item> itemList = (ArrayList<Item>) response.getLists();
                totalOwnedItemLabel.setText("Total Item: " + itemList.size());
                this.tableViewItemController.clearTableViewData();
                this.tableViewItemController.addTableViewData(FXCollections.observableArrayList(itemList));
            }

            searchItemBtn.setDisable(false);
            searchItemBtn.setText("Search Item");
            progressIndicator.setVisible(false);

        });
        task.setOnFailed((e) -> {
            Throwable exception = task.getException();
            exception.printStackTrace();
            JavaFxUtil.createAlert("Error Dialog", "Item Error", "An error occurred during item search");
            searchItemBtn.setDisable(false);
            searchItemBtn.setText("Search Item");
            progressIndicator.setVisible(false);
        });

        new Thread(task).start();
    }
}
