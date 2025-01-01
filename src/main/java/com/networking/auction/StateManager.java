package com.networking.auction;

import java.util.Optional;

import com.networking.auction.socket.TCPClient;

import io.github.cdimascio.dotenv.Dotenv;


public class StateManager {

    private static StateManager instance;

    private TCPClient clientSocket;


    private Optional<Integer> userId;


    private Optional<String> username;


    private Optional<Integer> roomId;


    private Optional<String> mainFxmlPath;


    private String serverHost;


    private int serverPort;

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
        this.mainFxmlPath = Optional.empty();
        Dotenv dotenv = Dotenv.load();
        this.serverHost = dotenv.get("SERVER_HOST");
        this.serverPort = Integer.parseInt(dotenv.get("SERVER_PORT"));
    }

    public Optional<Integer> getUserId() {
        return userId;
    }

    public void setUserId(Optional<Integer> userId) {
        this.userId = userId;
    }

    public Optional<String> getUsername() {
        return username;
    }

    public void setUsername(Optional<String> username) {
        this.username = username;
    }

    public Optional<Integer> getRoomId() {
        return roomId;
    }

    public void setRoomId(Optional<Integer> roomId) {
        this.roomId = roomId;
    }

    public Optional<String> getMainFxmlPath() {
        return mainFxmlPath;
    }

    public void setMainFxmlPath(Optional<String> mainFxmlPath) {
        this.mainFxmlPath = mainFxmlPath;
    }

    public String getServerHost() {
        return serverHost;
    }

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    @SuppressWarnings("exports")
    public TCPClient getClientSocket() {
        return clientSocket;
    }

    @SuppressWarnings("exports")
    public void setClientSocket(TCPClient clientSocket) {
        this.clientSocket = clientSocket;
    }

}
