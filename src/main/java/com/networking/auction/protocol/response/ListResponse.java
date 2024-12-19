package com.networking.auction.protocol.response;

import java.util.List;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public abstract class ListResponse<T> extends Response {
    private List<T> lists;
}