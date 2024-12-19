
module com.networking.meetingclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires static lombok;
    requires javafx.graphics;

    exports com.networking.meetingclient;
    exports com.networking.meetingclient.controller;
    exports com.networking.meetingclient.controller.room; // Add this line
    exports com.networking.meetingclient.models;
    exports com.networking.meetingclient.util;
    exports com.networking.meetingclient.protocol.request to com.fasterxml.jackson.databind;

    opens com.networking.meetingclient.controller to javafx.fxml;
    opens com.networking.meetingclient.controller.room to javafx.fxml; // Add this line
    opens com.networking.meetingclient.models to javafx.fxml;
    opens com.networking.meetingclient.util to javafx.fxml;
    opens com.networking.meetingclient.protocol.request to com.fasterxml.jackson.databind;
}
