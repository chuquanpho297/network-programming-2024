package com.networking.meetingclient.protocol.response;

import com.networking.meetingclient.util.ResponseEnum;
import com.networking.meetingclient.util.ResponseUtil;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class LoginResponse extends Response {
    private int userId;
    private int roomId;

    public static LoginResponse parseResponse(String response) {
        String[] parts = ResponseUtil.separateResponse(response);

        if (parts.length != 4) {
            throw new IllegalArgumentException("Invalid response format");
        }

        int respondCode = Integer.parseInt(parts[0]);
        int statusCode = Integer.parseInt(parts[1]);
        int userId = Integer.parseInt(parts[2].split(" ")[0]);
        int roomId = Integer.parseInt(parts[2].split(" ")[1]);

        if (respondCode != ResponseEnum.LOGIN_RES.getResponse()) {
            throw new IllegalArgumentException("Invalid response code");
        }

        return LoginResponse.builder()
                .respondCode(respondCode)
                .statusCode(statusCode)
                .userId(userId)
                .roomId(roomId)
                .build();
    }
}