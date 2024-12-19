package com.networking.meetingclient;

import com.networking.meetingclient.socket.TCPClient;

import lombok.Getter;
import lombok.Setter;

public class StateManager {

    private static StateManager instance;

    @Getter
    @Setter
    private TCPClient clientSocket;

    @Setter
    @Getter
    private int userId;

    @Setter
    @Getter
    private String username;

    @Setter
    @Getter
    private int roomId;

    public static StateManager getInstance() {
        if (instance == null) {
            instance = new StateManager();
        }
        return instance;
    }
}
