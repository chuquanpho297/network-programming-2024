package com.networking.auction.protocol.request.room;

import com.networking.auction.protocol.request.Request;
import com.networking.auction.util.RequestEnum;

import lombok.Builder;
import lombok.Getter;
@Getter
public class AcceptRejectItemRequest extends Request {

    private int itemId;
    private int roomId;
    private int confirmCode;

    @Builder
    public AcceptRejectItemRequest(int itemId, int roomId, int confirmCode) {
        this.itemId = itemId;
        this.roomId = roomId;
        this.confirmCode = confirmCode;
    }

    @Override
    public int getRequestType() {
        return RequestEnum.ACCEPT_REJECT_ITEM_REQ.getRequest();
    }

}
