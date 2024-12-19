package com.networking.auction.protocol.response;

import com.networking.auction.util.ResponseEnum;
import com.networking.auction.util.ResponseUtil;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class LogoutResponse extends Response {

    public static LogoutResponse parseResponse(String response) {
        String[] parts = ResponseUtil.separateResponse(response);

        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid response format");
        }

        int respondCode = Integer.parseInt(parts[0]);
        int statusCode = Integer.parseInt(parts[1]);

        if (respondCode != ResponseEnum.LOGOUT_RES.getResponse()) {
            throw new IllegalArgumentException("Invalid response code");
        }

        return LogoutResponse.builder()
                .respondCode(respondCode)
                .statusCode(statusCode)
                .build();
    }
}