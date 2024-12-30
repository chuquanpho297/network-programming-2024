package com.networking.auction.protocol.request;

import java.util.Optional;

import com.networking.auction.StateManager;
import com.networking.auction.util.RequestEnum;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LogoutRequest extends Request {
    private Optional<Integer> userId;

    @Builder
    public LogoutRequest() {
        this.userId = StateManager.getInstance().getUserId();
    }

    @Override
    public int getRequestType() {
        return RequestEnum.LOGOUT_REQ.getRequest();
    }
}
