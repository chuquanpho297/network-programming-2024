package com.networking.auction.controller;

import static javafx.scene.input.MouseEvent.MOUSE_CLICKED;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.networking.auction.StateManager;
import com.networking.auction.protocol.response.LoginResponse;
import com.networking.auction.service.UserService;
import com.networking.auction.util.JavaFxUtil;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController extends Controller implements Initializable {

    private final UserService userService = UserService.getInstance();
    @FXML
    public PasswordField password;

    @FXML
    public TextField username;

    @FXML
    public Label register;

    @FXML
    private Button loginButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginButton.addEventHandler(MOUSE_CLICKED, event -> {
            loginButton.setDisable(true);
            loginButton.setText("Login...");
            Task<LoginResponse> task = new Task<>() {
                @Override
                protected LoginResponse call() throws Exception {
                    return userService.login(username.getText(), password.getText());
                }
            };
            task.setOnSucceeded(workerStateEvent -> {
                loginButton.setDisable(false);
                loginButton.setText("Login");
                try {
                    LoginResponse loginResponse = task.getValue();

                    if (loginResponse == null) {
                        JavaFxUtil.createAlert("Error Dialog", "Login Error", "Login failed");
                    }

                    switch (loginResponse.getStatusCode()) {
                        case 0:
                            JavaFxUtil.createAlert("Error Dialog", "Login Error", "Invalid username or password");
                            break;
                        case 1:
                            StateManager.getInstance().setUserId(Optional.of(loginResponse.getUserId()));
                            StateManager.getInstance().setRoomId(Optional.of(loginResponse.getRoomId()));
                            StateManager.getInstance().setUsername(Optional.of(username.getText()));
                            switchToScreen((Stage) ((Node) event.getSource()).getScene().getWindow(), "room");
                            break;
                        case 2:
                            JavaFxUtil.createAlert("Error Dialog", "Login Error", "User already logged in");
                            break;
                        default:
                            throw new IllegalArgumentException("Invalid status code");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            task.setOnFailed(workerStateEvent -> {
                Throwable e = task.getException();
                e.printStackTrace();
                JavaFxUtil.createAlert("Error Dialog", "Login Error", "An error occurred during registration");
                loginButton.setDisable(false);
                loginButton.setText("Login");
            });

            new Thread(task).start();
        });

        register.addEventHandler(MOUSE_CLICKED, event -> {
            try {
                switchToScreen((Stage) ((Node) event.getSource()).getScene().getWindow(), "register");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}