package com.networking.auction.protocol.response;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class Response {
    private int respondCode;

    private int statusCode;

}
