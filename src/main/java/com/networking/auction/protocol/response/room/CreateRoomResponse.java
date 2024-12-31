package com.networking.auction.protocol.response.room;

import com.networking.auction.protocol.response.Response;
import com.networking.auction.util.ResponseEnum;
import com.networking.auction.util.ResponseUtil;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class CreateRoomResponse extends Response {

    public static CreateRoomResponse parseResponse(String response) {
        String[] parts = ResponseUtil.separateResponseWithoutEndTag(response);

        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid response format");
        }

        int respondCode = Integer.parseInt(parts[0]);
        int statusCode = Integer.parseInt(parts[1]);

        if (respondCode != ResponseEnum.CREATE_ROOM_RES.getResponse()) {
            throw new IllegalArgumentException("Invalid response code");
        }

        return CreateRoomResponse.builder()
                .respondCode(respondCode)
                .statusCode(statusCode)
                .build();
    }
}