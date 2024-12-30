package com.networking.auction.protocol.request.room;

import com.networking.auction.StateManager;
import com.networking.auction.protocol.request.Request;
import com.networking.auction.util.RequestEnum;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CreateRoomRequest extends Request {
    private int userId;
    private String roomName;

    @Builder
    public CreateRoomRequest(String roomName) {
        this.userId = StateManager.getInstance().getUserId().orElseThrow();
        this.roomName = roomName;
    }

    @Override
    public int getRequestType() {
        return RequestEnum.CREATE_ROOM_REQ.getRequest();
    }
}
