package com.networking.meetingclient.protocol.request.room;

import java.util.Optional;

import com.networking.meetingclient.StateManager;
import com.networking.meetingclient.protocol.request.Request;
import com.networking.meetingclient.util.RequestEnum;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GetAllOwnedRoomRequest extends Request {
    private int userId;

    @Builder
    public GetAllOwnedRoomRequest() {
        this.userId = Optional.ofNullable(StateManager.getInstance().getUserId()).orElse(-1);
    }

    @Override
    public int getRequestType() {
        return RequestEnum.VIEW_ROOMS_OWNED_REQ.getRequest();
    }
}
