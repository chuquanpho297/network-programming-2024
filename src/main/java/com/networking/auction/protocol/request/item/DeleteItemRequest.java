package com.networking.auction.protocol.request.item;

import com.networking.auction.StateManager;
import com.networking.auction.protocol.request.Request;
import com.networking.auction.util.RequestEnum;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteItemRequest extends Request {
    private int itemId;
    private int ownerId;

    @Builder
    public DeleteItemRequest(int itemId) {
        this.itemId = itemId;
        this.ownerId = StateManager.getInstance().getUserId().orElseThrow();
    }

    @Override
    public int getRequestType() {
        return RequestEnum.DELETE_ITEM_REQ.getRequest();
    }

}
