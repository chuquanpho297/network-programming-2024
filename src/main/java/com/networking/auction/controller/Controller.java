package com.networking.auction.controller;

import java.io.IOException;
import java.util.Objects;
import java.util.function.Function;

import com.networking.auction.HelloApplication;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.stage.Stage;

public class Controller {

    protected ProgressIndicator progressIndicator;

    public void switchToScreen(Stage stage, String fxmlDir) throws IOException {
        try {
            Parent root = FXMLLoader
                    .load(Objects.requireNonNull(HelloApplication.class.getResource(fxmlDir + "/index.fxml")));
            root.getStylesheets().add(HelloApplication.class.getResource(fxmlDir + "/styles.css").toExternalForm());
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void switchToScreen(Stage stage, String fxmlDir, Controller controller) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(HelloApplication.class.getResource(fxmlDir)));
            loader.setController(controller);
            Parent root = loader.load();
            root.getStylesheets().add(HelloApplication.class.getResource(fxmlDir + "/styles.css").toExternalForm());
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void switchToScreenNotStyle(Stage stage, String fxmlPath) throws IOException {
        try {
            Parent root = FXMLLoader
                    .load(Objects.requireNonNull(HelloApplication.class.getResource(fxmlPath)));
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void switchToScreenNotStyle(Stage stage, String fxmlPath, Controller controller) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(HelloApplication.class.getResource(fxmlPath)));
            loader.setController(controller);
            Parent root = loader.load();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setTitle(String title, Label titleLabel) {
        titleLabel.setText(title);
    }

    public void setClickable(Node node, Function<Event, Void> func) {
        node.setOnMouseClicked(func::apply);
    }
}