package com.networking.auction;

import java.time.LocalDateTime;
import java.util.Optional;

import com.networking.auction.protocol.request.item.SearchItemRequest;
import com.networking.auction.socket.TCPClient;
import com.networking.auction.util.FxmlUtil;
import com.networking.auction.util.JavaFxUtil;

import io.github.cdimascio.dotenv.Dotenv;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();
        TCPClient client = new TCPClient(Integer.parseInt(dotenv.get("SERVER_PORT")), dotenv.get("SERVER_ADDRESS"));
        StateManager.getInstance().setClientSocket(client);
        try {
            launch();
        } finally {
            client.closeSocketConnection();
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        // Load the FXML file for the login screen
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource(FxmlUtil.LOGIN.getFxmlPath()));
        // Create the scene with the root node loaded from the FXML file
        Scene scene = new Scene(loader.load());
        String css = this.getClass().getResource(FxmlUtil.LOGIN.getCssPath()).toExternalForm();
        scene.getStylesheets().add(css);
        stage.setScene(scene);

        // Set the title for the stage
        stage.setTitle("Auction System");

        // Show the stage
        stage.show();

        if (StateManager.getInstance().getClientSocket().getSocket() == null) {
            JavaFxUtil.createAlert(AlertType.WARNING, "Error Dialog", "Connection Error",
                    "Failed to connect to server");
        }
    }
}