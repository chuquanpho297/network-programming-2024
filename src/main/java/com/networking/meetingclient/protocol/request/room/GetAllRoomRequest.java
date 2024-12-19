package com.networking.meetingclient.protocol.request.room;

import com.networking.meetingclient.protocol.request.Request;
import com.networking.meetingclient.util.RequestEnum;

import lombok.Getter;

@Getter
public class GetAllRoomRequest extends Request {

    public GetAllRoomRequest() {
    }

    @Override
    public int getRequestType() {
        return RequestEnum.VIEW_ROOMS_REQ.getRequest();
    }
}
