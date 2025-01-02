package com.networking.auction.protocol.request.room;

import com.networking.auction.protocol.request.Request;
import com.networking.auction.util.RequestEnum;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GetUserLogRequest extends Request {
    private int roomId;
    private int itemId;

    @Builder
    public GetUserLogRequest(int roomId, int itemId) {
        this.roomId = roomId;
        this.itemId = itemId;
    }

    @Override
    public int getRequestType() {
        return RequestEnum.VIEW_USER_LOG_REQ.getRequest();
    }

}
