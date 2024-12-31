package com.networking.auction;

import com.networking.auction.util.FxmlUtil;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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
    }
}