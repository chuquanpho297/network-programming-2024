package com.networking.auction.controller;

import java.io.IOException;
import java.util.function.Function;

import com.networking.auction.HelloApplication;

import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.stage.Stage;

public class Controller extends FXMLLoaderController {
    private Stage stage;
    private Scene scene;
    protected ProgressIndicator progressIndicator;
    protected Controller mainController;
    private Controller prev;
    private Parent root;

    public Controller(String fxmlPath) throws IOException {
        super(fxmlPath);
        this.stage = new Stage();
        this.root = this.getLoader().load();
    }
    public Controller(String fxmlPath, String style) throws IOException {
        super(fxmlPath);
        this.stage = new Stage();
        this.root = this.getLoader().load();
        this.root.getStylesheets().add(HelloApplication.class.getResource(style).toExternalForm());
    }

    public Controller(Stage stage, String fxmlPath) throws IOException {
        super(fxmlPath);
        this.stage = stage;
        this.root = this.getLoader().load();
    }

    public Controller(Stage stage, String fxmlPath, String style) throws IOException {
        super(fxmlPath);
        this.stage = stage;
        this.root = this.getLoader().load();
        this.root.getStylesheets().add(HelloApplication.class.getResource(style).toExternalForm());
    }

    // @SuppressWarnings("exports")
    // public void switchToScreen(Stage stage, String fxmlDir) throws IOException {
    // try {
    // Parent root = FXMLLoader
    // .load(Objects.requireNonNull(HelloApplication.class.getResource(fxmlDir +
    // "/index.fxml")));
    // root.getStylesheets().add(HelloApplication.class.getResource(fxmlDir +
    // "/styles.css").toExternalForm());
    // this.scene = new Scene(root);
    // setStage(stage);
    // this.stage.setScene(scene);
    // this.stage.show();

    // } catch (IOException e) {
    // e.printStackTrace();
    // }
    // }

    // @SuppressWarnings("exports")
    // public void switchToScreen(Stage stage, String fxmlDir, Controller
    // controller) throws IOException {
    // try {
    // FXMLLoader loader = new
    // FXMLLoader(Objects.requireNonNull(HelloApplication.class.getResource(fxmlDir)));
    // loader.setController(controller);
    // Parent root = loader.load();
    // root.getStylesheets().add(HelloApplication.class.getResource(fxmlDir +
    // "/styles.css").toExternalForm());
    // this.scene = new Scene(root);
    // setStage(stage);
    // this.stage.setScene(scene);
    // this.stage.show();

    // } catch (IOException e) {
    // e.printStackTrace();
    // }
    // }

    // @SuppressWarnings("exports")
    // public void switchToScreenNotStyle(Stage stage, String fxmlPath) throws
    // IOException {
    // try {
    // Parent root = new
    // FXMLLoader(Objects.requireNonNull(HelloApplication.class.getResource(fxmlPath))).load();
    // this.scene = new Scene(root);
    // setStage(stage);
    // this.stage.setScene(scene);
    // this.stage.show();

    // } catch (IOException e) {
    // e.printStackTrace();
    // }
    // }

    // @SuppressWarnings("exports")
    // public void switchToScreenNotStyle(Stage stage, String fxmlPath, Controller
    // controller) throws IOException {
    // try {
    // FXMLLoader loader = new
    // FXMLLoader(Objects.requireNonNull(HelloApplication.class.getResource(fxmlPath)));
    // loader.setController(controller);
    // Parent root = loader.load();
    // this.scene = new Scene(root);
    // controller.setStage(stage);
    // this.stage.setScene(scene);
    // this.stage.show();
    // } catch (IOException e) {
    // e.printStackTrace();
    // }
    // }

    public void setTitle(String title, Label titleLabel) {
        titleLabel.setText(title);
    }

    public void setClickable(Node node, Function<Event, Void> func) {
        node.setOnMouseClicked(func::apply);
    }

    public void setPreviousController(Controller prev) {
        System.out.println(prev);
        this.prev = prev;
    }

    public Controller getPreviousController() {
        // System.out.println(this.prev);
        return prev;
    }

    public void show() {
        if (this.scene == null) {
            this.scene = new Scene(this.root);

        }
        try {
            this.stage.setScene(this.scene);
            this.stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return this.stage;
    }

    public void setMainController(Controller controller) {
        this.mainController = controller;
    }
}