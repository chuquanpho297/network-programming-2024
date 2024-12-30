package com.networking.auction.protocol.request.room;

import com.networking.auction.StateManager;
import com.networking.auction.protocol.request.Request;
import com.networking.auction.util.RequestEnum;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JoinRoomRequest extends Request {
    private int userId;
    private int roomId;

    @Builder
    public JoinRoomRequest(int roomId) {
        this.roomId = roomId;
        this.userId = StateManager.getInstance().getUserId().orElseThrow();
    }

    @Override
    public int getRequestType() {
        return RequestEnum.JOIN_ROOM_REQ.getRequest();
    }

}
