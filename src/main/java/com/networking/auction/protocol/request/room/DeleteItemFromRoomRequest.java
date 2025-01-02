package com.networking.auction.protocol.request.room;

import com.networking.auction.StateManager;
import com.networking.auction.protocol.request.Request;
import com.networking.auction.util.RequestEnum;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DeleteItemFromRoomRequest extends Request {
    private int userId;
    private int itemId;
    private int roomId;

    @Builder
    public DeleteItemFromRoomRequest(int itemId, int roomId) {
        this.itemId = itemId;
        this.roomId = roomId;
        this.userId = StateManager.getInstance().getUserId().orElseThrow();
    }

    @Override
    public int getRequestType() {
        return RequestEnum.DELETE_ITEM_IN_ROOM_REQ.getRequest();
    }

}
