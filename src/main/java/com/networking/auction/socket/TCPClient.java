package com.networking.auction.socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Logger;

import com.networking.auction.util.ResponseUtil;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class TCPClient {
    private final static Logger LOGGER = Logger.getLogger(TCPClient.class.getName());
    private BufferedReader inFromServer;
    private int clientPort;
    private String clientAddress;
    private int serverPort;
    private String serverAddress;
    private Socket socket;
    private BufferedWriter outToServer;

    public TCPClient(int serverPort, String serverAddress) {
        this.serverPort = serverPort;
        this.serverAddress = serverAddress;
        this.createSocket();

    }

    private void createSocket() {
        try {
            this.socket = new Socket(this.serverAddress, this.serverPort);
            this.socket.setSoTimeout(30000); // Set timeout to 30 seconds
            this.inFromServer = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.outToServer = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
            this.clientAddress = this.socket.getLocalAddress().getHostAddress();
            this.clientPort = this.socket.getLocalPort();
            LOGGER.info("Socket created");
        } catch (IOException e) {
            LOGGER.warning("Failed to create socket: " + e.getMessage());
        }
    }

    public void send(String message) throws IOException {
        try {
            LOGGER.info(String.format("TO SERVER %s:%s : %s\n", this.serverAddress, this.serverPort, message));
            this.outToServer.write(message);
            this.outToServer.flush();
        } catch (SocketException e) {
            if (e.getMessage().contains("Broken pipe")) {
                LOGGER.warning("Broken pipe detected. Attempting to reconnect...");
                closeSocketConnection();
                createSocket();
                if (this.outToServer != null) {
                    LOGGER.info(String.format("TO SERVER %s:%s : %s\n", this.serverAddress, this.serverPort, message));
                    this.outToServer.write(message);
                    this.outToServer.flush();
                }
            } else {
                throw e;
            }
        } catch (IOException e) {
            LOGGER.warning("Failed to send message: " + e.getMessage());
            throw e;
        }
    }

    public String receive() throws IOException, InterruptedException {
        try {
            StringBuilder receivedMess = new StringBuilder();
            String responsePart;

            do {
                responsePart = this.inFromServer.readLine();
                receivedMess.append(responsePart).append("\n");
            } while (!responsePart.equals(ResponseUtil.END_TAG));

            System.out.println(
                    "FROM SERVER " + this.serverAddress + ":" + this.serverPort + " : " + receivedMess);

            return receivedMess.toString();
        } catch (IOException e) {
            LOGGER.warning("Failed to receive message: " + e.getMessage());
            throw e;
        }
    }

    public String sendAndReceive(String message) throws IOException, InterruptedException {
        this.send(message);
        return this.receive();
    }

    public void closeSocketConnection() {
        try {
            LOGGER.info("Close socket");
            this.socket.close();
        } catch (IOException e) {
            LOGGER.warning("Failed to close socket");
        }
    }
}
