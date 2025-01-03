package com.networking.auction.protocol.request;

import com.networking.auction.util.RequestEnum;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginRequest extends Request {
    private String username;
    private String password;

    @Builder
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public int getRequestType() {
        return RequestEnum.LOGIN_REQ.getRequest();
    }
}