package com.networking.auction.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.networking.auction.HelloApplication;
import com.networking.auction.StateManager;
import com.networking.auction.controller.item.AllItemController;
import com.networking.auction.controller.item.OwnedItemController;
import com.networking.auction.controller.room.OwnedRoomController;
import com.networking.auction.controller.room.RoomController;
import com.networking.auction.protocol.response.LogoutResponse;
import com.networking.auction.service.UserService;
import com.networking.auction.util.JavaFxUtil;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainController extends Controller implements Initializable {
    @FXML
    private Label homeLabel;

    @FXML
    private VBox body;

    @FXML
    private HBox header;

    @FXML
    private Button logoutBtn;

    @FXML
    private ProgressIndicator progressIndicator;

    @FXML
    private Label roomListLabel;

    @FXML
    private Label ownedRoomListLabel;

    @FXML
    private Label itemListLabel;

    @FXML
    private Label ownedItemListLabel;

    @FXML
    private VBox screen;

    @FXML
    private VBox sidebar;

    @FXML
    private Label titleLabel;

    @FXML
    private Label username;

    private UserService userService;

    private StateManager stateManager;

    public MainController(String fxmlPath) throws IOException {
        super(fxmlPath);

    }

    public void setMainBody(String fxmlPath) {
        progressIndicator.setVisible(false);
        String title = mapFxmlPathToTitle(fxmlPath);
        Controller controller1 = null;
        try {
            controller1 = getController(fxmlPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        setTitle(title, titleLabel);
        this.stateManager.setMainFxmlPath(Optional.of(fxmlPath));

        try {
            body.getChildren().clear();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(HelloApplication.class.getResource(fxmlPath));
            loader.setController(controller1);
            Node node = loader.load();
            body.getChildren().add(node);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.username.setText("Hello, " + StateManager.getInstance().getUsername().orElse("User"));
        this.stateManager = StateManager.getInstance();
        this.userService = UserService.getInstance();
        this.setMainBody("room/room.fxml");

        setClickable(roomListLabel, event -> {
            this.setMainBody("room/room.fxml");
            return null;
        });

        setClickable(ownedRoomListLabel, event -> {
            this.setMainBody("room/owned_room.fxml");
            return null;
        });

        setClickable(itemListLabel, event -> {
            this.setMainBody("item/item.fxml");
            return null;
        });

        setClickable(ownedItemListLabel, event -> {
            this.setMainBody("item/owned_item.fxml");
            return null;
        });

        setClickable(logoutBtn, event -> {
            try {
                LogoutResponse response = userService.logout();
                if (response == null) {
                    JavaFxUtil.createAlert("Error Dialog", "Logout Error", "Logout failed");
                }

                StateManager.getInstance().setUserId(Optional.empty());
                StateManager.getInstance().setUsername(Optional.empty());
                StateManager.getInstance().setRoomId(Optional.empty());
                StateManager.getInstance().setMainFxmlPath(Optional.empty());

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        });

    }

    private String mapFxmlPathToTitle(String fxmlPath) {
        switch (fxmlPath) {
            case "room/room.fxml":
                return "Room List";
            case "room/owned_room.fxml":
                return "Owned Room List";
            case "item/item.fxml":
                return "Item List";
            case "item/owned_item.fxml":
                return "Owned Item List";
            default:
                return "Home";
        }
    }

    private Controller getController(String fxmlPath) throws IOException {
        switch (fxmlPath) {
            case "room/room.fxml":
                RoomController controller = new RoomController(this.getStage(), fxmlPath, progressIndicator);
                controller.setMainController(this);
                return controller;

            case "room/owned_room.fxml":
                OwnedRoomController controller1 = new OwnedRoomController(this.getStage(), fxmlPath, progressIndicator);
                controller1.setMainController(this);
                return controller1;
            case "item/item.fxml":
                AllItemController controller2 = new AllItemController(progressIndicator, this.getStage(), fxmlPath);
                controller2.setMainController(this);
                return controller2;
            case "item/owned_item.fxml":
                OwnedItemController controller3 = new OwnedItemController(progressIndicator, this.getStage(), fxmlPath);
                controller3.setMainController(this);
                return controller3;
            default:
                RoomController controller4 = new RoomController(this.getStage(), fxmlPath, progressIndicator);
                controller4.setMainController(this);
                return controller4;
        }
    }
}
