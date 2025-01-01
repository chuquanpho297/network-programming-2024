package com.networking.auction.protocol.request.item;

import com.networking.auction.StateManager;
import com.networking.auction.protocol.request.Request;
import com.networking.auction.util.RequestEnum;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateItemRequest extends Request {
    private int ownerId;
    private int itemId;
    private float buyNowPrice;

    @Builder
    public UpdateItemRequest(int ownerId,
            float buyNowPrice, int itemId) {
        this.buyNowPrice = buyNowPrice;
        this.ownerId = StateManager.getInstance().getUserId().orElseThrow();
        this.itemId = itemId;
    }

    @Override
    public int getRequestType() {
        return RequestEnum.UPDATE_ITEM_REQ.getRequest();
    }

}
