package com.networking.auction.protocol.request.room;

import java.time.LocalDateTime;

import com.networking.auction.StateManager;
import com.networking.auction.protocol.request.Request;
import com.networking.auction.util.RequestEnum;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CreateRoomRequest extends Request {
    private int userId;
    private String roomName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @Builder
    public CreateRoomRequest(String roomName, LocalDateTime startTime, LocalDateTime endTime) {
        this.userId = StateManager.getInstance().getUserId().orElseThrow();
        this.roomName = roomName;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public int getRequestType() {
        return RequestEnum.CREATE_ROOM_REQ.getRequest();
    }
}
