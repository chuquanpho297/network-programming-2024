package com.networking.auction;

import com.networking.auction.controller.LoginController;

import javafx.application.Application;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    public static void main(String[] args) {
        launch();
    }

    // TODO: Add room log (place item in room request)
    // TODO: Add user log (in auction, place bid, buy now will be logged)
    // TODO: Add place item to room (after join room)
    // TODO: Add place bid (after join room)
    // TODO: Add buy now (after join room)
    // TODO: Add send notification to user

    @Override
    public void start(Stage stage) throws Exception {
        // Load the FXML file for the login screen
        // stage.setTitle("Auction System");
        // FXMLLoader loader = new FXMLLoader(
        // this.getClass().getResource("login/index.fxml"));
        // // Create the scene with the root node loaded from the FXML file
        // Scene scene = new Scene(loader.load());
        // String css = this.getClass().getResource("login/style.css").toExternalForm();
        // scene.getStylesheets().add(css);
        // stage.setScene(scene);

        // // Set the title for the stage

        // // Show the stage
        // stage.show();
        LoginController loginController = new LoginController("login/index.fxml");
        stage.setTitle("Auction System");
        loginController.setStage(stage);
        loginController.show();
    }
}