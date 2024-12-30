package com.networking.auction.protocol.request.room;

import com.networking.auction.protocol.request.Request;
import com.networking.auction.util.RequestEnum;

import lombok.Getter;

@Getter
public class GetAllRoomRequest extends Request {

    @Override
    public int getRequestType() {
        return RequestEnum.VIEW_ROOMS_REQ.getRequest();
    }
}
