package com.networking.auction;

import java.util.Optional;

import com.networking.auction.socket.TCPClient;

import lombok.Getter;
import lombok.Setter;

public class StateManager {

    private static StateManager instance;

    @Getter
    @Setter
    private TCPClient clientSocket;

    @Setter
    @Getter
    private Optional<Integer> userId;

    @Setter
    @Getter
    private Optional<String> username;

    @Setter
    @Getter
    private Optional<Integer> roomId;

    public static StateManager getInstance() {
        if (instance == null) {
            instance = new StateManager();
        }
        return instance;
    }

    private StateManager() {
        this.userId = Optional.empty();
        this.username = Optional.empty();
        this.roomId = Optional.empty();
    }
}
