package com.networking.auction.protocol.request.room;

import com.networking.auction.StateManager;
import com.networking.auction.protocol.request.Request;
import com.networking.auction.util.RequestEnum;

import lombok.Builder;
import lombok.Getter;

@Getter

public class PlaceItemInRoomRequest extends Request {
    private int userId;
    private int itemId;
    private int roomId;

    @Builder
    public PlaceItemInRoomRequest(int roomId, int itemId) {
        this.roomId = roomId;
        this.itemId = itemId;
        this.userId = StateManager.getInstance().getUserId().orElseThrow();
    }

    @Override
    public int getRequestType() {
        return RequestEnum.PLACE_ITEM_IN_ROOM_REQ.getRequest();
    }

}
