package com.networking.auction.protocol.request.item;

import com.networking.auction.protocol.request.Request;
import com.networking.auction.util.RequestEnum;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GetAllItemInRoomRequest extends Request {
    private int roomId;

    @Builder
    public GetAllItemInRoomRequest(int roomId) {
        this.roomId = roomId;
    }

    @Override
    public int getRequestType() {
        return RequestEnum.VIEW_ITEMS_IN_ROOM_REQ.getRequest();
    }
}
