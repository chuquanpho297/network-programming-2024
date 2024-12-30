
module com.networking.auction {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires static lombok;
    requires javafx.graphics;
    requires dotenv.java;

    exports com.networking.auction;
    exports com.networking.auction.controller;
    exports com.networking.auction.controller.room; // Add this line
    exports com.networking.auction.models;
    exports com.networking.auction.util;
    exports com.networking.auction.protocol.request to com.fasterxml.jackson.databind;

    opens com.networking.auction.controller to javafx.fxml;
    opens com.networking.auction.controller.room to javafx.fxml; // Add this line
    opens com.networking.auction.models to javafx.fxml;
    opens com.networking.auction.util to javafx.fxml;
    opens com.networking.auction.protocol.request to com.fasterxml.jackson.databind;
}
