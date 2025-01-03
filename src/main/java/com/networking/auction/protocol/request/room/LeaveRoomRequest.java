package com.networking.auction.protocol.request.room;

import com.networking.auction.StateManager;
import com.networking.auction.protocol.request.Request;
import com.networking.auction.util.RequestEnum;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LeaveRoomRequest extends Request {
    private int userId;
    private int roomId;

    @Builder
    public LeaveRoomRequest(int roomId) {
        this.roomId = roomId;
        this.userId = StateManager.getInstance().getUserId().orElseThrow();
    }

    @Override
    public int getRequestType() {
        return RequestEnum.LEAVE_ROOM_REQ.getRequest();
    }

}
