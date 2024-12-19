package com.networking.meetingclient.protocol.request.room;

import java.util.Optional;

import com.networking.meetingclient.StateManager;
import com.networking.meetingclient.protocol.request.Request;
import com.networking.meetingclient.util.RequestEnum;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CreateRoomRequest extends Request {
    private int userId;
    private String roomName;

    @Builder
    public CreateRoomRequest(String roomName) {
        this.userId = Optional.ofNullable(StateManager.getInstance().getUserId()).orElse(-1);
        this.roomName = roomName;
    }

    @Override
    public int getRequestType() {
        return RequestEnum.CREATE_ROOM_REQ.getRequest();
    }
}
