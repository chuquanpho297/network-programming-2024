package com.networking.meetingclient.controller.room;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import com.networking.meetingclient.HelloApplication;
import com.networking.meetingclient.StateManager;
import com.networking.meetingclient.controller.Controller;
import com.networking.meetingclient.protocol.response.LogoutResponse;
import com.networking.meetingclient.service.UserService;
import com.networking.meetingclient.util.JavaFxUtil;

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
    public Label homeLabel;
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
    private VBox screen;

    @FXML
    private VBox sidebar;

    @FXML
    private Label title;

    @FXML
    private Label username;

    private final UserService userService = UserService.getInstance();

    private void setMainBody(String fxmlPath, Controller controller) {
        progressIndicator.setVisible(false);
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
        this.setMainBody("room/room.fxml", new RoomController(progressIndicator));
        setTitle("Room List", title);

        setClickable(roomListLabel, event -> {
            this.setMainBody("room/room.fxml", new RoomController(progressIndicator));
            setTitle("Room List", title);
            return null;
        });

        setClickable(ownedRoomListLabel, event -> {
            this.setMainBody("room/owned_room.fxml", new OwnedRoomController(progressIndicator));
            setTitle("Owned Room List", title);
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
