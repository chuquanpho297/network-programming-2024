package com.networking.meetingclient.protocol.response;

import com.networking.meetingclient.util.ResponseEnum;
import com.networking.meetingclient.util.ResponseUtil;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class CreateRoomResponse extends Response {
    private int userId;

    public static CreateRoomResponse parseResponse(String response) {
        String[] parts = ResponseUtil.separateResponse(response);

        if (parts.length != 3) {
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