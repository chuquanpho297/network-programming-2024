package com.networking.auction.controller.room;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ResourceBundle;

import com.networking.auction.controller.Controller;
import com.networking.auction.controller.item.TableViewItemController;
import com.networking.auction.models.Item;
import com.networking.auction.models.Room;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class AuctionRoomController extends Controller implements Initializable {
    @FXML
    private TableView<Item> itemTableView;

    @FXML
    private TableViewItemController tableViewItemController;

    @FXML
    private Button backBtn;

    @FXML
    private Label titleLabel;

    @FXML
    private Label countdownLabel;

    private Timeline timeline;
    private LocalTime endTime;
    private Room room;

    public AuctionRoomController(Room room) {
        this.room = room;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableViewItemController = (TableViewItemController) itemTableView.getProperties().get("controller");
        // Initialize table columns if needed
        startCountdown(Duration.ofMinutes(5)); // Example: 5-minute countdown
        titleLabel.setText(room.getRoomName());
        backBtn.setOnAction(this::handleBackButton);
        tableViewItemController.getBuyNowPriceColumn().setVisible(false);
        tableViewItemController.getCurrentPriceColumn().setVisible(false);
        tableViewItemController.getStateColumn().setVisible(false);
    }

    @FXML
    private void handleBackButton(Event e) {
        try {
            switchToScreen((Stage) ((Node) e.getSource()).getScene().getWindow(), "room");
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private void startCountdown(Duration duration) {
        endTime = LocalTime.now().plusSeconds(duration.getSeconds());
        timeline = new Timeline(
                new KeyFrame(javafx.util.Duration.seconds(1), event -> {
                    Duration remaining = Duration.between(LocalTime.now(), endTime);
                    if (!remaining.isNegative() && !remaining.isZero()) {
                        countdownLabel.setText(formatDuration(remaining));
                    } else {
                        countdownLabel.setText("00:00:00");
                        timeline.stop();
                    }
                }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private String formatDuration(Duration duration) {
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;
        long seconds = duration.getSeconds() % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}