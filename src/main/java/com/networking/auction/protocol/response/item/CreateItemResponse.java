package com.networking.auction.protocol.response.item;

import com.networking.auction.protocol.response.Response;
import com.networking.auction.util.ResponseEnum;
import com.networking.auction.util.ResponseUtil;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class CreateItemResponse extends Response {
    public static CreateItemResponse parseResponse(String response) {
        String[] parts = ResponseUtil.separateResponse(response);

        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid response format");
        }

        int respondCode = Integer.parseInt(parts[0]);
        int statusCode = Integer.parseInt(parts[1]);

        if (respondCode != ResponseEnum.CREATE_ITEM_RES.getResponse()) {
            throw new IllegalArgumentException("Invalid response code");
        }

        return CreateItemResponse.builder()
                .respondCode(respondCode)
                .statusCode(statusCode)
                .build();
    }
}
