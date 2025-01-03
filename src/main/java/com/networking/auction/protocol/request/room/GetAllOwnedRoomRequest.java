package com.networking.auction.protocol.request.room;

import java.util.Optional;

import com.networking.auction.StateManager;
import com.networking.auction.protocol.request.Request;
import com.networking.auction.util.RequestEnum;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GetAllOwnedRoomRequest extends Request {
    private Optional<Integer> userId;

    @Builder
    public GetAllOwnedRoomRequest() {
        this.userId = StateManager.getInstance().getUserId();
    }

    @Override
    public int getRequestType() {
        return RequestEnum.VIEW_ROOMS_OWNED_REQ.getRequest();
    }
}
