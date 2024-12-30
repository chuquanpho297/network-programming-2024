package com.networking.auction.protocol.request;

import com.networking.auction.StateManager;
import com.networking.auction.util.RequestEnum;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LogoutRequest extends Request {
    private int userId;

    @Builder
    public LogoutRequest() {
        this.userId = StateManager.getInstance().getUserId().orElse(-1);
    }

    @Override
    public int getRequestType() {
        return RequestEnum.LOGOUT_REQ.getRequest();
    }
}
