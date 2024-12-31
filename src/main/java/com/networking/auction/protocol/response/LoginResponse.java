package com.networking.auction.protocol.response;

import java.util.Optional;

import com.networking.auction.util.ResponseEnum;
import com.networking.auction.util.ResponseUtil;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class LoginResponse extends Response {
    private Optional<Integer> userId;
    private Optional<Integer> roomId;

    public static LoginResponse parseResponse(String response) {
        String[] parts = ResponseUtil.separateResponseWithoutEndTag(response);

        int respondCode = Integer.parseInt(parts[0]);
        int statusCode = Integer.parseInt(parts[1]);
        Optional<Integer> userId = Optional.empty();
        Optional<Integer> roomId = Optional.empty();

        if (parts.length == 3) {
            userId = Optional.of(Integer.parseInt(parts[2].split(" ")[0]));
            roomId = Optional.of(Integer.parseInt(parts[2].split(" ")[1]));
        }
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