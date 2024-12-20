package com.networking.auction.protocol.request.room;

import java.util.Optional;

import com.networking.auction.StateManager;
import com.networking.auction.protocol.request.Request;
import com.networking.auction.util.RequestEnum;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GetAllOwnedRoomRequest extends Request {
    private int userId;

    @Builder
    public GetAllOwnedRoomRequest() {
        this.userId = Optional.ofNullable(StateManager.getInstance().getUserId()).orElse(-1);
    }

    @Override
    public int getRequestType() {
        return RequestEnum.VIEW_ROOMS_OWNED_REQ.getRequest();
    }
}
