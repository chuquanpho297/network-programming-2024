package com.networking.auction.service;

import com.networking.auction.protocol.request.LoginRequest;
import com.networking.auction.protocol.request.LogoutRequest;
import com.networking.auction.protocol.request.RegisterRequest;
import com.networking.auction.protocol.response.LoginResponse;
import com.networking.auction.protocol.response.LogoutResponse;
import com.networking.auction.protocol.response.RegisterResponse;
import com.networking.auction.socket.TCPClient;

public class UserService {

    private static UserService userService;

    public static UserService getInstance() {
        if (userService == null) {
            userService = new UserService();
        }
        return userService;
    }

    public LoginResponse login(String username, String password) {
        try {
            LoginRequest loginRequest = LoginRequest.builder()
                    .username(username)
                    .password(password)
                    .build();

            String receiveMess = TCPClient.fetchServer(loginRequest.toString());
            LoginResponse loginResponse = LoginResponse.parseResponse(receiveMess);

            return loginResponse;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public LogoutResponse logout() {
        try {
            LogoutRequest logoutRequest = new LogoutRequest();

            String receiveMess = TCPClient.fetchServer(logoutRequest.toString());
            LogoutResponse response = LogoutResponse.parseResponse(receiveMess);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public RegisterResponse register(String username, String password) {
        try {
            RegisterRequest registerRequest = RegisterRequest.builder()
                    .username(username)
                    .password(password)
                    .build();
            String receiveMess = TCPClient.fetchServer(registerRequest.toString());
            RegisterResponse registerResponse = RegisterResponse.parseResponse(receiveMess);

            return registerResponse;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}