package com.networking.auction.protocol.response.room;

import com.networking.auction.protocol.response.Response;
import com.networking.auction.util.ResponseEnum;
import com.networking.auction.util.ResponseUtil;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class LeaveRoomResponse extends Response {

    public static LeaveRoomResponse parseResponse(String response) {
        String[] parts = ResponseUtil.separateResponseWithoutEndTag(response);

        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid response format");
        }

        int respondCode = Integer.parseInt(parts[0]);
        int statusCode = Integer.parseInt(parts[1]);

        if (respondCode != ResponseEnum.LEAVE_ROOM_RES.getResponse()) {
            throw new IllegalArgumentException("Invalid response code");
        }

        return LeaveRoomResponse.builder()
                .respondCode(respondCode)
                .statusCode(statusCode)
                .build();
    }
}