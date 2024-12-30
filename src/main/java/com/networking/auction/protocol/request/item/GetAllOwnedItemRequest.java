package com.networking.auction.protocol.request.item;

import com.networking.auction.StateManager;
import com.networking.auction.protocol.request.Request;
import com.networking.auction.util.RequestEnum;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GetAllOwnedItemRequest extends Request {
    private int userId;

    @Builder
    public GetAllOwnedItemRequest() {
        this.userId = StateManager.getInstance().getUserId().orElse(-1);
    }

    @Override
    public int getRequestType() {
        return RequestEnum.VIEW_OWNER_ITEMS_REQ.getRequest();
    }

}
