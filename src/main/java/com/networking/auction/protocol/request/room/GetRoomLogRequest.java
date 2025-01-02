package com.networking.auction.protocol.request.room;

import com.networking.auction.protocol.request.Request;
import com.networking.auction.util.RequestEnum;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GetRoomLogRequest extends Request {
    private int roomId;

    @Builder
    public GetRoomLogRequest(int roomId) {
        this.roomId = roomId;
    }

    @Override
    public int getRequestType() {
        return RequestEnum.VIEW_ROOM_LOG_REQ.getRequest();
    }

}
