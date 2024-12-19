package com.networking.meetingclient.protocol.request;

import java.util.Optional;

import com.networking.meetingclient.StateManager;
import com.networking.meetingclient.util.RequestEnum;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LogoutRequest extends Request {
    private int userId;

    @Builder
    public LogoutRequest() {
        this.userId = Optional.ofNullable(StateManager.getInstance().getUserId()).orElse(-1);
    }

    @Override
    public int getRequestType() {
        return RequestEnum.LOGOUT_REQ.getRequest();
    }
}
