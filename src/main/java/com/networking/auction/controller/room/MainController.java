package com.networking.auction.controller.room;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import com.networking.auction.HelloApplication;
import com.networking.auction.StateManager;
import com.networking.auction.controller.Controller;
import com.networking.auction.controller.item.AllItemController;
import com.networking.auction.controller.item.OwnedItemController;
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

    private final UserService userService = UserService.getInstance();

    private static MainController instance;

    private MainController() {
        // Private constructor to prevent instantiation
    }

    public static MainController getInstance() {
        if (instance == null) {
            synchronized (MainController.class) {
                if (instance == null) {
                    instance = new MainController();
                }
            }
        }
        return instance;
    }

    public void setMainBody(String fxmlPath, Controller controller, String title) {
        progressIndicator.setVisible(false);
        setTitle(title, titleLabel);
        try {
            body.getChildren().clear();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(HelloApplication.class.getResource(fxmlPath));
            loader.setController(controller);
            Node node = loader.load();
            body.getChildren().add(node);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.username.setText("Hello, " + StateManager.getInstance().getUsername());
        this.setMainBody("room/room.fxml", new RoomController(progressIndicator), "Room List");

        setClickable(roomListLabel, event -> {
            this.setMainBody("room/room.fxml", new RoomController(progressIndicator), "Room List");
            return null;
        });

        setClickable(ownedRoomListLabel, event -> {
            this.setMainBody("room/owned_room.fxml", new OwnedRoomController(progressIndicator), "Owned Room List");
            return null;
        });

        setClickable(itemListLabel, event -> {
            this.setMainBody("item/item.fxml", new AllItemController(progressIndicator), "Item List");
            return null;
        });

        setClickable(ownedItemListLabel, event -> {
            this.setMainBody("item/owned_item.fxml", new OwnedItemController(progressIndicator), "Owned Item List");
            return null;
        });

        setClickable(logoutBtn, event -> {
            try {
                LogoutResponse response = userService.logout();
                if (response == null) {
                    JavaFxUtil.createAlert("Error Dialog", "Logout Error", "Logout failed");
                }

                switch (Objects.requireNonNull(response).getStatusCode()) {
                    case 0:
                        JavaFxUtil.createAlert("Error Dialog", "Logout Error", "Server error");
                        break;
                    case 1:
                        switchToScreen((Stage) ((Node) event.getSource()).getScene().getWindow(), "login");
                        break;
                    case 2:
                        JavaFxUtil.createAlert("Error Dialog", "Logout Error", "User not logged in");
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid status code");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        });

    }
}
