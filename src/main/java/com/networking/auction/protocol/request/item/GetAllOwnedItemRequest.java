package com.networking.auction.protocol.request.item;

import java.util.Optional;

import com.networking.auction.StateManager;
import com.networking.auction.protocol.request.Request;
import com.networking.auction.util.RequestEnum;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GetAllOwnedItemRequest extends Request {
    private Optional<Integer> userId;

    @Builder
    public GetAllOwnedItemRequest() {
        this.userId = StateManager.getInstance().getUserId();
    }

    @Override
    public int getRequestType() {
        return RequestEnum.VIEW_OWNER_ITEMS_REQ.getRequest();
    }

}
