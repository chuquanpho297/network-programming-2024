package com.networking.auction.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class JavaFxUtil {
    public static void createAlert(AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);

        alert.showAndWait();
    }

    public static void createAlert(String title, String headerText, String contentText) {
        createAlert(AlertType.ERROR, title, headerText, contentText);
    }

    public static String formatPrice(Float price) {
        if (price >= 1_000_000_000) {
            return String.format("%.1fB", price / 1_000_000_000);
        } else if (price >= 1_000_000) {
            return String.format("%.1fM", price / 1_000_000);
        } else if (price >= 1_000) {
            return String.format("%.1fK", price / 1_000);
        } else {
            return String.format("%.2f", price);
        }
    }
}
