package com.networking.auction.controller;

import static javafx.scene.input.MouseEvent.MOUSE_CLICKED;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.networking.auction.protocol.response.RegisterResponse;
import com.networking.auction.service.UserService;
import com.networking.auction.util.JavaFxUtil;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegisterController extends Controller implements Initializable {

    @FXML
    private Button registerButton;

    @FXML
    private Label login;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    private UserService userService = UserService.getInstance();

    public RegisterController(String fxmlPath) throws IOException {
        super(fxmlPath, "register/styles.css");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        registerButton.addEventHandler(MOUSE_CLICKED, event -> {
            registerButton.setDisable(true);
            registerButton.setText("Registering...");
            Task<RegisterResponse> task = new Task<>() {
                @Override
                protected RegisterResponse call() throws Exception {
                    return userService.register(username.getText(), password.getText());
                }
            };

            task.setOnSucceeded(workerStateEvent -> {
                registerButton.setDisable(false);
                registerButton.setText("Register");

                RegisterResponse response = task.getValue();
                if (response == null) {
                    JavaFxUtil.createAlert("Error Dialog", "Register Error", "Register failed");
                    return;
                }
                switch (response.getStatusCode()) {
                    case 0:
                        JavaFxUtil.createAlert("Error Dialog", "Register Error", "Server error");
                        break;
                    case 1:
                        try {
                            JavaFxUtil.createAlert(AlertType.INFORMATION, "Information Dialog", "Register Success",
                                    "Register successfully");
                            Thread.sleep(500);
                            LoginController loginController = new LoginController("login/index.fxml");
                            loginController.show();
                            this.getStage().close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case 2:
                        JavaFxUtil.createAlert("Error Dialog", "Register Error", "Username already exists");
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid status code");
                }
            });

            task.setOnFailed(workerStateEvent -> {
                Throwable e = task.getException();
                e.printStackTrace();
                JavaFxUtil.createAlert("Error Dialog", "Register Error", "An error occurred during registration");

                registerButton.setDisable(false);
                registerButton.setText("Register");
            });

            new Thread(task).start();
        });

        login.addEventHandler(MOUSE_CLICKED, event -> {
            try {
                LoginController loginController = new LoginController("login/index.fxml");
                loginController.show();
                this.getStage().close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}