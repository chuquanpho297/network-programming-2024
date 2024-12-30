package com.networking.auction.protocol.request.item;

import java.time.LocalDate;
import java.util.Optional;

import com.networking.auction.protocol.request.Request;
import com.networking.auction.util.RequestEnum;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SearchItemRequest extends Request {
    private Optional<String> itemName;
    private Optional<LocalDate> startTime;
    private Optional<LocalDate> endTime;
    private Optional<Integer> roomId;
    private Optional<Integer> userId;

    @Builder
    public SearchItemRequest(Optional<LocalDate> startTime, Optional<LocalDate> endTime,
            Optional<Integer> roomId, Optional<Integer> userId, Optional<String> itemName) {
        this.userId = userId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.roomId = roomId;
        this.itemName = itemName;
    }

    @Override
    public int getRequestType() {
        return RequestEnum.SEARCH_ITEM_REQ.getRequest();
    }
}
