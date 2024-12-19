package com.networking.auction.protocol.request;

import com.networking.auction.util.RequestEnum;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RegisterRequest extends Request {
    private String username;
    private String password;

    @Builder
    public RegisterRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public int getRequestType() {
        return RequestEnum.REGISTER_REQ.getRequest();
    }
}
